package app.appmeteo.controller;

public class Commands {

    /**
     * Commands types, first thing to check when the user does a query
     * if a command type does not begin with one of those, then it's a weather query
     */
    public class CommandType {
        public static final String FAV = "fav";
        public static final String HELP = "help";
        public static final String QUIT = "quit";
    }

    /**
     * weather related commands, for no commandtype case
     */
    public class WeatherCommands {
        public static final String TEMP = "-temp";
        public static final String WIND = "-wind";
    }

    /**
     * favourites
     */
    public class FavouritesCommands {
        public static final String ADD = "-add";
        public static final String DEL = "-del";
    }


}
