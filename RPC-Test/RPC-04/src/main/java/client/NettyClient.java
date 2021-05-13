package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static NettyClientHandler client;

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //编写方法使用代理模式，获取一个代理对象
    public Object getBeans(final Class<?> serverClass,final String providerName){
        //使用jdk的静态代理的方法获取classLoader，类的字节码，初始化的handler
        //Thread.currentThread().getContextClassLoader()获取类的加载器，用当前线程去获取
        // new Class<?>[]{serverClass获取一个类的实例
        //那么也就是声明了代理类的实现了这些接口，代理类就可以调用接口中的声明中的所有方法
        //h：一个InvocationHandler对象，表示的是当动态代理对象调用方法的时候会关联到哪一个InvocationHandler对象上，并最终由其调用。
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serverClass}, new InvocationHandler() {
            @Override
            /**
             * proxy:代理对象
             * method:方法
             * args:参数
             *
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //使用单例模式保证client有值
                if (client == null){
                    initClient();
                }
                //providerName协议头 args[0]就是客户端调用api hello的参数
                client.serPara(providerName + args[0]);
                //
                return executorService.submit(client).get();
            }
        });
    }

    //初始化客户端
    public static void initClient(){

        client = new NettyClientHandler();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",6666).sync();
            System.out.println("client start...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
