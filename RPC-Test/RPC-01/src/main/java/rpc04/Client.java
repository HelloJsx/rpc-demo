package rpc04;

import rpc.common.IUserService;

public class Client {

    public static void main(String[] args) throws Exception {
        IUserService service = Stub.getStub();
        System.out.println(service.findUserById(800));
    }
}
