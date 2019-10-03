package com.wyc.common.com.wyc.common.session;

import com.wyc.common.util.CommonUtil;
import lombok.Data;

import java.util.*;

abstract public class BaseAbstractSessionRoomContext implements SessionRoomContext {
    @Data
    class UserEntry{
        User user;
        List<SessionRule> rules;
        Session session;
    }
    private Map<String,UserEntry> userEntryIdMap = new HashMap<>();
    private Map<String,UserEntry> userEntryTokenMap = new HashMap<>();
    @Override
    public void addUser(Session session,List<SessionRule> rules) {
        UserEntry userEntry = new UserEntry();
        User user = session.getUser();
        userEntry.setUser(user);
        userEntry.setRules(rules);
        userEntry.setSession(session);
        userEntryIdMap.put(user.getId(),userEntry);
        userEntryTokenMap.put(user.getToken(),userEntry);

        if(CommonUtil.isNotEmpty(rules)&&rules.size()>0){
            Collections.sort(rules, new Comparator<SessionRule>() {
                @Override
                public int compare(SessionRule o1, SessionRule o2) {
                    int index1 = o1.index();
                    int index2 = o2.index();
                    if(index1>index2){
                        return 1;
                    }else if(index1==index2){
                        return 0;
                    }else{
                        return -1;
                    }
                }
            });
        }
    }

    @Override
    public void removeUser(String id) {
        UserEntry userEntry = userEntryIdMap.get(id);
        if(CommonUtil.isNotEmpty(userEntry)){
            userEntryIdMap.remove(userEntry.getUser().getId());
            userEntryTokenMap.remove(userEntry.getUser().getToken());
        }
    }

    @Override
    public void removeUserByToken(String token) {
        UserEntry userEntry = userEntryTokenMap.get(token);
        if(CommonUtil.isNotEmpty(userEntry)){
            userEntryIdMap.remove(userEntry.getUser().getId());
            userEntryTokenMap.remove(userEntry.getUser().getToken());
        }
    }

    @Override
    public void send(Object data) {
        for(Map.Entry<String,UserEntry> userEntry:userEntryIdMap.entrySet()){
            UserEntry userE = userEntry.getValue();
            User user = userE.getUser();
            Session session = userE.getSession();
            if(!user.isRule()){
                session.send(data);
            }else {
                List<SessionRule> rules = userE.getRules();
                boolean flag = false;
                for(SessionRule sessionRule:rules){
                    if(!flag){
                        if(sessionRule.check(data)){
                            session.send(data);
                        }
                    }else{
                        if(sessionRule.check(data)&&sessionRule.parallel()){
                            session.send(data);
                        }
                    }
                }
            }

        }
    }
}
