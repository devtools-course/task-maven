package com.github.devtoolscourse;

import java.util.List;

public class Main {
    static void main() {
        List<Persona> p1 = List.of(
                new Persona("Alejandro", "García", 28),
                new Persona("Lucía", "Fernández", 24),
                new Persona("Javier", "Martínez", 31),
                new Persona("Carmen", "López", 27),
                new Persona("Pablo", "Sánchez", 22)
        );
        List<Persona> p2 = List.of(
                new Persona("Marta", "Pérez", 35),
                new Persona("Diego", "Gómez", 29),
                new Persona("Elena", "Ruiz", 26),
                new Persona("Sergio", "Díaz", 33),
                new Persona("Ana", "Torres", 21)
        );

        Queue<Persona> q = new Queue<>(p1);
        Stack<Persona> s = new Stack<>(p2);

        for (int i = 0; i < 5; i++) {
            q.pop().darseMano(s.pop());
        }
    }
}
