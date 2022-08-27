package netty.tutorial.timeserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
 	    final ByteBuf time = ctx.alloc().buffer(4);
        int timeValue = (int) (System.currentTimeMillis() / 1000L + 2208988800L);
        System.out.println("request invokded. gonna write this time value: " + timeValue);

        time.writeInt(timeValue);

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
