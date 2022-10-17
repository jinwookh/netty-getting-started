import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSockerChannel;
import java.util.Set;

public class Reactor imlements Runnable {
	final Selector selector;
	final ServerSocketChannel serverSockerChannel;
	
	Reactor(int poirt) throws IOException {
		selector = Selector.open();

		serverSockerChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port)):
		serverSocketChannel.configureBlocking(false);
		SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT):

		//Attach ahandler to handle when an event occurs
		selectionKey.attach(new AcceptHandler(selector, serverSocketChannel));

	}

	public void run() {
		try {
			while(true) {
				selector.select();
				Set<SelectionKey> selected = selector.selectedKeys();

