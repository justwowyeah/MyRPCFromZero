package com.justwowyeah.MyRPCVersion1.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
// 通过 reflect 使用 invoke 在 proxy 中封装 request
public class RPCRequest implements Serializable {
    // 获取类名
    private String interfaceName;
    // 获取方法名
    private String methodName;
    // 获取未知参数类
    private Object[] params;
    // 获取未知参数类的类型
    private Class<?>[] paramsType;
}