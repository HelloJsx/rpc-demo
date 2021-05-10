package rpc08_Hassian01;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import rpc.common.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class HelloHessian {
    public static void main(String[] args) throws Exception {
        User u = new User(1,"wc");
        byte[] bytes = serialize(u);
        System.out.println(bytes.length);
        User u1 = (User) deserialize(bytes);
        System.out.println(u1);
    }

    public static byte[] serialize(Object o) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        output.writeObject(o);
        output.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object deserialize(byte[] bytes) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);
        Object o = input.readObject();
        bais.close();
        input.close();
        return o;
    }
}
