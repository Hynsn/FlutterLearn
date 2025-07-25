package com.hynson.flutterlearn.channel;

import android.util.Log;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.StringCodec;

/**
 * Author: Hynsonhou
 * Date: 2022/2/19 15:38
 * Description: BasicMessageChannel
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/2/19   1.0       首次创建
 */
public class MessageChannelPlugin implements BasicMessageChannel.MessageHandler<String>, BasicMessageChannel.Reply<String> {
    private static String TAG = "MessageChannelPlugin";

    private static BasicMessageChannel<String> messageChannel;

    public static MessageChannelPlugin registerWith(FlutterEngine engine) {
        return new MessageChannelPlugin(engine);
    }

    private MessageChannelPlugin(FlutterEngine engine) {
        messageChannel = new BasicMessageChannel<>(engine.getDartExecutor().getBinaryMessenger(), "BasicMessageChannelPlugin", StringCodec.INSTANCE);
        //设置消息处理器，处理来自Dart的消息
        messageChannel.setMessageHandler(this);
    }

    @Override
    public void onMessage(String s, BasicMessageChannel.Reply<String> reply) {//处理Dart发来的消息
        reply.reply("BasicMessageChannel收到：" + s);//可以通过reply进行回复
        Log.i(TAG, "onMessage: "+s);
    }

    /**
     * 向Dart发送消息，并接受Dart的反馈
     *
     * @param message  要给Dart发送的消息内容
     * @param callback 来自Dart的反馈
     */
    public static void send(String message, BasicMessageChannel.Reply<String> callback) {
        messageChannel.send(message, callback);
    }

    @Override
    public void reply(String s) {
        Log.i(TAG, "reply: "+s);
    }
}
