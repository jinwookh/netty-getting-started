package nettyreactor;

import io.netty.handler.logging.LoggingHandler;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.netty.Connection;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiFunction;

public class TcpEchoClientApplication {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("tcp client application running..");
		CountDownLatch latch = new CountDownLatch(1);

		BiFunction<NettyInbound, NettyOutbound, Publisher<Void>> handler
				= (nettyInbound, nettyOutbound) -> {
					nettyInbound.receive().asString()
						.doOnNext(msg -> System.out.println(msg))
						.subscribe();

					return nettyOutbound
						.sendString(Flux.fromIterable(List.of("hello","bye","hello again?"))
							.delayElements(Duration.ofMillis(1000)))
							.neverComplete();
				};

		Connection connection = TcpClient.create()
			.wiretap(true)
			.host("localhost")
			.port(8100)
			.doOnChannelInit((observer, channel, remoteAddress) -> channel.pipeline().addFirst(new LoggingHandler("reactor.netty.examples")))
			.handle(handler)
			.connectNow();

		System.out.println("client connected! done!");

		latch.await();

		System.out.println("dispose block done!");
	}
}
