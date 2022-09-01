package nettyreactor;

import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.util.concurrent.TimeUnit;

public class TcpServerApplication {
	public static void main(String[] args) {
		DisposableServer server = TcpServer.create()
				.doOnConnection(conn -> conn.addHandlerFirst(new ReadTimeoutHandler(10, TimeUnit.SECONDS)))
				.doOnChannelInit((observer, channel, remoteAddress) -> channel.pipeline().addFirst(new LoggingHandler("reactor.netty.examples")))
				.handle((inbound, outbound) -> inbound.receive().then())
				.handle((inbound, outbound) -> outbound.sendString(Mono.just("hello")))
				.wiretap(true)
				.host("localhost")
				.port(8100)
				.bindNow();

		server.onDispose().block();
	}
}
