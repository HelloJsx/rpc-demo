package client;

import server.HelloService;

public class ClientBootstrap {

    public static final String prividerName = "HelloService#hello#";

    public static void main(String[] args) {
        NettyClient customer = new NettyClient();
        //获取实体对象，.hello真正被调用的地方在于getBean里面
        //只要通过代理对象去调用，那么就可以进入
        HelloService helloService = (HelloService) customer.getBeans(HelloService.class,prividerName);
        String str = helloService.hello("hello bro");

        System.out.println("server result:" + str);
    }
}
