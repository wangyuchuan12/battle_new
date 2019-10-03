package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket;

public interface SocketConfig {
    public SessionSearcher sessionSearcher();

    public SocketConnector socketConnector();

    public Class<? extends SocketSession>  socketSession();

    public MessageExecuter messageExecuter();

    public RegisterCondition registerConditon();

    public Integer port();
}
