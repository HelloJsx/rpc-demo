package model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResult implements Serializable {
    //是否登录成功
    private boolean result;

    //描述信息
    private String dec;

}
