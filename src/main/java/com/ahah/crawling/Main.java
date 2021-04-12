package com.ahah.crawling;

import com.ahah.crawling.util.MakeCsv;
import com.ahah.crawling.util.ScrapMovie;

public class Main {
    public static void main(String[] args) {

        CrawlerInit crawler = new CrawlerInit();
        ScrapMovie scrapMovie = new ScrapMovie();
        scrapMovie.scrap(crawler.init());
        MakeCsv makeCsv = new MakeCsv();
        makeCsv.init();
    }
}
