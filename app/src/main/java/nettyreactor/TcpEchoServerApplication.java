package nettyreactor;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import reactor.netty.tcp.TcpServer;

import java.util.concurrent.CountDownLatch;

public class TcpEchoServerApplication {
	public static void main(String[] args) throws InterruptedException {

		CountDownLatch latch = new CountDownLatch(1);
		System.out.println("start booting server..");

		DisposableServer server = TcpServer.create()
				.doOnConnection(conn -> {
					System.out.println("connection persistent: " + conn.isPersistent());
				})
				.doOnChannelInit((observer, channel, remoteAddress) -> channel.pipeline().addFirst(new LoggingHandler(LogLevel.DEBUG)))
				.handle((inbound, outbound) -> echo(inbound, outbound))
//				.handle((inbound, outbound) -> writeHello(outbound))
				.host("localhost")
				.port(8100)
				.bind().block();


		System.out.println("binding is done!");

		latch.await();
	}

	private static NettyOutbound writeHello(NettyOutbound outbound) {
		String hello = "hello";

		return outbound
				.sendString(Mono.just(hello));
	}

	private static Mono<Void> echo(NettyInbound inbound, NettyOutbound nettyOutbound) {
		return inbound.receive()
				.asString()
				.flatMap(msg -> {
					System.out.println(msg);
					return nettyOutbound.sendString(Mono.just("hello"));
				})
				.log("logger")
				.then();
	}
}
