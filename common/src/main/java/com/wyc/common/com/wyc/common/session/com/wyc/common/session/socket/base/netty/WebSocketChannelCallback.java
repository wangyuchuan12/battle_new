package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base.netty;

import io.netty.channel.ChannelHandlerContext;

public interface WebSocketChannelCallback {
    public void close(ChannelHandlerContext channelHandlerContext);
    public void receiveMessage(ChannelHandlerContext channelHandlerContext, String msg);
    public void open(ChannelHandlerContext channelHandlerContext);
}
