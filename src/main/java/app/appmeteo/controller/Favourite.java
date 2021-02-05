package app.appmeteo.controller;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Favourite {

    private ArrayList<String> favourites;
    private File relatedFile;


    /**
     * Construct a new Favourite instance by reading specified file and adding it's content into the favourites array.
     *
     * @param file the file to read and write the favourites
     * @throws IOException               if the specified file does not exist. it is very unlikely to happen considering this constructor
     *                                   calls the File method "createNewFile()".
     * @throws InvalidParameterException if param file is a directory
     */
    public Favourite(File file) throws IOException {
        if (file.isDirectory()) throw new InvalidParameterException("specified file is a directory");
        boolean isNew = file.createNewFile();
        this.relatedFile = file;
        this.favourites = new ArrayList<>();

        if (!isNew) {
            BufferedReader rdr = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = rdr.readLine()) != null) {
                if (line.length() == 0) continue;
                favourites.add(line);
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
    public Favourite() throws IOException, InvalidParameterException {
        File file = new File(System.getProperty("user.dir"));
        file = findFavouritesFile(file);

        if (file != null) {
            favourites = new Favourite(file).favourites;
            relatedFile = file;
        } else {
            file = new File("favourites.txt");
            file.createNewFile();
            favourites = new ArrayList<>();
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

    public ArrayList<String> getFavourites() {
        return favourites;
    }

    /**
     * @param fav the new favourite town to add. it will transformed to a lowercase version
     */
    public void addFavourite(String fav) {
        fav = fav.toLowerCase();
        if (favourites.contains(fav)) CLIController.addDisplay(fav + " is already in your favourites");
        else {
            favourites.add(fav);
            CLIController.addDisplay(fav + " was added to your favourites");
        }
    }

    public void delFavourite(String fav) {
        boolean removed = favourites.remove(fav);
        if (removed) CLIController.addDisplay(fav + " was removed from your favourites");
        else CLIController.addDisplay(fav + " was already not in your favourites");
    }

    /**
     * delete all favourites
     */
    public void clearFavourites() {
        favourites.clear();
        CLIController.addDisplay("cleared favourites");
    }

    /**
     * write all favourites into related file and erase attributes. Must be called for favourites to be saved after a
     * session.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        write();
        relatedFile = null;
        favourites = null;
    }

    private void write() throws IOException {
        BufferedWriter wrt = new BufferedWriter(new FileWriter(relatedFile));
        for (String s : favourites) {
            wrt.append(s);
            wrt.newLine();
        }
        wrt.close();
    }
}