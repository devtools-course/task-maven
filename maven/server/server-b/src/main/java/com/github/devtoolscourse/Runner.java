package com.github.devtoolscourse;

import java.io.IOException;
import java.util.Properties;

public class Runner {
    record NameDTO(String name) {
    }

    record PairDTO(int[] x, int[] y) {
        int[] sum() {
            if (x.length != y.length) {
                throw new IllegalArgumentException("x and y arrays must have same length");
            }

            int[] result = new int[x.length];
            for (int i = 0; i < x.length; i++) {
                result[i] = x[i] + y[i];
            }
            return result;
        }
    }

    record NumberDTO(int[] result) {
    }

    record GreetingDTO(String from, String name, String message) {
    }

    static void main() throws IOException {
        var props = new Properties();
        var user = "Leela";
        var server = new Server(7778)
                .handler("/echo", Handler.create(
                        NameDTO.class,
                        (in) -> Handler.response(new GreetingDTO(user, in.name, "HI"), 200)
                ))
                .handler("/sum", Handler.create(
                        PairDTO.class,
                        (in) -> Handler.response(new NumberDTO(in.sum()), 532)
                ));

        server.start();
    }
}
