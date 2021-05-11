package server;

import model.Request;
import model.Response;
import model.UserResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket ssc = new ServerSocket(9999);
            while (true) {
                //获取客户端的连接
                Response res = new Response();
                Socket accept = ssc.accept();
                ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
                //接收到对象
                Object readObject = ois.readObject();
                Request req = (Request) readObject;
                String className = req.getClassName();
                String methodName = req.getMethodName();
                Class[] parameters = req.getParameters();
                Object[] paramValues = req.getParamValues();

                //反射
                //获取class对象
                Class<?> forName = Class.forName(className);
                //根据class进行实例化
                Object newInstance = forName.newInstance();
                //获取对象方法
                Method method = forName.getMethod(methodName,parameters);
                //执行方法
                Object invoke = method.invoke(newInstance,paramValues);
                UserResult result = (UserResult) invoke;

                res.setStatus("200");
                res.setResult(result);
                //得到输出流，把数据发送给客户端
                ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream());
                //把对象输出出去
                oos.writeObject(res);
                oos.flush();
                oos.close();
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
