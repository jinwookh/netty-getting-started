# Time Server
https://netty.io/wiki/user-guide-for-4.x.html#writing-a-time-server   

netty로 time server를 구현한다.

## channelActive
time server에서는 channelActive를 사용한다는 점에 주목하자.   
이전 echo server의 경우channelRead를 사용했다.

channelRead의 경우 클라이언트에서 메시가 수신됐을 때 실행되고, channelActive의 경우 클라이언트와 연결이 맺어진 후 실행된다.

echo server의 경우 클라이언트에게 응답을 보내려면 클라이언트의 요청 메시지가 필요했지만, time server의 경우 요청 메시지가 필요 없다.   
time server는 클라이언트와 연결을 맺은 이후 즉시 응답을 보낸다.

