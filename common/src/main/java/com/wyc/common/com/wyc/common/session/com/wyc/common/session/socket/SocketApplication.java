package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket;
import org.springframework.beans.factory.annotation.Autowired;
public class SocketApplication {
    @Autowired
    private SocketContext socketContext;
    public void start(SocketConfig socketConfig) throws IllegalAccessException, InstantiationException, ConnectException {
        new Thread(){
            @Override
            public void run() {
                try {
//                    SocketConfig socketConfig = new BaseSocketConfig();
                    socketContext.init(socketConfig);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
