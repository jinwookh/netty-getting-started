
package multiplexingserver;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {

	private static final String EXIT = "EXIT";

	public static void main(String[] args) throws IOException {
		// Create selector
		Selector selector = Selector.open();

		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress("localhost", 3456));
		serverSocket.configureBlocking(false);
		
		//Register channel to the selector
		serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		ByteBuffer buffer = ByteBuffer.allocate(256);

		while (true) {
			selector.select();
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			while(iter.hasNext()) {
				SelectionKey key = iter.next();
				
				//Case: ServerSocketChannel
				if (key.isAcceptable()) {
					register(selector, serverSocket);
				}

				//Case: SocketChannel
				if (key.isReadable()) {
					answerWithEcho(buffer, key);
				}
				iter.remove();
			}
		}
	}

	private static void answerWithEcho(ByteBuffer buffer, SelectionKey key) throws IOException {
		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);
		String receivedMessage = new String(buffer.array()).trim();
		System.out.println("received message: " + receivedMessage);

		if (receivedMessage.equals(EXIT)) {
			client.close();
			System.out.println("closed client.");
			return;
		}

		buffer.flip();
		client.write(buffer);
		buffer.clear();
	}

	private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
		SocketChannel client = serverSocket.accept();
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_READ);
		System.out.println("connected new client.");
	}

}
