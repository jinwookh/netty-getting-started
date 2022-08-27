# time client
https://netty.io/wiki/user-guide-for-4.x.html#writing-a-time-client

## boss eventLoopGroup and worker eventLoopGroup
client는 boss와 워커 두가지로 나눠서 eventLoopGroup을 사용할 필요없다.   
eventLoopGroup 한 개를 사용한다.

## childOption
client는 child process가 없기 때문에 childOption도 사용하지 않는다.

