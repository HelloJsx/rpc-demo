package rpc10;

import rpc.common.IProductService;
import rpc.common.Product;

public class ProductServiceImpl implements IProductService {
    @Override
    public Product findProductById(Integer id) {
        return new Product(2,"wu");
    }
}
