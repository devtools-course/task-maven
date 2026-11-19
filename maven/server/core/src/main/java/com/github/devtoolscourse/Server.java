package com.github.devtoolscourse;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server implements AutoCloseable {
    private final HttpServer server;

    public Server(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public Server handler(String endpoint, Handler handler) {
        this.server.createContext(endpoint, handler);
        return this;
    }

    public Server start() {
        this.server.setExecutor(null);
        this.server.start();
        return this;
    }

    @Override
    public void close() {
        this.server.stop(0);
    }
}
