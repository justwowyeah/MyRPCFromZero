package com.justwowyeah.MyRPCVersion2.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
// 通过 reflect 在服务端封装 response
public class RPCResponse implements Serializable {
    // 定义状态码
    private int code;
    // 定义未知数据类
    private Object data;
    // 定义消息
    private String message;

    public static RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).data(data).build();
    }
    public static RPCResponse fail() {
        return RPCResponse.builder().code(500).message("服务端发生错误").build();
    }
}
