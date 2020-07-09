package com.ccssoft.cloudmessagemachine.mina.comon;

import com.ccssoft.cloudmessagemachine.entity.Message;
import com.ccssoft.cloudmessagemachine.mina.iosession.IOSessionManager;
import org.apache.mina.core.session.IoSession;

import java.sql.Timestamp;

/**
 * 处理数据工具类
 * @author moriarty
 * @date 2020/5/13 17:26
 */


public class ComonUtils {
    /**
     * 解析与处理数据
     * @param str
     * @param session
     * @return 所需数据的对应实体类
     */
    public static Message toMessageWeNeed (String str, IoSession session) {
        String[] split = str.split(",");
        String head = split[0];
        String id = head.split("\\*")[1];
        IOSessionManager.addSession(Long.valueOf(id),session);
        String type = head.split("\\*")[3];
        if ("UD".equals(type)) {
            String t1 = split[1];
            String t2 = split[2];
            String data = t1.charAt(4)+""+t1.charAt(5)+"-"+t1.charAt(2)+t1.charAt(3)+"-"+t1.charAt(0)+t1.charAt(1);
            String s = "" + t2.charAt(0) + t2.charAt(1);
            String s1 = Integer.parseInt(s) + 8 + ":" + t2.charAt(2) + t2.charAt(3) +":"+ t2.charAt(4) + t2.charAt(5);
            Timestamp time = Timestamp.valueOf("20"+data+" "+s1);
//            AltitudeUtil.SelectAltitude(Double.valueOf(split[6]),Double.valueOf(split[4]));
            Message message = new Message(id,type,split[4]+" "+split[6],split[3],split[8],split[9],split[10],split[13],time,"");
            return message;
        } else if ("LK]".equals(type) || "LK".equals(type)) {
            session.write("[3G*"+id+"*0002*LK]");
            return new Message(id+"链路保持成功！");
        } else if ("RESET]".equals(type)) {
            return new Message(id+"盒子现在开始重启");
        }
        return new Message(id+"收到标示号异常，请检查！");
    }

    /**
     * 处理盒子远程重启功能
     * @param id
     * @return 结果
     */
    public static String restartBox (Long id) {
        IoSession session = IOSessionManager.getSession(id);
        session.write("[3G*"+id+"*0005*RESET]");
        return "开始重启。。。";
    }
}
