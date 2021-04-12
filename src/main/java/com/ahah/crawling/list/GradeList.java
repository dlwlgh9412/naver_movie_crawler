package com.ahah.crawling.list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GradeList {
    public ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        Document doc = null;
        String url = "https://movie.naver.com/movie/sdb/browsing/bmovie_grade.nhn";
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = doc.select("div#old_content > dl.directory_item_1 > dd > table > tbody > tr > td > a");
        for(Element element : elements) {
            list.add(element.text());
        }
        return list;
    }
}
