package rpc07;

import rpc.common.IProductService;
import rpc.common.IUserService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public static Object getStub(Class clazz){
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1",5555);
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                String clazzName = clazz.getName();
                String methodName = method.getName();
                Class[] parametersTypes = method.getParameterTypes();

                oos.writeUTF(clazzName);
                oos.writeUTF(methodName);
                oos.writeObject(parametersTypes);
                oos.writeObject(args);
                oos.flush();

                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Object o = ois.readObject();

                oos.close();
                s.close();
                return o;
            }
        };
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(),new Class[] {IProductService.class},h);
        return o;
    }
}
