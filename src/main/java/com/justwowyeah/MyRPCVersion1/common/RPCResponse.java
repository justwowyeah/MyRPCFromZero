package com.justwowyeah.MyRPCVersion1.common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RPCResponse {
    private int code;
    private Object data;
    private String message;

    public RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).data(data).build();
    }
    public RPCResponse fail() {
        return RPCResponse.builder().code(500).message("服务端发生错误").build();
    }
}
