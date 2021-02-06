package app.appmeteo.controller;
import app.appmeteo.controller.Commands.*;

import java.io.IOException;

public class FavouriteSession extends Session{

    protected FavouriteSession(User usr) throws IOException {
        super(usr);
    }

    @Override
    public void treatQuery() throws IOException {
        super.treatQuery();
        switch (user.getQuery()[0]) {
            case FavouritesCommands.ADD: {
                user.favouriteList.addFavourite(user.getQuery()[1]);
                break;
            }
            case FavouritesCommands.DEL: {
                if (user.getQueryLength() == 3) user.favouriteList.delFavouriteById(user.getQuery()[2]);
                else user.favouriteList.delFavouriteByName(user.getQuery()[1]);
                break;
            }
            case FavouritesCommands.LIST: {
                for (Favourite fav: user.favouriteList.getFavouriteList()) {
                    CLIController.addDisplay(fav.toString());
                }
                break;
            }
        }
    }

    @Override
    public String getHelp() {
        return "Use command:\n"
                + "'-add' + 'existing town name'    to add a town to your favorites\n"
                + "'-del' + 'favourite name' +\n"
                + "             ['favourite id']    to delete a town from your favorites\n"
                + "'-list'                          to get a display of all your current favourites\n"
                + "'quit'                           to get back to main session\n"
                + "'session' + 'session command'    to process another session specific query";
    }

    @Override
    public String toString() {
        return "Favourite Session";
    }
}
