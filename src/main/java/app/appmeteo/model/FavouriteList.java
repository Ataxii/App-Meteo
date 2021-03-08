package app.appmeteo.model;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Represents a list of Favourites
 */
public class FavouriteList {

    private ArrayList<Favourite> favouriteList;
    private File relatedFile;

    /**
     * Construct a new Favourite instance by reading specified file and adding it's content into the favourites array.
     * Currently used for testing purposes only.
     *
     * @param file the file to read and write the favourites
     * @throws IOException               if reading the file fails
     * @throws InvalidParameterException if the found file was a directory.
     */
    public FavouriteList(File file) throws IOException, InvalidParameterException {
        boolean isNew = file.createNewFile();
        relatedFile = file;
        favouriteList = new ArrayList<>();

        if (!isNew) {
            BufferedReader rdr = new BufferedReader(new FileReader(file));
            String line = "";
            String[] lineContent = new String[2];
            while ((line = rdr.readLine()) != null) {
                if (line.length() == 0) continue;
                lineContent = line.split(" ");
                try {
                    favouriteList.add(new Favourite(lineContent[0], lineContent[1]));
                } catch (IOException e) {
                    System.err.println(
                            "Error : failed to add one of your favourites (" + lineContent[0] + " " + lineContent[1] + ") into the list"
                    );
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println(
                            "Error : failed to add one of your favourites into the list"
                    );
                }
            }
            rdr.close();
        }
    }

    /**
     * Create a new Favourite instance by creating a file named "favourites.txt". If the file isn't found,
     * creates a new file and the favourite list will be empty.
     *
     * @throws IOException               if reading the file fails
     * @throws FileNotFoundException     if even after trying to create it, the favourite.txt file does not exist
     * @throws InvalidParameterException if the found file was a directory.
     */
    public FavouriteList() throws IOException, InvalidParameterException, FileNotFoundException {
        File file = new File("./src/main/resources/app/appmeteo/favourite.txt");
        boolean isNew = file.createNewFile();
        relatedFile = file;
        favouriteList = new ArrayList<>();

        if (!isNew) {
            BufferedReader rdr = new BufferedReader(new FileReader(file));
            String line = "";
            String[] lineContent = new String[2];
            while ((line = rdr.readLine()) != null) {
                if (line.length() == 0) continue;
                lineContent = line.split(" ");
                try {
                    favouriteList.add(new Favourite(lineContent[0], lineContent[1]));
                } catch (IOException e) {
                    System.err.println(
                            "Error : failed to add one of your favourites (" + lineContent[0] + " " + lineContent[1] + ") into the list\n"
                    );
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println(
                            "Error : failed to add one of your favourites into the list\n"
                    );
                }
            }
            rdr.close();
        }
    }

    public ArrayList<Favourite> getList() {
        return favouriteList;
    }

    /**
     * @param index the position of the wanted Favourite
     * @return the Favourite at the specified index
     * @throws IndexOutOfBoundsException if there is no Favourite at the specified index
     */
    public Favourite getFavouriteAtIndex(int index) throws IndexOutOfBoundsException {
        if (0 > index || index >= favouriteList.size())
            throw new IndexOutOfBoundsException("Error : specified index is wrong");
        return favouriteList.get(index);
    }

    public void addFavourite(Favourite favourite) {
        if (!favouriteList.contains(favourite)) {
            favouriteList.add(favourite);
            try {
                write();
            } catch (IOException e) {
                System.err.println("Error : failed to write");
            }
        }
    }

    /**
     * @param cityName
     * @throws InvalidParameterException if specified city is already contained byt the favourite list.
     * @throws IOException               if specified city does not exist or the API is not responding.
     */
    public void addFavourite(String cityName) throws InvalidParameterException, IOException {
        cityName = cityName.toLowerCase();
        Favourite newFav = new Favourite(cityName);
        if (favouriteList.contains(newFav))
            throw new InvalidParameterException("Error : specified city already in your favourites... try to specify country code");
        else {
            favouriteList.add(newFav);
            try {
                write();
            } catch (IOException e) {
                System.err.println("Error : failed to write");
            }
        }
    }

    /**
     * @param cityName
     * @param countryCode - the country code of the city (FR, US, JP...).
     * @throws InvalidParameterException if specified city is already contained byt the favourite list.
     * @throws IOException               if specified city with specified country code does not exist or the API is not responding.
     */
    public void addFavourite(String cityName, String countryCode) throws InvalidParameterException, IOException {
        cityName = cityName.toLowerCase();
        cityName = cityName.toLowerCase();
        Favourite newFav = new Favourite(cityName, countryCode);
        if (favouriteList.contains(newFav))
            throw new InvalidParameterException("Error : specified city already in your favourites...");
        else {
            favouriteList.add(newFav);
            try {
                write();
            } catch (IOException e) {
                System.err.println("Error : failed to write");
            }
        }
    }

    public void removeFavourite(int index) throws IndexOutOfBoundsException {
        if (0 > index || index >= favouriteList.size())
            throw new IndexOutOfBoundsException("Error : specified index is wrong");
        favouriteList.remove(index);
        try {
            write();
        } catch (IOException e) {
            System.err.println("Error : failed to write");
        }
    }

    public void write() throws IOException {
        BufferedWriter wrt = new BufferedWriter(new FileWriter(relatedFile));
        for (Favourite f : favouriteList) {
            wrt.append(f.getName()).append(" ").append(f.getCountryCode());
            wrt.newLine();
        }
        wrt.close();
    }
}