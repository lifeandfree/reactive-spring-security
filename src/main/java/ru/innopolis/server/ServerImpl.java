package ru.innopolis.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Service;
import reactor.ipc.netty.NettyContext;
import reactor.ipc.netty.http.server.HttpServer;

@Service
public class ServerImpl implements Server {

    private static final Logger logger = LogManager.getLogger(ServerImpl.class.getName());

    @Override
    public NettyContext startReactorServer(String host, int port, HttpHandler httpHandler) {
        logger.info("Starting server on: "+ host + ":" +  port);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create(host, port);
        return server.newHandler(adapter).block();
    }
}