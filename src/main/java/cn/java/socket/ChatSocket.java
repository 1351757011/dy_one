package cn.java.socket;

import cn.java.entity.ChatObj;
import cn.java.entity.Message;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@ServerEndpoint("/chat")
@Component
public class ChatSocket {//没登录一个用户相当于创建一个对象,即一个页面相当于一个对象
    //每添加一个用户集合中存储一个新数据
    private static List<Session> sessionList = new ArrayList<>();
    //创建集合,保存每一个连接到服务器的用户名称
    private static List<String> users = new ArrayList<>();
    //记录当前进入的用户名
    private String user;
    //存储用户与管道信息
    private static Map<String,Session> map = new HashMap<>();
    @OnOpen//当客户端与服务器端建立连接方法被自动调用
    public void open(Session session) throws IOException {
        System.out.println("通道名为session" + session.getId());
        //保存通道
        sessionList.add(session);
        //前端传递的数据为(user=abc)格式
        user = URLDecoder.decode(session.getQueryString().split("=")[1], "utf-8");
        System.out.println(user);
        //将每一个登陆的用户存储到集合
        users.add(user);
        map.put(user,session);
        Message message = new Message();
        message.setWelcome("欢迎<b style='color:red'>【"+ user+"】</b>进入房间");
        message.setUsers(users);
        System.out.println(message);
        broadcast(message.toJson());
        System.out.println("客户端与服务器端建立连接********");
    }

    //广播消息,向每一个管道都发送消息,例如新用户加入房间
    public void broadcast(String msg) throws IOException {
        for (Session session:sessionList){
            session.getBasicRemote().sendText(msg);
        }
    }

    @OnClose
    public void logout(Session session) throws IOException {
        //将当前要关闭的通道从sessionList集合中移除
        sessionList.remove(session);
        Message message = new Message();
        message.setWelcome("<b style='color:green'>【"+ user +"】</b>离开房间");
        users.remove(user);
        message.setUsers(users);
        //广播消息
        broadcast(message.toJson());
    }

    @OnMessage
    public void getMessage(Session session,String json) throws IOException {
        //将接收到的json数据反序列化成对象
        ChatObj chatObj = JSON.parseObject(json, ChatObj.class);
        //判断是群聊还是私聊
        if (chatObj.getType()==1){
            //群聊
            //封装要广播的消息
            String content = "<b style='color:blue'>【"+user+"】说:"+ chatObj.getContent() + "    " + getTime();
            Message message = new Message();
            message.setContent(content);
            broadcast(message.toJson());
        }else {
            //私聊
            //得到消息要发送给谁
            String toWho = chatObj.getToWho();
            //多个用户需要分离出来
            String[] users = toWho.split(",");
            //组装消息
            String content = "<b style='color:green'>【悄悄话】</b><b style='color:blue'>【"+user+"】说:"+ chatObj.getContent() + "    " + getTime();
            Message message = new Message();
            message.setContent(content);
            message.setType(2);
            for (String toWhichOne:users){
                message.setToWho(toWhichOne);
                //根据用户名获得需要发送的管道
                Session toSession = map.get(toWhichOne);
                //向目标对象发送消息
                toSession.getBasicRemote().sendText(message.toJson());
            }
            //向自己发送消息
            session.getBasicRemote().sendText(message.toJson());
        }
    }

    public String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return "<b style='color:red'>"+sdf.format(date) + "</b>";
    }

}
