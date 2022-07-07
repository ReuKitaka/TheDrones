    package io.musala.thedrone.model;

    import java.util.List;
    import java.util.Random;

    public enum Model {
        LIGHTWEIGHT,MIDDLEWEIGHT,CRUISERWEIGHT,HEAVYWEIGHT;
        private static final List<Model> VALUES =
                List.of(values());
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Model randomModel()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }
