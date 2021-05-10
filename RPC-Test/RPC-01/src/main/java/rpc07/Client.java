package rpc07;

import rpc.common.IProductService;
import rpc.common.Product;

public class Client {

    public static void main(String[] args) throws Exception {

        IProductService productService = (IProductService) Stub.getStub(IProductService.class);
        Product productById = productService.findProductById(321);
        System.out.println(productById);
    }
}
