package netty.tutorial.usingpojo.timeserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.tutorial.usingpojo.UnixTime;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {

        UnixTime time = new UnixTime();
        System.out.println("request invoked. gonna write this time value: " + time);

        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener((ChannelFutureListener) future -> {
                assert f == future;
                ctx.close();
        });
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	    cause.printStackTrace();
	    ctx.close();
    }

}
