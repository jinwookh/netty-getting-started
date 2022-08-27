# echo server

https://netty.io/wiki/user-guide-for-4.x.html#writing-a-time-client

netty로 구현한 echo server.

## channelRead
아래 코드가 핵심이다.
아래 코드를 제외한 나머지는 discard 서버와 같다.

```
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }
```
