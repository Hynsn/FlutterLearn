import 'dart:convert';
import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.green,
      ),
      home: MyHomePage(title: 'Flutter'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel('samples.flutter.io/battery');
  static const EventChannel eventChannel =
      EventChannel('samples.flutter.io/event');

  static const BasicMessageChannel _basicMessageChannel =
      BasicMessageChannel('BasicMessageChannelPlugin', StringCodec());

  // Get battery level.
  String _batteryLevel = '--';

  // eventChannel
  String _receive_data = "";

  String showMessage = "";

  Future<Null> _getBatteryLevel() async {
    String batteryLevel;
    try {
      final int result = await platform.invokeMethod('getBatteryLevel');
      batteryLevel = '$result %';
    } on PlatformException catch (e) {
      batteryLevel = '${e.message}';
    }

    // 在setState中来更新用户界面状态batteryLevel。
    setState(() {
      _batteryLevel = batteryLevel;
    });
  }

  Future<Void> _sendMessage2Native(String value) async {
    String response;
    try {
      response = await _basicMessageChannel.send(value);
    } on PlatformException catch (e) {
      print(e);
    }
    print("");
  }

  @override
  void initState() {
    super.initState();

    // 事件通道接收
    eventChannel.receiveBroadcastStream(['arg1', 'arg2']).listen(_onEvent,
        onError: _onError);
    //使用BasicMessageChannel接受来自Native的消息，并向Native回复
    _basicMessageChannel.setMessageHandler((message) => Future<String>(() {
          setState(() {
            showMessage = message;
          });
          return "";
        }));
  }

  void _onEvent(Object event) {
    setState(() {
      _receive_data = '$event';
    });
  }

  void _onError(Object error) {
    setState(() {
      _receive_data = 'Receive  failed';
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: <Widget>[
            Text(
              'method channel: BatteryLevel $_batteryLevel',
              style: Theme.of(context).textTheme.bodyLarge,
            ),
            Text(
              'basemessage channel: $showMessage',
              style: Theme.of(context).textTheme.headline6,
            ),
            Text(
              'event channel: receive $_receive_data',
              style: Theme.of(context).textTheme.headline6,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                //FlatButton 和 RaisedButton 基本一致
                TextButton(
                  child: Text(
                    "getBatteryLevel",
                    style: TextStyle(color: Colors.white),
                  ),
                  style: ButtonStyle(backgroundColor:
                      MaterialStateProperty.resolveWith((states) {
                    if (states.contains(MaterialState.pressed))
                      return null;
                    else
                      return Colors.lightBlue;
                  })),
                  onPressed: _getBatteryLevel,
                ),

                SizedBox(
                  width: 5,
                ),
                //OutlineButton 和 RaisedButton 基本一致
                TextButton(
                  child: Text(
                    "sendMessage",
                    style: TextStyle(color: Colors.white),
                  ),
                  style: ButtonStyle(backgroundColor:
                      MaterialStateProperty.resolveWith((states) {
                    if (states.contains(MaterialState.pressed))
                      return null;
                    else
                      return Colors.lightBlue;
                  })),
                  onPressed: () {
                    _sendMessage2Native('dart消息');
                  },
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
