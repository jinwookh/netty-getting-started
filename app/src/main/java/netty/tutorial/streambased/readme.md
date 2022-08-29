
# stream based transport

https://netty.io/wiki/user-guide-for-4.x.html#dealing-with-a-stream-based-transport

수신자는 송신자가 보낸 패킷의의 일부만 볼 수도 있고, 아니면 다른 패킷과 합친 내용을 볼 수도 있다.
![image](https://user-images.githubusercontent.com/31182783/187108454-0ac28690-0690-4697-b6f2-5a9eebfb3cbb.png)



수신자가 송신자의 패킷을 온전히 볼 수 있으려면, 어플리케이션 레벨에서 정확히 패킷만큼 읽을 수 있게 하는 별도의 코드가 필요하다.
