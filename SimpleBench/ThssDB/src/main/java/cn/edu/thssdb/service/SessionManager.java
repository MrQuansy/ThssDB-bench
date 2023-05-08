package cn.edu.thssdb.service;

import java.util.HashMap;
import java.util.Random;

public class SessionManager {
    private HashMap<Long, String> sessionMap;
    public int sessionNum;

    public SessionManager(){
        sessionMap = new HashMap<Long, String>();
        sessionNum = 0;
    }

    public long newSession(String username){

        Random random = new Random();
        long session = System.currentTimeMillis();
        session += random.nextLong();
        sessionMap.put(session,username);
        sessionNum += 1;
//        System.out.println(session);
        return session;
    }

    public boolean exist(long sessionId){
        return sessionMap.containsKey(sessionId);
    }

    public void deleteSession(long sessionId){
        sessionMap.remove(sessionId);
        sessionNum -= 1;
    }
}
