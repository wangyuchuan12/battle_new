package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket;

public interface MessageExecuter {
    public void onClose(String key);
    public void onReadMessage(String key, String msg);
    public String onAcceopt(SocketSession socketSession);
    public void write(String key, String msg);
}
