package com.example.mala_prodavnica.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session,Boolean>());

    @OnOpen
    public void onOpen(Session curSession) {
        System.out.println("Session opened: " + curSession.getId());
        userSessions.add(curSession);

        String message = "Server:User joined, currently active " + userSessions.size();
        for (Session ses : userSessions) {
            if (ses.isOpen()) {
                ses.getAsyncRemote().sendText(message);
            }
        }
    }

    @OnClose
    public void onClose(Session curSession) {
        System.out.println("Session closed: " + curSession.getId());
        userSessions.remove(curSession);

        //obavesti druge koliko je ostalo korisnika
        String message = "Server:User left, currently active " + userSessions.size();
        for (Session ses : userSessions) {
            if (ses.isOpen()) {
                ses.getAsyncRemote().sendText(message);
            }
        }
    }

    //posalji poruku
    @OnMessage
    public void onMessage(String message, Session userSession) {
        System.out.println("Received message: " + message);
        for (Session ses : userSessions) {
            if (ses.isOpen()) {
                ses.getAsyncRemote().sendText(message);
            }
        }
    }
}
