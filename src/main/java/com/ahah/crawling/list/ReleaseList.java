package com.ahah.crawling.list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ReleaseList {
    public ArrayList<String> getList() {
        ArrayList<String> releaseList = new ArrayList<>();
        Document doc = null;
        String url = "https://movie.naver.com/movie/sdb/browsing/bmovie_open.nhn";

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert doc != null;
        Elements elements = doc.select("div#old_content > table.directory_item_other > tbody > tr > td > a");
        int i = 1;
        for(Element element : elements) {
            releaseList.add(element.text());
        }

        return  releaseList;
    }
}
