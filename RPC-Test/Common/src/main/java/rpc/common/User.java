package rpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer empno;
    private String name;
}
