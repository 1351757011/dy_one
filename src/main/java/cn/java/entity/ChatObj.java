package cn.java.entity;

import lombok.Data;

import java.io.Serializable;
@Data//用于封装客户端向服务端发送的消息
public class ChatObj implements Serializable {
    private Integer type = 1;//标识群聊还是私聊,默认群聊
    private String toWho;
    private String content;//聊天的内容
}
