### Flutter混合开发

#### 引擎
Mova App中引擎是怎么管理的？
示例中为什么Fragment嵌入Flutter跳转后页面空白，引擎没复用？
引擎怎么复用？

#### flutter和原生通信
MethodChannel用于和原生一次性交互，方便用于组件化实现
MessageChanel用于和原生消息通信，方便于事件总线
EventChannel用于原生状态刷新，底层数据变化通知到Flutter
示例:
- dart获取原生手机电量
- BasicMessageChannel双向通信
- 原生发送事件给Dart