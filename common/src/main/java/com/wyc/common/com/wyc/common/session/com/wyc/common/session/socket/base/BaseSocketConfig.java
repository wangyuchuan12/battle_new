package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base;


import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.*;

public class BaseSocketConfig implements SocketConfig {


    private SessionSearcher sessionSearcher;
    private SocketConnector socketConnector;
    private MessageExecuter messageExecuter;
    private RegisterCondition registerCondition;

    public BaseSocketConfig(){
        sessionSearcher = new BaseSessionSearcher();
        socketConnector = new BaseSocketConnector();
        messageExecuter = new BaseMessageExecuter();
        registerCondition = new BaseRegisterCondtion();
    }

    @Override
    public SessionSearcher sessionSearcher() {
        return sessionSearcher;
    }

    @Override
    public SocketConnector socketConnector() {
        return socketConnector;
    }

    @Override
    public Class<? extends SocketSession>  socketSession() {
        return BaseSocketSession.class;
    }

    @Override
    public MessageExecuter messageExecuter() {
        return messageExecuter;
    }

    @Override
    public RegisterCondition registerConditon() {
        return registerCondition;
    }

    @Override
    public Integer port() {
        return 9001;
    }
}
