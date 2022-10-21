# Reactor pattern

line 기술 블로그 글 예제를 복사해서 구현했다.   
https://engineering.linecorp.com/ko/blog/do-not-block-the-event-loop-part3/


## Use
1. app/build.gradle에서 mainmainClassName 이름을 multiplexingserver.reactor.Server로 변경한다.
```
mainClassName = "multiplexingserver.reactor.Server"


2. 프로젝트 홈 디렉토리에서 build 실행
```
./gradlew build
```

3. run app
```
cd app/build/libs
java -jar app.jar
```
    
## TODO
클라이언트(telnet)에서 연결 후, 클라이언트에서 연결을 닫으면 에러가 발생한다.   
```
java.io.IOException: Broken pipe
	at java.base/sun.nio.ch.FileDispatcherImpl.write0(Native Method)
	at java.base/sun.nio.ch.SocketDispatcher.write(SocketDispatcher.java:47)
	at java.base/sun.nio.ch.IOUtil.writeFromNativeBuffer(IOUtil.java:113)
	at java.base/sun.nio.ch.IOUtil.write(IOUtil.java:79)
	at java.base/sun.nio.ch.IOUtil.write(IOUtil.java:50)
	at java.base/sun.nio.ch.SocketChannelImpl.write(SocketChannelImpl.java:466)
	at multiplexingserver.reactor.EchoHandler.send(EchoHandler.java:50)
	at multiplexingserver.reactor.EchoHandler.handle(EchoHandler.java:33)
	at multiplexingserver.reactor.Reactor.dispatch(Reactor.java:44)
	at multiplexingserver.reactor.Reactor.run(Reactor.java:33)
	at multiplexingserver.reactor.Server.main(Server.java:8)
```
에러가 한 번만 발생하는게 아니라, 연결이 끊어진 이후로 연이어서 발생한다.
