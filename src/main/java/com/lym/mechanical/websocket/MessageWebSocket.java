package com.lym.mechanical.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Classname MessageWebSocket
 * @Description
 * @Date 2019/11/11 13:40
 * @Created by jimy
 * good good code, day day up!
 */
@ServerEndpoint("/messageSocket")
@Component
@Slf4j
public class MessageWebSocket {

    private static CopyOnWriteArraySet<MessageWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
    }

    @OnMessage
    public static void onMessage(String message) {
        for (MessageWebSocket webSocket : webSocketSet) {
            try {
                webSocket.sendMessage(message);
            } catch (IOException e) {
                log.info(e.getMessage());
                continue;
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {

    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
