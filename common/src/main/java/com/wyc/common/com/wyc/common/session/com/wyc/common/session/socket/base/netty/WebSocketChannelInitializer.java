package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base.netty;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private ConcurrentHashMap<ChannelId,HashMap> concurrentHashMap = new ConcurrentHashMap();

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    private WebSocketChannelCallback webSocketChannelCallback;

    public WebSocketChannelInitializer(WebSocketChannelCallback webSocketChannelCallback){
        this.webSocketChannelCallback = webSocketChannelCallback;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //HttpServerCodec: 针对http协议进行编解码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //ChunkedWriteHandler分块写处理，文件过大会将内存撑爆
        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        /**
         * 作用是将一个Http的消息组装成一个完成的HttpRequest或者HttpResponse，那么具体的是什么
         * 取决于是请求还是响应, 该Handler必须放在HttpServerCodec后的后面
         */
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));

        //用于处理websocket, /ws为访问websocket时的uri
        pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));

        MyHandler myHandler = new MyHandler(webSocketChannelCallback);
        autowireCapableBeanFactory.autowireBean(myHandler);
        pipeline.addLast("myWebSocketHandler",myHandler);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
        concurrentHashMap.remove(ctx.channel().id());
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
        concurrentHashMap.remove(ctx.channel().id());
    }
}
