package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket;

public interface SocketConnector {
    public void init(SocketConfig socketConfig) throws ConnectException;
    public void accept(SocketConnectorAcceptCallback callback);
    public static interface SocketConnectorAcceptCallback{
        public void accept(SocketSession socketSession);
    }
}


