package com.github.devtoolscourse;

import java.io.IOException;

public class Runner {
    record NameDTO(String name) {}
    record PairDTO(int x, int y) {}
    record NumberDTO(int number) {}

    static void main() throws IOException {
        var server = new Server(7777)
            .handler("/echo", Handler.create(
                    NameDTO.class,
                    (in) -> Handler.response(new NameDTO(in.name), 200)
            ))
            .handler("/sum", Handler.create(
                    PairDTO.class,
                    (in) -> Handler.response(new NumberDTO(in.x + in.y), 429)
            ));

            server.start();
    }
}
