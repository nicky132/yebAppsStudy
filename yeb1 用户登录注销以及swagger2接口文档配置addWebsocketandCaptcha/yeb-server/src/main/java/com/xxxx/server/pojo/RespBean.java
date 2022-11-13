package com.xxxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
//无参数构造
@NoArgsConstructor
//有数构造
@AllArgsConstructor
public class RespBean {
    private long code;
    private Boolean success;
    private String message;
    private Object obj;
    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(Boolean success,String message){
        return new RespBean(200,success,message,null);
    }

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(Boolean success,String message,Object obj){
        return new RespBean(200,success,message,obj);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(Boolean success,String message){
        return new RespBean(500,success,message,null);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(Boolean success,String message,Object obj){
        return new RespBean(500,success,message,obj);
    }
}
