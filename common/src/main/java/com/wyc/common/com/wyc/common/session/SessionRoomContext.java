package com.wyc.common.com.wyc.common.session;

import java.util.List;

public interface SessionRoomContext {
    public void addUser(Session session,List<SessionRule> sessionRooms);
    public void removeUser(String id);
    public void removeUserByToken(String token);
    public void send(Object data);
}
