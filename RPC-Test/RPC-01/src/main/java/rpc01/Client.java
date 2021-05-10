package rpc01;

import rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1",8888);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(7369);

        s.getOutputStream().write(baos.toByteArray());
        s.getOutputStream().flush();

        DataInputStream dis = new DataInputStream(s.getInputStream());
        int empno = dis.readInt();
        String name = dis.readUTF();
        User user = new User(empno,name);

        System.out.println(user);

        dos.close();
        s.close();
    }
}
