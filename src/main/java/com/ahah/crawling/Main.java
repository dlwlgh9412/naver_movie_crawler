package com.ahah.crawling;

import com.ahah.crawling.util.MakeCsv;
import com.ahah.crawling.util.ScrapMovie;

public class Main {
    public static void main(String[] args) {
        System.out.println("Before Memory: " + Runtime.getRuntime().totalMemory());

        CrawlerInit crawler = new CrawlerInit();
        ScrapMovie scrapMovie = new ScrapMovie();
        scrapMovie.scrap(crawler.init());
        MakeCsv makeCsv = new MakeCsv();
        makeCsv.init();

        System.out.println("Used Memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }
}
