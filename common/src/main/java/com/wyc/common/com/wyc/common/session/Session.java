package com.wyc.common.com.wyc.common.session;

public interface Session {
    public User getUser();
    public void send(Object data);
    public void close();
}
