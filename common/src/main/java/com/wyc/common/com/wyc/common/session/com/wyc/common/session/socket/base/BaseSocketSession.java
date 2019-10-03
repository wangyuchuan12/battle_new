package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base;

import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SocketSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
public class BaseSocketSession extends SocketSession {
    private ChannelHandlerContext channelHandlerContext;
    public BaseSocketSession(ChannelHandlerContext channelHandlerContext) {
        super(channelHandlerContext);
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public String getId() {
        return channelHandlerContext.channel().id().toString();
    }

    @Override
    public void init() {

    }

    @Override
    public void writeMsg(String msg) {
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg);
        channelHandlerContext.writeAndFlush(textWebSocketFrame);
    }

    @Override
    public void doClose() {
        super.doClose();
        boolean isOpen = channelHandlerContext.channel().isOpen();
        if(isOpen){
            channelHandlerContext.close();
        }

        onCloseCallback.onClose();

    }

    @Override
    public void doReadMessage(String msg) {
        onReadCallback.onReadMessage(msg);
    }
}
