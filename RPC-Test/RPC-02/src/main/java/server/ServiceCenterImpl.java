package server;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceCenterImpl implements Server{
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final HashMap<String,Class> serviceRegistry = new HashMap<String, Class>();

    private static boolean isRunning = false;

    private static int port;

    public ServiceCenterImpl(int port){
        this.port = port;
    }
    @Override
    public void stop() {
        isRunning = false;
        executorService.shutdown();
    }

    @Override
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        System.out.println("启动--Server");
        try {
            while (true) {
                //监听客户端的TCP连接，接到TCP连接后将其封装为task，由线程池执行
                executorService.execute(new ServiceTask(serverSocket.accept()));
            }
        } finally {
            serverSocket.close();
        }
    }

    @Override
    public void register(Class serviceInterface, Class impl) {
        serviceRegistry.put(serviceInterface.getName(),impl);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPort() {
        return port;
    }

    public static class ServiceTask implements Runnable{
        Socket clent = null;

        public ServiceTask(Socket clent) {
            this.clent = clent;
        }

        @SneakyThrows
        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;

            try {
                //将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
                input = new ObjectInputStream(clent.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class serviceClass = serviceRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + "not found");
                }
                Method method = serviceClass.getMethod(methodName,parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(),arguments);

                //将执行结果反序列化，通过invoke方法发送给客户端
                output = new ObjectOutputStream(clent.getOutputStream());
                output.writeObject(result);
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
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    input.close();
                }
                if (clent != null) {
                    clent.close();
                }
            }
        }
    }
}
