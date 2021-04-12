package com.ahah.crawling.list;

import com.ahah.crawling.entity.Movie;

import java.util.ArrayList;

public class MovieList {
    public static ArrayList<Movie> movieList = new ArrayList<>();

    public static void putMovie(Movie movie) {
        movieList.add(movie);
    }
}
