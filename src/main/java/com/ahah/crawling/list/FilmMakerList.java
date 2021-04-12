package com.ahah.crawling.list;

import com.ahah.crawling.entity.FilmMaker;

import java.util.ArrayList;
public class FilmMakerList {
    public static ArrayList<FilmMaker> filmMakerList = new ArrayList<>();

    public static void putFilmMaker(FilmMaker filmMaker) {
        filmMakerList.add(filmMaker);
    }
}
