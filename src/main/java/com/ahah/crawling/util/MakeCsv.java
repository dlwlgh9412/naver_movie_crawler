package com.ahah.crawling.util;

import com.ahah.crawling.entity.*;
import com.ahah.crawling.list.*;
import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MakeCsv {

    public void init() {
        movieCSV();
        filmMakerCSV();
        genreCSV();
        gradeCSV();
        movie_GradeCSV();
        movie_GenreCSV();
    }

    private static void movie_GradeCSV() {
        String folderPath = "./resources/data/movie_grade";
        File file = new File(folderPath);
        // 경로상에 디렉토리가 없는 경우 생성
        if(!file.exists()) {
            try {
                file.mkdirs();
                System.out.println("movie_Grade Directory CREATED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            String filePath = "./resources/data/movie_grade/movie_grade.csv";
            file = new File(filePath);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            ScrapMovie scrapMovie = new ScrapMovie();

            bufferedWriter.write("TITLE_ID, GRADE_NUM");
            bufferedWriter.newLine();
            ArrayList<Movie_Grade> movie_grades = Movie_GradeList.movie_gradeList;
            for(int i = 0; i < movie_grades.size(); i++) {
                bufferedWriter.write(movie_grades.get(i).getTitle_id() + ", " + movie_grades.get(i).getGrade_num());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void movie_GenreCSV() {
        String folderPath = "./resources/data/movie_genre";
        File file = new File(folderPath);
        // 경로상에 디렉토리가 없는 경우 생성
        if(!file.exists()) {
            try {
                file.mkdirs();
                System.out.println("movie_Genre Directory CREATED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        BufferedWriter bufferedWriter = null;
        try {
            String filePath = "./resources/data/movie_genre/movie_genre.csv";
            file = new File(filePath);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            ScrapMovie scrapMovie = new ScrapMovie();

            bufferedWriter.write("TITLE_ID, GENRE_NUM");
            bufferedWriter.newLine();
            ArrayList<Movie_Genre> movie_genres = Movie_GenreList.movie_genreLists;
            for(int i = 0; i < movie_genres.size(); i++) {
                bufferedWriter.write(movie_genres.get(i).getTitle_num() + ", " + movie_genres.get(i).getGenre_num());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void gradeCSV() {
        String folderPath = "./resources/data/grade";
        File file = new File(folderPath);
        // 경로상에 디렉토리가 없는 경우 생성
        if(!file.exists()) {
            try {
                file.mkdirs();
                System.out.println("grade Directory CREATED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            String filePath = "./resources/data/grade/grade.csv";
            file = new File(filePath);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            ScrapMovie scrapMovie = new ScrapMovie();

            bufferedWriter.write("NUM, NAME");
            bufferedWriter.newLine();
            GradeList gradeList = new GradeList();
            ArrayList<String> grades = gradeList.getList();
            for(int i = 0; i < grades.size(); i++) {
                bufferedWriter.write((i + 1) + ", " + grades.get(i));
                bufferedWriter.newLine();
            }



            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void genreCSV() {
        String folderPath = "./resources/data/genre";
        File file = new File(folderPath);
        // 경로상에 디렉토리가 없는 경우 생성
        if(!file.exists()) {
            try {
                file.mkdirs();
                System.out.println("genre Directory CREATED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        BufferedWriter bufferedWriter = null;
        try {
            String filePath = "./resources/data/genre/genre.csv";
            file = new File(filePath);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            ScrapMovie scrapMovie = new ScrapMovie();

            bufferedWriter.write("NUM, NAME");
            bufferedWriter.newLine();
            GenreList genreList = new GenreList();
            ArrayList<String> genres = genreList.getList();
            for(int i = 0; i < genres.size(); i++) {
                bufferedWriter.write((i + 1) + ", " + genres.get(i));
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void filmMakerCSV() {
        String folderPath = "./resources/data/filmMaker";
        File file = new File(folderPath);
        // 경로상에 디렉토리가 없는 경우 생성
        if(!file.exists()) {
            try {
                file.mkdirs();
                System.out.println("filmMaker Directory CREATED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            String filePath = "./resources/data/filmMaker/filmMaker.csv";
            file = new File(filePath);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            //ScrapMovie scrapMovie = new ScrapMovie();

            bufferedWriter.write("ID, NAME, ROLE, TITLE_ID, IMG_DIR");
            bufferedWriter.newLine();

            ArrayList<FilmMaker> filmMakers = FilmMakerList.filmMakerList;
            for(int i = 0; i < filmMakers.size(); i++) {
                bufferedWriter.write(filmMakers.get(i).getId() + ", " + filmMakers.get(i).getName() + ", " + filmMakers.get(i).getRole()
                + ", " + filmMakers.get(i).getTitle_id() + ", " + filmMakers.get(i).getImgDir());
                bufferedWriter.newLine();
            }



            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void movieCSV() {
        String folderPath = "./resources/data/movie";
        File file = new File(folderPath);
        // 경로상에 디렉토리가 없는 경우 생성
        if(!file.exists()) {
            try {
                file.mkdirs();
                System.out.println("movie Directory CREATED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        BufferedWriter bufferedWriter = null;
        try {
            String filePath = "./resources/data/movie/movie.csv";
            file = new File(filePath);
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            ScrapMovie scrapMovie = new ScrapMovie();

            //                     1          2          3       4       5          6           7             8        9        10
            String[] column = {"TITLE_ID", "TITLE", "TITLE2", "PLOT", "RATING", "RUNTIME", "RELEASE_DATE",  "IMG_DIR"};
            writer.writeNext(column);

            // 영화 객체 불러오기
            ArrayList<Movie> movies = MovieList.movieList;
            for(int i = 0; i < movies.size(); i++) {
                String[] row = inputMovieRow(movies, i, column.length);
                if(row != null)
                    writer.writeNext(row);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String nullCheck(String get) {
        if(get == null || get.equals("null") || get.equals(" ")) {
            return "";
        }
        else {
            if(get.contains(",")) {
                get = get.replace(",", "");
            }
            return get;
        }
    }

    /* 추후 수정 */
    private static String[] inputMovieRow(ArrayList<Movie> movies, int index, int length) {
        // "TITLE_ID", "TITLE", "TITLE2", "PLOT", "RATING", "RUNTIME", "RELEASE_DATE", "IMG_DIR"
        String[] movie = new String[length];
        movie[0] = movies.get(index).getTitle_id().replace(" ", "");
        if(movie[0].equals("")) {
            return null;
        }
        movie[1] = movies.get(index).getTitle();
        movie[2] = movies.get(index).getTitle2();
        movie[3] = movies.get(index).getPlot();
        movie[4] = movies.get(index).getRating();
        movie[5] = movies.get(index).getRuntime();
        movie[6] = movies.get(index).getRelease_date();
        movie[7] = movies.get(index).getImgDir();
        return movie;
    }

}
