package test;

import client.RPCClient;
import service.HelloService;

import java.net.InetSocketAddress;

public class RpcClientTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            HelloService service = RPCClient.getRemoteProxyObj(HelloService.class,new InetSocketAddress("127.0.0.1",8888));
            System.out.println(service.sayHi("客户端传入的调用参数") + i);
        }
    }
}
