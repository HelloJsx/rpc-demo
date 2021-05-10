package rpc10;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static boolean running = true;

    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8888);
            while (running) {
                Socket s = ss.accept();
                process(s);
                s.close();
            }
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void process(Socket s){
        try {
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            ObjectInputStream ois = new ObjectInputStream(in);

            String clazzName = ois.readUTF();
            String methodName = ois.readUTF();
            Class[] parameterTypes = (Class[]) ois.readObject();
            Object[] args = (Object[]) ois.readObject();

            Class clazz = null;

            clazz = UserServiceImpl.class;

            Method method = clazz.getMethod(methodName,parameterTypes);
            Object o = method.invoke(clazz.newInstance(),args);

            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
