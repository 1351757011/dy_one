package cn.java.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

//用于封装需要传递给客户端显示的数据
@Data
public class Message implements Serializable {
    private String welcome;//新用户进来发出的欢迎信息
    private List<String> users;//所有在房间里面的用户
    private String content;//用户在发送消息是存储的信息
    private Integer type=1;//返回客户端是私聊还是群聊,默认群聊
    private String toWho;//返回客户端发送给谁
    //使用此方法将当前对象转换成json格式的字符串    需要导入fastjson依赖
    public String toJson(){
        return JSON.toJSONString(this);
    }

}
