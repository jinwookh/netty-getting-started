# Echo Server
java NIO를 사용해서 Echo Server를 구현한다.   
아래 글의 코드를 복사했다.   
https://engineering.linecorp.com/ko/blog/do-not-block-the-event-loop-part2/

## ClosedChannelException
아래 코드에서 return을 하지 않으면 ClosedChannelException이 발생한다.   
유의하자.

```
if (receivedMessage.equals(EXIT)) {
	client.close();
	System.out.println("closed client.");
	return;
}

buffer.flip();
client.write(buffer);
```
