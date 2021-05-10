package rpc06;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8888);
        while (running) {
            Socket s = ss.accept();
            process(s);
            s.close();
        }
        ss.close();
    }

    private static void process(Socket s) throws Exception{
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
    }
}
