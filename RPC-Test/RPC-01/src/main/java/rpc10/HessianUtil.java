package rpc10;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class HessianUtil {
    public static byte[] serialize(Object o) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        output.writeObject(o);
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
