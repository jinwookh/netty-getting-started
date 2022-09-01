package nettyreactor;

import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.w3c.dom.ls.LSOutput;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

public class TcpClientApplication {

	public static void main(String[] args) {
		Connection connection = TcpClient.create()
			.wiretap(true)
			.host("localhost")
			.port(8100)
			.doOnConnected(conn -> conn.addHandlerFirst(new ReadTimeoutHandler(10, TimeUnit.SECONDS)))
			.doOnChannelInit((observer, channel, remoteAddress) -> channel.pipeline().addFirst(new LoggingHandler("reactor.netty.examples")))
			.handle((inbound, outbound) -> {
				inbound.receive().asString().doOnNext(System.out::println).subscribe();
				return Mono.empty();
			})
			.handle((inbound, outbound) -> outbound.sendString(Mono.just("hello")))
			.connectNow();

		connection.onDispose()
			.block();
	}



}
