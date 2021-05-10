package rpc04;

import rpc.common.IUserService;
import rpc.common.User;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8888);
        while(running) {
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
        DataOutputStream dos = new DataOutputStream(out);

        String methodName = ois.readUTF();
        Class[] parameterTypes = (Class[]) ois.readObject();
        Object[] args = (Object[]) ois.readObject();

        IUserService service = new UserServiceImpl();
        Method method = service.getClass().getMethod(methodName,parameterTypes);
        User user = (User) method.invoke(service,args);

        dos.writeInt(user.getEmpno());
        dos.writeUTF(user.getName());
        dos.flush();
    }
}
