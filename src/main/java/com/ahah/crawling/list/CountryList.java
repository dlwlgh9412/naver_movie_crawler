package com.ahah.crawling.list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class CountryList {
    public ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        String url = "https://movie.naver.com/movie/sdb/browsing/bmovie_nation.nhn";
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = doc.select("div#old_content > dl.directory_item > dd > ul > li > a");
        for(Element element : elements) {
            list.add(element.text());
        }
        return list;
    }
}
