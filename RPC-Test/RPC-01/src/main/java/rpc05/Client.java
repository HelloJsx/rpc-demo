package rpc05;

import rpc.common.IUserService;

public class Client {

    public static void main(String[] args) {
        IUserService service = new UserServiceImpl();
        System.out.println(service.findUserById(7369));
    }
}
