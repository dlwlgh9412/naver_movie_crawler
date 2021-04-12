package com.ahah.crawling.list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GenreList {

    public ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();

        Document doc = null;
        String url = "https://movie.naver.com/movie/sdb/browsing/bmovie_genre.nhn";
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = doc.select("div#old_content > table > tbody > tr > td > a");
        for(Element element : elements) {
            list.add(element.text());
        }

        return list;
    }
}
