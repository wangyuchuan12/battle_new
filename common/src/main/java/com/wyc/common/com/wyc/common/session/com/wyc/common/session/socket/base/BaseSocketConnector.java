package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base;

import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.*;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base.netty.WebSocketChannelCallback;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base.netty.WebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BaseSocketConnector implements SocketConnector {
    private SocketConfig socketConfig;
    private SocketConnectorAcceptCallback callback;

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    private Map<Object, SocketSession> sessionMap = new HashMap<>();
    @Override
    public void init(SocketConfig socketConfig) throws ConnectException {
        this.socketConfig = socketConfig;

        SessionSearcher sessionSearcher = socketConfig.sessionSearcher();

        Class<? extends SocketSession> socketSessionType = socketConfig.socketSession();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            WebSocketChannelInitializer webSocketChannelInitializer = new WebSocketChannelInitializer(new WebSocketChannelCallback(){

                @Override
                public void close(ChannelHandlerContext channelHandlerContext) {
                    Map<String,Object> params = new HashMap<>();
                    params.put("key",channelHandlerContext.channel().id());
                    SocketSession socketSession =  sessionMap.get(channelHandlerContext.channel().id());
                    socketSession.doClose();
                    sessionMap.remove(channelHandlerContext.channel().id());
                }

                @Override
                public void receiveMessage(ChannelHandlerContext channelHandlerContext, String msg) {
                    Map<String,Object> params = new HashMap<>();
                    params.put("key",channelHandlerContext.channel().id());
                    SocketSession socketSession =  sessionMap.get(channelHandlerContext.channel().id());
                    socketSession.doReadMessage(msg);

                }

                @Override
                public void open(ChannelHandlerContext channelHandlerContext) {
                    try {
                        Constructor<? extends SocketSession> socketSessionConstructor = socketSessionType.getConstructor(ChannelHandlerContext.class);
                        SocketSession socketSession = socketSessionConstructor.newInstance(channelHandlerContext);
                        socketSession.init();
                        callback.accept(socketSession);
                        sessionMap.put(channelHandlerContext.channel().id(),socketSession);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } finally {

                    }
                }
            });
            autowireCapableBeanFactory.autowireBean(webSocketChannelInitializer);
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(webSocketChannelInitializer);

            Integer port = socketConfig.port();
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new ConnectException(e);
        } finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void accept(SocketConnectorAcceptCallback callback) {
        this.callback = callback;
    }
}
