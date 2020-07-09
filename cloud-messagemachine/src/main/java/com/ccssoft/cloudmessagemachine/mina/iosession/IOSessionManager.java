package com.ccssoft.cloudmessagemachine.mina.iosession;

import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 管理session以便能发送指令
 * @author moriarty
 * @date 2020/5/14 12:24
 */
public class IOSessionManager {
    private static Map<IoSession,Long> sessionMap = new HashMap<>();
    private static Map<Long,IoSession> idMap = new HashMap<>();

    public static void addSession(Long id, IoSession session) {
        if (idMap.get(id) == null) {
            ReentrantLock lock = new ReentrantLock();
            lock.lock();
            try {
                sessionMap.put(session,id);
                idMap.put(id,session);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("接入新的session");
            }
        }

    }

    public static Long removeSession (IoSession session) {
        ReentrantLock lock = new ReentrantLock();

        Long id = sessionMap.get(session);

        lock.lock();
        try {
            sessionMap.remove(session);
            idMap.remove(id);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
            System.out.println("移除"+id);
        }
    }

    public static IoSession getSession (Long id) {
        return idMap.get(id);
    }

    public static Long getId(IoSession session) {
        return sessionMap.get(session);
    }
}
