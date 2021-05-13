package server;

public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String str) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (str != null) {
            return str + "666";
        }
        return null;
    }
}
