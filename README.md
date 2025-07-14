### Flutter混合开发

#### 引擎
实际项目中App中引擎是怎么管理的？引擎怎么复用？
通过FlutterEngineGroup
示例中为什么Fragment嵌入Flutter跳转后页面空白，引擎没复用？
原因是引擎被Activity使用了，没跨路由服用。
https://docs.flutter.cn/add-to-app/android/add-flutter-fragment

#### flutter和原生通信
MethodChannel用于和原生一次性交互，方便用于组件化实现
MessageChanel用于和原生消息通信，方便于事件总线
EventChannel用于原生状态刷新，底层数据变化通知到Flutter
示例:
- dart获取原生手机电量
- BasicMessageChannel双向通信
- 原生发送事件给Dart

#### 弹性布局使用
https://www.cnblogs.com/linuxAndMcu/p/18458612
https://blog.csdn.net/qq_33635385/article/details/102510108