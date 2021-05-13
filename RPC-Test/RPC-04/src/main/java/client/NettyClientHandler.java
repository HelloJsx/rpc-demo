package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context; //上下文

    private String result;

    private String para; //参数

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead method invoke");
        result = msg.toString();
        System.out.println("server message:" + result);
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public void serPara(String para){
        this.para = para;
    }

    //NettyClientHandler可能在每个workGroup中使用，而且如果是业务线程池去执行任务，那么加上synchronized在方法前，就只能有一个线程去访问
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call method invoke");
        context.writeAndFlush(para);
        System.out.println("send message :" + para);
        wait();  //等待channelRead获取数据
        return result;
    }
}
