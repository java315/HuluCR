# 实现细节

## 已实现

* protobuf 用于处理消息 https://www.jianshu.com/p/353faad6b35d
* 消息类型：
  * 有哪些房间：WHAT_ROOMS_CMD = 0
  * 回复：WHAT_ROOMS_RESULT = 1
  * 进房间：USER_ENTRY_CMD = 2
  * 回复：USER_ENTRY_RESULT = 3
  * 玩家准备：USER_READY_CMD = 4
  * 回复：USER_READY_RESULT = 5
  *  放置角色： USER_PUT_CMD = 6
  * 回复：USER_PUT_RESULT = 7
  * 游戏结束：USER_DIE_CMD = 8
  * 回复：USER_DIE_RESULT = 9
  * 离开房间：USER_LEAVE_CMD = 10
  * 回复：USER_LEAVE_RESULT = 11
* 为了实现多房间：
  * ~~每个房间为一个信道组，用信道组 Map 实现，客户端需要用消息指明加入或创建的房间~~
  * 考虑到需要保存房间的状态，实现了一个 Room 类，在其中实现了枚举 ROOM_STATE，~~每个房间中仍具有一个信道组~~，每个房间保存（至多）两位玩家的信息，并提供对外查询接口。
  * 利用从整形映射到 Room 的 map 实现多房间。
  * ~~服务器端设置由 chanel 映射到信道组编号的 map，或消息中设置房间号，偏向前者~~
* 将玩家 channel 保存在全局信道组中
* 实现 Player 类，其中保存玩家 ID、 ChannelId、所在房间号、以及是否准备好。利用从 ChannelId 映射到 player 的 map 实现 channel 与 玩家的一一对应。在玩家进入房间时，将其对应 player 对象传入房间，后续可用其中信息（channelId），从信道组中检索得到对应的channel以发送信息。

## 未实现

* 每个时隙里面只发一次，把收到的消息全部在一个包里转发出去，然后限制每个时隙客户端只能发一次
* 群发方法 https://www.jianshu.com/p/adc2de3691c7


