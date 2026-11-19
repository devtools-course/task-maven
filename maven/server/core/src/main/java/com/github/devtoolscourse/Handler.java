package com.github.devtoolscourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class Handler<RequestDTO, ResponseDTO> implements HttpHandler {
    protected RequestDTO requestDTO;
    private final Class<RequestDTO> requestDTOClass;
    protected ResponseDTO responseDTO;
    protected int statusCode;

    public Handler(Class<RequestDTO> requestDTOClass) {
        this.requestDTOClass = requestDTOClass;
    }

    public abstract void handleRequest();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] body = exchange.getRequestBody().readAllBytes();
        var mapper = new ObjectMapper();
        requestDTO = mapper.readValue(body, requestDTOClass);

        this.handleRequest();

        byte[] responseBody = mapper.writeValueAsBytes(responseDTO);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBody.length);
        exchange.getResponseBody().write(responseBody);
        exchange.close();
    }

    @FunctionalInterface
    public interface Handle<RequestDTO, ResponseDTO> {
        record Result<ResponseDTO>(ResponseDTO responseDTO, int code) {}

        Result<ResponseDTO> handle(RequestDTO in);
    }

    public static <ResponseDTO> Handle.Result<ResponseDTO> response(ResponseDTO responseDTO, int code) {
        return new Handle.Result<>(responseDTO, code);
    }

    public static <RequestDTO, ResponseDTO> Handler<RequestDTO, ResponseDTO> create(
            Class<RequestDTO> requestDTOClass,
            Handle<RequestDTO, ResponseDTO> handler
    ) {
        return new Handler<RequestDTO, ResponseDTO>(requestDTOClass) {
            @Override
            public void handleRequest() {
                var out = handler.handle(requestDTO);
                this.responseDTO = out.responseDTO;
                this.statusCode = out.code;
            }
        };
    }
}
