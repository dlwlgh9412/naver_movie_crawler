package com.ahah.crawling.util;

import com.ahah.crawling.entity.FilmMaker;
import com.ahah.crawling.entity.Movie;
import com.ahah.crawling.entity.Movie_Genre;
import com.ahah.crawling.entity.Movie_Grade;
import com.ahah.crawling.list.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class ScrapMovie {
    private static String url;

    // 디렉토리 (개봉년도) 페이지
    public void scrap(ArrayList<String> yearList) {
        // 1980년대 이하는 경로가 다르므로, 변환작업하여 리스트에 재 입력 1980년대 -> 198
        for(int i = 0; i < yearList.size(); i++) {
            if(yearList.get(i).contains("0년대")) {
                yearList.set(i, yearList.get(i).substring(0, yearList.get(i).indexOf("0")));
            }
        }
        url = "https://movie.naver.com/movie/sdb/browsing/bmovie_open.nhn";
        Document doc = null;

        // 페이지 연결
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 년도별 목록
        assert doc != null;
        Elements elements = doc.select("div#old_content > table > tbody > tr > td");
        for(String year : yearList) {
            for(Element element : elements) {
                // 연도 경로 설정 및 접속
                String div = element.select("a").attr("href");
                
                // 경로에서 연도만 잘라서 반환 ex) =1990 -> 1990, =1940년대 -> 1940
                div = div.substring(div.indexOf("=") + 1);

                // 시작화면에서 지정한 범위의 각 문자열과 사이트의 연도 문자열과 비교하여
                // 지정한 문자열과 같으면 크롤링 시작
                if (div.equals(year)) {
                    url = "https://movie.naver.com/movie/sdb/browsing/" + element.select("a").attr("href");
                    try {
                        doc = Jsoup.connect(url).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 해당 연도의 영화리스트 페이지 Document객체 전달
                    connectMovieList(doc);
                }
            }
        }
    }
    private String getTitleId(String url) {
        return url.substring(url.indexOf("=") + 1);
    }

    private String getRating(Elements elements) {
        String rating = "";
        rating += elements.text();
        return rating;
    }

    private String getRuntime(String summaryText) {
        if (summaryText.contains("분")) {
            int index = summaryText.indexOf("분");
            return summaryText.substring(0, index);
        } else {
            return null;
        }
    }

    /*
    private int countChar(String summaryText, char check) {
        int count = 0;
        for(int i = 0; i < summaryText.length(); i++) {
            if(summaryText.charAt(i) == check) count++;
        }
        return count;
    }

     */


    private String getReleaseDate(String summaryText) {
        if(summaryText.contains("개봉")) {
            summaryText = summaryText.replace("개봉", "");
            // 개봉미정인경우 null 값으로 처리
            summaryText = summaryText.replace(" ", "");
            if(summaryText.length() != 10) {
                return "";
            }
            else {
                return summaryText;
            }
        }
        else {
            summaryText = summaryText.replace(" ", "");
            if(summaryText.length() != 10) {
                return "";
            }
            else {
                return summaryText;
            }
        }
    }
    // 개요 파트 함수
    private String[] getSummary(Elements elements, String title_id) {
        //Elements summary = elements.select("p > span");
        String[] summaryList = new String[4];
        // 개요의 내용들을 비교 전에 가공
        String summaryText = elements.text().replace(",", "");
        // 장르 리스트의 값과 개요 문자열의 값 비교하여 장르저장
        GenreList genre = new GenreList();
        ArrayList<String> genreList = genre.getList();
        for(int i = 0; i < genreList.size(); i++) {
            if(summaryText.contains(genreList.get(i))) {
                summaryList[0] += genreList.get(i) + " ";
                summaryText = summaryText.replace(genreList.get(i), "");
                Movie_Genre entity = Movie_Genre.builder()
                        .title_num(title_id)
                        .genre_num(Integer.toString(i + 1))
                        .build();
                Movie_GenreList.movie_genreLists.add(entity);
            }
        }


        if(summaryList[0] != null)
            summaryList[0] = summaryList[0].replace("null", "");
        CountryList country = new CountryList();
        ArrayList<String> countryList = country.getList();
        // 국가 리스트의 값과 개요 문자열의 값 비교하여 국가 저장
        for (String element : countryList) {
            if (summaryText.contains(element)) {
                summaryList[1] += country + " ";
                summaryText = summaryText.replace(element, "");
            }
        }
        if(summaryList[1] != null)
            summaryList[1] = summaryList[1].replace("null", "");

        // 상영시간
        summaryList[2] = getRuntime(summaryText);
        // 함수사용으로 인해 summaryText변수의 값을 재가공
        if(getRuntime(summaryText) != null) {
            summaryText = summaryText.replace(getRuntime(summaryText), "");
            summaryText = summaryText.replace("분", "");
        }

        // 개봉날짜
        summaryList[3] = getReleaseDate(summaryText);

        return summaryList;
    }

    // 이미지 크롤러
    private String getImg(String imgUrls, String id) {
        return ImgCrawler.getImg(imgUrls, id);
    }
    // 감독, 배우의 이미지 경로 함수
    private String getFilmMakerUrl(String imgUrl) {
        Document doc = null;
        try {
            doc = Jsoup.connect(imgUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert doc != null;
        imgUrl = doc.select("div#content > div.article > div.mv_info_area > div.poster > img").attr("src");

        return imgUrl;
    }

    // 영화 감독 함수
    private TreeMap<Integer, String> getDirector(Elements elements, String title_id) {
        Elements director = elements.select("p > a");
        TreeMap<Integer, String> directorList = new TreeMap<>();
        for(Element element : director) {
            String id = element.attr("href");
            url = "https://movie.naver.com" + id;
            id = id.substring(id.indexOf("=") + 1);
            String name = element.text().replace(",", "");
            directorList.put(Integer.parseInt(id), name);
            String imgDir;
            // 이미지 크롤링
            imgDir = getImg(getFilmMakerUrl(url), id);

            FilmMaker entity = FilmMaker.builder()
                    .id(id)
                    .name(name)
                    .title_id(title_id)
                    .role("감독")
                    .imgDir(imgDir)
                    .build();

            FilmMakerList.putFilmMaker(entity);
        }
        return directorList;
    }
    // 영화 출연진 페이지 - 펼쳐보기 포함
    private TreeMap<Integer, String> actorPage(Document doc, String title_id) {
        TreeMap<Integer, String> actorList = new TreeMap<>();
        Elements elements = doc.select("div.section_group.section_group_frst > div.obj_section.noline > div.made_people > div.lst_people_area > ul.lst_people > li");
        for(Element element : elements) {
            String id = element.select("div.p_info > a").attr("href");
            url = "https://movie.naver.com" + id;
            id = id.substring(id.indexOf("=") + 1);
            String name = element.select("div.p_info > a").text().replace(",", "");
            // 주연, 조연
            String role = element.select("div.part > p.in_prt").text();
            String imgDir = "";
            if(id.equals("")) {
                actorList.put(0, name);
            } else {
                actorList.put(Integer.parseInt(id), name);
                imgDir = getImg(getFilmMakerUrl(url), id);
            }

            FilmMaker entity = FilmMaker.builder()
                    .id(id)
                    .name(name)
                    .title_id(title_id)
                    .role(role)
                    .imgDir(imgDir)
                    .build();
            FilmMakerList.putFilmMaker(entity);
        }

        return actorList;
    }

    private TreeMap<Integer, String> getActor(Elements elements, String title_id) {
        TreeMap<Integer, String> actorList = new TreeMap<>();
        Elements actor = elements.select("a");
        for(Element element : actor) {
            // 영와 출연진들의 리스트에서 더보기 란 체크
            if(element.hasClass("more")) {
                url = "https://movie.naver.com/movie/bi/mi/" + element.attr("href");
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert doc != null;
                actorList = actorPage(doc, title_id);
            }
        }
        return actorList;
    }

    // 해당 영화 페이지 스크랩
    private void scrapMovie(Document doc) {
        String title_id, title, title2, plot, rating, genre ="", country = "", runtime = "", releaseDate = "", director = "", actor = "", grade = "";
        // 영화 번호
        title_id = getTitleId(doc.select("div.mv_info_area > div.mv_info > h3 > a").attr("href")).replace(",", "");
        // 영화 제목
        title = doc.select("div.mv_info_area > div.mv_info > h3.h_movie > a:nth-child(1)").text().replace(",", "");
        // 영화 소제목
        title2 = doc.select("div.mv_info_area > div.mv_info > strong").text().replace(",", "");
        // 영화 줄거리
         plot = doc.select("#content > div.article > div.section_group.section_group_frst > div:nth-child(1) > div > div.story_area").text().replace("줄거리", "").replace(",", "");
        // 영화 평점
         rating = getRating(doc.select("div.main_score > div.score.score_left > div.star_score > a > em")).replace(" ", "");

        // 영화 개요목록(평점, 국가, 상영시간, 개봉날짜)
        // step1(개요) 태그 존재여부 체크
        if(doc.select("dl.info_spec > dt").hasClass("step1")) {
            String[] summary = getSummary(doc.select("dl.info_spec > dt.step1 + dd"), title_id);
            // 장르
            genre = summary[0];
            // 국가
            country = summary[1];
            // 상영시간
            runtime = summary[2];
            // 개봉날짜
            releaseDate = summary[3];
        }

        // 영화 감독목록
        // step2(감독) 태그 존재 여부
        if(doc.select("dl.info_spec > dt").hasClass("step2")) {
            TreeMap<Integer, String> directorList = getDirector(doc.select("dl.info_spec > dt.step2 + dd"), title_id);
            for(Integer key : directorList.keySet()) {
                director += key.toString() + " ";
            }
        }

        // 영화 출연(배우) 목록
        if(doc.select("dl.info_spec > dt").hasClass("step3")) {
            TreeMap<Integer, String> actorList = getActor(doc.select("dl.info_spec > dt.step3 + dd"), title_id);
            for(Integer key : actorList.keySet()) {
                actor += key.toString() + " ";
            }
        }

        // 등급
        if(doc.select("dl.info_spec > dt").hasClass("step4")) {
            grade = getGrade(doc.select("dl.info_spec > dt.step4 + dd"), title_id);
        }

        System.out.println(title_id + " " + title + " " + title2 + " " + rating + " " + genre + " " + country + " " + runtime + " " + releaseDate + " " + director + " " + grade);
        System.out.println();
        String imgDir = "";
        // 포스터 이미지 크롤링

        try {
            doc = Jsoup.connect("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=" + title_id).get();
            if(doc.select("div#page_content > a > img").hasAttr("src")) {
                String imgUrl = doc.select("div#page_content > a > img").attr("src");
                imgDir = ImgCrawler.getImg(imgUrl, title_id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Movie entity = Movie.builder()
                .title_id(title_id)
                .title(title)
                .title2(title2)
                .rating(rating)
                .release_date(releaseDate)
                .plot(plot)
                .runtime(runtime)
                .imgDir(imgDir)
                .build();

        MovieList.putMovie(entity);

    }

    private String getGrade(Elements elements, String title_id) {
        Elements grade = elements.select("p > a");
        String str = "";
        GradeList grades = new GradeList();
        ArrayList<String> gradeList = grades.getList();
        for(Element element : grade) {
            for(int i = 0; i < gradeList.size(); i++) {
                if(element.text().equals(gradeList.get(i))) {
                    str += gradeList.get(i) + " ";
                    Movie_Grade entity = Movie_Grade.builder()
                            .grade_num(Integer.toString(i + 1))
                            .title_id(title_id)
                            .build();
                    Movie_GradeList.movie_gradeList.add(entity);
                }
            }
        }


        return str;
    }


    // 해당 년도 페이지 연결
    private void connectMovieList(Document doc) {
        // 다음 버튼의 경로를 담을 html Document
        Document next = doc;

        // 현재 페이지 영화 리스트 경로
        Elements list = doc.select("ul.directory_list > li");
        for(Element element : list) {
            url = "https://movie.naver.com/" + element.select("a").attr("href");
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            scrapMovie(doc);
        }

        // 영화리스트 페이지의 다음 버튼
        if(next.select("#old_content > div.pagenavigation > table > tbody > tr > td").hasClass("next")) {
            url = "https://movie.naver.com/" + next.select("#old_content > div.pagenavigation > table > tbody > tr > td.next > a").attr("href");
            Document nextDoc = null;
            try {
                nextDoc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert nextDoc != null;
            connectMovieList(nextDoc);
        }
    }
}

