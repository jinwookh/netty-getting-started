package netty.tutorial.usingpojo.timeclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.tutorial.usingpojo.UnixTime;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		if (in.readableBytes() < 4) {
			return;
		}

		out.add(new UnixTime(in.readUnsignedInt()));
	}
}