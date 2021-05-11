package client;

import model.Request;
import model.Response;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Socket sc = new Socket("localhost",9999);
            ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream());
            //发送数据的封装
            Request req = new Request();
            req.setClassName("service.UserServiceImpl");
            req.setMethodName("register");
            req.setParameters(new Class[]{User.class});
            req.setParamValues(new Object[]{new User("wc","666666",null,0)});
            //发送数据
            oos.writeObject(req);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
            //接受数据
            Object readObject = ois.readObject();
            Response res = (Response) readObject;
            System.out.println(res);
            sc.shutdownInput();
            sc.shutdownOutput();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
