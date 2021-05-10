package rpc03;

import rpc.common.IUserService;
import rpc.common.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
        DataInputStream dis = new DataInputStream(in);
        DataOutputStream dos = new DataOutputStream(out);

        int empno = dis.readInt();
        //IUserService service = new UserServiceImpl();
        IUserService service = new UserServiceImpl();
        User user = service.findUserById(empno);
        dos.writeInt(user.getEmpno());
        dos.writeUTF(user.getName());
        dos.flush();
    }
}
