package id.go.babelprov.moviecatalogues5.model;

import java.util.ArrayList;

public class DataGenre {

    final private static String[][] dataGenre = new String[][]{
        {"12", "Adventure"}, {"16", "Animation"}, {"35", "Comedy"},
        {"80", "Crime"}, {"99", "Drama"}, {"10751", "Family"},
        {"14", "Fantasy"}, {"36", "History"}, {"27", "Horror"},
        {"10402", "Music"}, {"9648", "Mystery"}, {"10749", "Romance"},
        {"878", "Science Fiction"}, {"10770", "TV Movie"}, {"53", "Thriller"},
        {"10752", "War"}, {"37", "Western"}
    };

    public static ArrayList<Genres> getDataGenre() {

        ArrayList<Genres> list = new ArrayList<>();

        for (String[] data_genre : dataGenre) {
            Genres genres = new Genres();

            genres.setId(Integer.valueOf(data_genre[0]));
            genres.setName(data_genre[1]);

            list.add(genres);
        }
        return list;
    }
}
