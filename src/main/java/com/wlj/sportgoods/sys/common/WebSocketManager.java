package com.wlj.sportgoods.sys.common;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketManager {

    private final static Map<String, String> uuidMap = new ConcurrentHashMap<>();
    private final static Map<String, String> selMap = new ConcurrentHashMap<>();
    public void adduuid(String userId, String UUID) {
        uuidMap.put(userId, UUID);
    }
    public void removeuuid(String userId) {
        uuidMap.remove(userId);
    }

    public String getuuid(String userId) {
        if(!uuidMap.containsKey(userId)) {
            return null;
        }else {
            return uuidMap.get(userId);
        }  
    }
    public void changeSel(String userId,String sel) {
        selMap.put(userId, sel);
    }
    public String getSel(String userId) {
        if(!selMap.containsKey(userId)) {
            return null;
        }else {
            return selMap.get(userId);
        }
    }
    public void removeSel(String userId) {
        selMap.remove(userId);
    }
    // 可以添加其他方法来管理会话信息，例如获取所有会话等
}
