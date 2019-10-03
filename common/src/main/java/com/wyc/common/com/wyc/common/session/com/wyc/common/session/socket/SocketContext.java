package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.vo.RegisterVo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

//该容器用于初始化
abstract  public class SocketContext {
    private SocketConfig socketConfig;
    @Getter
    private SessionSearcher sessionSearcher;
    private SocketConnector socketConnector;
    private RegisterCondition registerCondition;
    @Getter
    private MessageExecuter messageExecuter;

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;
    public void init(SocketConfig socketConfig) throws InstantiationException, IllegalAccessException, ConnectException {
        this.socketConfig = socketConfig;
        sessionSearcher = this.socketConfig.sessionSearcher();
        socketConnector = this.socketConfig.socketConnector();
        messageExecuter = this.socketConfig.messageExecuter();
        registerCondition = this.socketConfig.registerConditon();

        autowireCapableBeanFactory.autowireBean(sessionSearcher);
        autowireCapableBeanFactory.autowireBean(socketConnector);
        autowireCapableBeanFactory.autowireBean(registerCondition);
        autowireCapableBeanFactory.autowireBean(messageExecuter);
        onAcceptInit();
        socketConnector.init(socketConfig);
        registerCondition.init(sessionSearcher);
    }

    public void onAcceptInit(){
        socketConnector.accept(new SocketConnector.SocketConnectorAcceptCallback() {
            @Override
            public void accept(SocketSession socketSession) {
                autowireCapableBeanFactory.autowireBean(socketSession);
                onCloseInit(socketSession);
                onReadMessageInit(socketSession);
                RegisterVo registerVo = registerCondition.onAcceptRegQt(socketSession,socketSession.getStatus());
                if(registerVo.isPass()){
                    socketSession.doOpen();
                    sessionSearcher.register(registerVo,socketSession);
                    messageExecuter.onAcceopt(socketSession);
                }

            }
        });
    }

    public void onCloseInit(SocketSession socketSession){
        socketSession.onClose(new SocketSession.OnCloseCallback() {
            @Override
            public void onClose() {
                RegisterVo registerVo = registerCondition.onCloseRemoveQt(socketSession,socketSession.getStatus());
                if(registerVo.isPass()){
                    socketSession.doDestroy();
                    sessionSearcher.remove(registerVo,socketSession);
                    messageExecuter.onClose(registerVo.getKey());
                }
            }
        });
    }

    public void onReadMessageInit(SocketSession socketSession){
        socketSession.onReadMessage(new SocketSession.OnReadCallback() {
            @Override
            public void onReadMessage(String msg) {
                RegisterVo registerVo = registerCondition.onReadRemoveQt(msg,socketSession,socketSession.getStatus());
                if(registerVo.isPass()){
                    sessionSearcher.remove(registerVo,socketSession);
                }

                if(socketSession.getStatus()==SocketSession.LISTEN_IN_STATUS){
                    messageExecuter.onReadMessage(registerVo.getKey(),msg);
                }


                registerVo = registerCondition.onReadMsgRegQt(msg,socketSession,socketSession.getStatus());
                if(registerVo.isPass()){
                    sessionSearcher.register(registerVo,socketSession);
                }
            }
        });
    }
}
