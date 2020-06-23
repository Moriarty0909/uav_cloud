package com.ccssoft.cloudmessagemachine.mina.handler;

import com.ccssoft.cloudmessagemachine.entity.Message;
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

/**
 * @author moriarty
 * @date 2020/5/13 12:13
 */
@Slf4j
public class MinaServerHandler extends IoHandlerAdapter {

    private Jedis jedis = new Jedis("183.56.219.211",16379);
    /**
     * 会话创建
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
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
     * 会话关闭,并把session从管理器中删掉，并告知前端。
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
        IOSessionManager.removeSession(session);
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

        savaData(messageWeNeed);
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

    /**
     * 对盒子发过来的数据进行对应的处理与存储
     * @param message
     */
    private void savaData(Message message) {
        if ("UD".equals(message.getType())) {
            String id = message.getId();
            String point = message.getCoordinate();
            if (Double.valueOf(message.getSpeed()) >4.0 && !StringUtil.isNullOrEmpty(jedis.get(id))) {
                jedis.set(id, point);
            } else if (Double.valueOf(message.getSpeed()) > 4.0 && StringUtil.isNullOrEmpty(jedis.get(id))) {
                jedis.append(id, ","+point);
            }
            //TODO 之后可以在这里加else如果速度和高度没达标就持久化到mysql并且删除redis里的数据，算完成一段任务,还要往前端发送一个结束的标签，不在地图上继续显示这趟航程
        }

    }
}
