package ru.innopolis.server;

import org.springframework.http.server.reactive.HttpHandler;
import reactor.ipc.netty.NettyContext;

public interface Server {

    NettyContext startReactorServer(String host, int port, HttpHandler httpHandler);
}