package model;

import lombok.Data;

import java.io.Serializable;

/**
 * 发送的请求：
 *  类名
 *  方法名
 *  参数类型
 *  参数
 */
@Data
public class Request implements Serializable {
    private String className;
    private String methodName;
    private Class[] parameters;
    private Object[] paramValues;
}
