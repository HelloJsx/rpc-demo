package model;

import lombok.Data;

import java.io.Serializable;

/**
 * 得到返回数据
 *  返回状态码
 *  返回的结果
 *  返回异常信息
 */
@Data
public class Response implements Serializable {

    private String status;
    private UserResult result;
    private Exception e;
}
