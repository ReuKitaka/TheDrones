package io.musala.thedrone.model;

import java.util.List;
import java.util.Random;

public enum State {
    IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING;

    private static final List<State> VALUES =
            List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static State randomModel()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
