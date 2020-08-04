package com.ccssoft.cloudmessagemachine.mina.handler;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.ccssoft.cloudcommon.common.utils.SpringBeanUtils;
import com.ccssoft.cloudmessagemachine.dao.PlanDataDao;
import com.ccssoft.cloudmessagemachine.entity.Message;
import com.ccssoft.cloudmessagemachine.entity.PlanData;
import com.ccssoft.cloudmessagemachine.mina.comon.ComonUtils;
import com.ccssoft.cloudmessagemachine.mina.iosession.IOSessionManager;
import com.ccssoft.cloudmessagemachine.websocket.WebsocketService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import redis.clients.jedis.Jedis;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author moriarty
 * @date 2020/5/13 12:13
 */
@Slf4j
public class MinaServerHandler extends IoHandlerAdapter {
    private PlanDataDao dataDao;
    private Jedis jedis = new Jedis("183.56.219.211",16379);
    /**
     * 会话创建
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        dataDao = (PlanDataDao) SpringBeanUtils.getBean("planDataDao");
        // 获取客户端ip
        InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
        InetAddress inetAddress = socketAddress.getAddress();
        log.info("sessionCreated id={},ip={}",session.getId(),inetAddress.getHostAddress());
    }

    /**
     * 会话打开
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        log.info("sessionOpened");
    }

    /**
     * 会话关闭,并把session从管理器中删掉，并告知前端。并持久化数据至mysql
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        log.info("sessionClosed");
        //通知前端
        WebsocketService websocketService = new WebsocketService();
        websocketService.sendMessageAll(String.valueOf(IOSessionManager.getId(session))+":offline");
        //从管理器删除
        Long id = IOSessionManager.removeSession(session);
        saveDataInMysql(id);

    }


    /**
     * 会话等待
     * @param session
     * @param status
     * @throws Exception
     */


    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        log.info( "IDLE:{}",session.getIdleCount( status ));
    }

    /**
     * 会话异常
     * @param session
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        cause.printStackTrace();
    }

    /**
     * 接收消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        // 读取客户端消息
        String str = message.toString();
        Message messageWeNeed = ComonUtils.toMessageWeNeed(str,session);
        log.info("Message from session [{}]: {}",session.getId(),messageWeNeed);

        WebsocketService websocketService = new WebsocketService();
        //暂时先只发位置过去就行
        if ("UD".equals(messageWeNeed.getType())) {
            websocketService.sendMessageAll(messageWeNeed.getId()+":"+messageWeNeed.getCoordinate()+":"+messageWeNeed.getSpeed()+":"+messageWeNeed.getAltitude()+":"+messageWeNeed.getBattery());
        }

        savaDataInRedis(messageWeNeed);
        // 给客户端返回数据
//        session.write();
    }

    /**
     * 发送消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        log.info("messageSent : {}",message);
    }

    /**
     * 关闭输入流
     * @param session
     * @throws Exception
     */
    @Override
    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
        log.info("inputClosed");
    }
    //单纯拿来记录数据的
    Map map = new HashMap<String,Integer>();

    /**
     * 对盒子发过来的数据进行对应的处理与存储
     * @param message
     */
    private void savaDataInRedis(Message message) {
        if ("UD".equals(message.getType())) {
            String id = message.getId();
            String point = message.getCoordinate();
            String altitude = message.getAltitude();
            String[] split = point.split(" ");
            double longitude = Double.valueOf(split[1]);
            double latitude = Double.valueOf(split[0]);
            jedis.select(7);

            if (Double.valueOf(message.getSpeed()) >4.0 && StringUtil.isNullOrEmpty(jedis.get(id+"data"))) {
                //存放记录开始时间
                map.put(id+"time",message.getTime());
                jedis.set(id+"data", point+":"+altitude);
                add(id,longitude,latitude);
            } else if (Double.valueOf(message.getSpeed()) > 4.0 && !StringUtil.isNullOrEmpty(jedis.get(id+"data"))) {
                jedis.append(id+"data", ","+point+":"+altitude);
                add(id,longitude,latitude);
            }

            map.put(id,(Integer)map.get(id)+1);

        }
    }

    private void add (String id, double longitude, double latitude) {
        if(map.get(id) == null) {
            //设置初始值关于记录坐标点距离的
            map.put(id,0);
            jedis.geoadd(id,longitude,latitude,String.valueOf(map.get(id)));
        } else {
            jedis.geoadd(id,longitude,latitude,String.valueOf(map.get(id)));
        }
    }

    private void saveDataInMysql(Long id) throws ParseException {
        Long size = jedis.zcard(String.valueOf(id));
        double geodist = 0.0;

        for (int i = 0;i<size-1;i++) {
            geodist += jedis.geodist(String.valueOf(id), String.valueOf(i), String.valueOf(i + 1));
        }
        String s = jedis.get(id+"data");

        PlanData data = new PlanData();
        String[] split = s.split(",");
        String height = "";
        String coordinate = "LINESTRING(";
        for (String str : split) {
            String[] flydata = str.split(":");
            coordinate += flydata[0]+",";
            height += flydata[1]+",";
        }

        coordinate = coordinate.substring(0, coordinate.length()-1);
        height = height.substring(0,height.length()-1);
        coordinate += ")";
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long uuid = snowflake.nextId();

        data.setId(uuid);
        data.setUavId(id);
        data.setDistance(geodist);
        data.setCoordinate(coordinate);
        data.setAltitude(height);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(""+map.get(id+"time"));
        data.setStart(date);
        data.setEnd(new Date());
        data.setGmtCreate(date);
        data.setGmtModified(date);
        System.out.println(data);
        dataDao.insertData(data);
        log.info("持久化完成");
        //清除缓存和map
        jedis.del(id+"data");
        jedis.del(String.valueOf(id));
        map.remove(id);
    }
}
