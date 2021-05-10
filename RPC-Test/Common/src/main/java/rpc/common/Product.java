package rpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
}
