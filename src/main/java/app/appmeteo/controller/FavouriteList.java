package app.appmeteo.controller;

import app.appmeteo.controller.CLI.CLIController;
import app.appmeteo.model.Favourite;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class FavouriteList {

    private ArrayList<Favourite> favouriteList;
    private File relatedFile;


    /**
     * Construct a new Favourite instance by reading specified file and adding it's content into the favourites array.
     *
     * @param file the file to read and write the favourites
     * @throws IOException               if the specified file does not exist. it is very unlikely to happen considering this constructor
     *                                   calls the File method "createNewFile()".
     * @throws InvalidParameterException if param file is a directory
     */
    public FavouriteList(File file) throws IOException {

        if (file.isDirectory()) throw new InvalidParameterException("specified file is a directory");
        boolean isNew = file.createNewFile();
        this.relatedFile = file;
        this.favouriteList = new ArrayList<>();

        if (!isNew) {
            BufferedReader rdr = new BufferedReader(new FileReader(file));
            String line = "";
            String[] lineContent = new String[2];
            while ((line = rdr.readLine()) != null) {
                if (line.length() == 0) continue;
                lineContent = line.split(" ");
                favouriteList.add(new Favourite(lineContent[0]));
            }
            rdr.close();
        }
    }

    /**
     * Create a new Favourite instance by searching a file named "favourites.txt" into every sub directories of this
     * project directory (using System.getProperty("user.dir")). If the file isn't found, creates a new file and favourites
     * array will be empty.
     *
     * @throws IOException               if the found file does not exist. Extremely unlikely to happen
     * @throws InvalidParameterException if the found file was a directory. Again very unlikely to happen regarding
     *                                   static method findFavouritesFile's implementation
     * @see #findFavouritesFile(File)
     */
    public FavouriteList() throws IOException, InvalidParameterException {
        File file = new File(System.getProperty("user.dir"));
        file = findFavouritesFile(file);

        if (file != null) {
            favouriteList = new FavouriteList(file).favouriteList;
            relatedFile = file;
        } else {
            file = new File("favourites.txt");
            file.createNewFile();
            favouriteList = new ArrayList<>();
            relatedFile = file;
        }
    }

    /**
     * From specified directory, recursively search anywhere for it. for optimisation purpose, search into current dir first
     * then in all sub directories.
     *
     * @param file the directory to search from
     * @return a file if "favourites.txt" was found, null otherwise. Should never return a directory.
     * @throws InvalidParameterException if specified file isn't a directory
     */
    private static File findFavouritesFile(File file) {
        if (!file.isDirectory()) throw new InvalidParameterException("specified file must be a directory");

        for (File f : file.listFiles(File::isFile)) {
            if (f.getName().equals("favourites.txt") && !f.isDirectory()) return f;
        }
        for (File dir : file.listFiles(File::isDirectory)) {
            File fav = findFavouritesFile(dir);
            if (fav != null && !fav.isDirectory()) return fav;
        }
        return null;
    }

    public ArrayList<Favourite> getFavouriteList() {
        return favouriteList;
    }

    /**
     * @param fav the new favourite town to add. it will transformed to a lowercase version
     */
    public void addFavourite(String fav) {
        fav = fav.toLowerCase();
        try {
            Favourite newFav = new Favourite(fav);
            if (favouriteList.contains(newFav)) CLIController.addDisplay(fav + " is already in your favourites");
            else {
                favouriteList.add(newFav);
                CLIController.addDisplay(fav + " was added to your favourites");
            }
        } catch (InvalidParameterException e) {
            CLIController.addDisplay("specified city might not exist...");
        }
    }

    // not safe with name
    public void delFavouriteByName(String name) {
        ArrayList<Favourite> removeList = new ArrayList<>();
        for (Favourite f: favouriteList) {
            if (f.getName().equals(name)) removeList.add(f);
        }
        for (Favourite f: removeList) {
            boolean removed = favouriteList.remove(f);
            if (removed) CLIController.addDisplay(f.getName() + " " + f.getLatLong() + " was removed from your favourites");
        }
    }

    public void delFavouriteByLongLat(String LongLat) {
        ArrayList<Favourite> removeList = new ArrayList<>();
        for (Favourite f: favouriteList) {
            if (f.getLatLong().equals(LongLat)) removeList.add(f);
        }
        for (Favourite f: removeList) {
            boolean removed = favouriteList.remove(f);
            if (removed) CLIController.addDisplay(f.getName() + " " + f.getLatLong() + " was removed from your favourites");
        }
    }



    /**
     * delete all favourites
     */
    public void clearFavouriteList() throws IOException {
        favouriteList.clear();
        CLIController.addDisplay("cleared favourites");
    }

    public void write() throws IOException {

        // TODO ECRITURE AU MEME FORMAT

        BufferedWriter wrt = new BufferedWriter(new FileWriter(relatedFile));
        for (Favourite f : favouriteList) {
            wrt.append(f.toString());
            wrt.newLine();
        }
        wrt.close();
    }
}