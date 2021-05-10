package rpc09_HessianAndJDK;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import rpc.common.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HessianAndJDK {

    public static void main(String[] args) throws Exception {
        User u = new User(1,"zhangsan");
        System.out.println("hessian: " + hessianSerialize(u).length);
        System.out.println("JDK: " + jdkSerialize(u).length);
    }

    public static byte[] hessianSerialize(Object o) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        output.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object hessianDeserialize(byte[] bytes) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);
        Object o = input.readObject();
        bais.close();
        input.close();
        return o;
    }

    public static byte[] jdkSerialize(Object o) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(baos);
        output.writeObject(o);
        output.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object jdkDeserialize(byte[] bytes) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream input = new ObjectInputStream(bais);
        Object o = input.readObject();
        bais.close();
        input.close();
        return o;
    }
}
