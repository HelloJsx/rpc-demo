package start;

import server.Server;
import server.ServiceCenterImpl;
import service.HelloService;
import service.HelloServiceImpl;

import java.io.IOException;

public class ServerStart {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server serviceServer = new ServiceCenterImpl(8888);
                serviceServer.register(HelloService.class, HelloServiceImpl.class);
                try {
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
