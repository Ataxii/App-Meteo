package app.appmeteo.controller;

public class Commands {

    /**
     * Commands types, first thing to check when the user does a query
     * if a command type does not begin with one of those, then it's a weather query
     */
    enum CommandTypes {

        FAV("fav"),
        HELP("help"),
        QUIT("quit");

        private final String commandName;

        CommandTypes(String commandName) {
            this.commandName = commandName;
        }

        public String toString() {
            return this.commandName;
        }
    }

    /**
     * weather related commands, for no commandtype case
     */
    enum Weather {
        TEMP("-temp"),
        WIND("-wind");

        private final String commandName;

        Weather(String commandName) {
            this.commandName = commandName;
        }

        public String toString() {
            return this.commandName;
        }

    }

    /**
     * favourites
     */
    enum Favourites {
        ADD("-add"),
        DEL("del");

        private final String commandName;

        Favourites(String commandName) {
            this.commandName = commandName;
        }

        public String toString() {
            return this.commandName;
        }
    }


}
