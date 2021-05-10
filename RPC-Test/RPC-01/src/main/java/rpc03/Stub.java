package rpc03;

import rpc.common.IUserService;
import rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * Client的调用显得不是很合理(Stub里只有findById的代码)，如果有个findByName的新方法，那么就又得重新改进
 * 下面这种写法解决了方法增加的问题
 */
public class Stub {

    public static IUserService getStub() {
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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

                dos.close();
                s.close();
                return user;
            }
        };
        Object o = Proxy.newProxyInstance(IUserService.class.getClassLoader(),new Class[] {IUserService.class},h);

        return (IUserService) o;
    }
}
