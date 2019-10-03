package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
public class MyHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebSocketChannelCallback webSocketChannelCallback;
    MyHandler(WebSocketChannelCallback webSocketChannelCallback){
        this.webSocketChannelCallback = webSocketChannelCallback;
    }


    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        webSocketChannelCallback.close(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        webSocketChannelCallback.open(channelHandlerContext);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        webSocketChannelCallback.receiveMessage(channelHandlerContext,msg);
    }
}
