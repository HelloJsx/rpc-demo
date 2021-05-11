package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private String userName;
    private String passWord;
    private String nickName;
    private int age;

    public User() {
        super();
    }

}
