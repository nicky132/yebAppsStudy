package com.xxxx.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息
 *
 * @author harry
 * @date 2022/11/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {
    //发送者
    private String from;
    //接收者
    private String to;
    //内容
    private String content;
    //发送时间
    private LocalDateTime date;
    //发送者昵称
    private String formNickName;
}
