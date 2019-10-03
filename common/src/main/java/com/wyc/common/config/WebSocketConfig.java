package com.wyc.common.config;

import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.ConnectException;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SocketApplication;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SocketConfig;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SocketContext;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base.BaseSocketConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfig {

    @Bean
    public SocketConfig socketConfig(SocketContext socketContext) throws IllegalAccessException, ConnectException, InstantiationException {
        SocketConfig socketConfig = new BaseSocketConfig();
        SocketApplication socketApplication = new SocketApplication();
        socketApplication.start(socketConfig);
        return socketConfig;
    }
}
