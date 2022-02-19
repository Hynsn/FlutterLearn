package com.hynson.flutterlearn;

import java.util.ArrayList;
import java.util.List;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;

/**
 * Author: Hynsonhou
 * Date: 2022/2/19 15:28
 * Description:
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/2/19   1.0       首次创建
 */

public class EventChannelPlugin implements EventChannel.StreamHandler {
    static List<EventChannel.EventSink> eventSinks = new ArrayList<>();

    static EventChannelPlugin registerWith(FlutterEngine engine) {
        EventChannelPlugin plugin = new EventChannelPlugin();
        new EventChannel(engine.getDartExecutor().getBinaryMessenger(), "samples.flutter.io/event").setStreamHandler(plugin);
        return plugin;
    }

    static void sendEvent(Object params) {
        for (EventChannel.EventSink eventSink : eventSinks) {
            eventSink.success(params);
        }
    }

    @Override
    public void onListen(Object args, EventChannel.EventSink eventSink) {
        eventSinks.add(eventSink);
    }

    @Override
    public void onCancel(Object o) {

    }
}