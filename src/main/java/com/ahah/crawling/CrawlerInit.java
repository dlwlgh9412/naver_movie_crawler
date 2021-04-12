package com.ahah.crawling;

import com.ahah.crawling.list.ReleaseList;

import java.util.ArrayList;
import java.util.Scanner;

public class CrawlerInit {
    public ArrayList<String> init() {
        ReleaseList list = new ReleaseList();
        ArrayList<String> releaseList = list.getList(); // 선택한 번호와 비교할 개봉년도 리스트
        ArrayList<String> selectList = new ArrayList<>(); // 선택한 개봉년도를 담을 리스트

        for(int i = 1; i < releaseList.size() + 1; i++) {
            String year  = releaseList.get(i - 1);
            if(year.contains("년대")) {
                year = year.replace("년대", "");
            }
            int printYear = Integer.parseInt(year);
            System.out.printf("%2d. %4d\t", i, printYear);
            if(i % 4 == 0) {
                System.out.println();
            }
        }
        System.out.println("\nex) 1 2 OR 1");
        System.out.print("읽어올 범위: ");
        String input = new Scanner(System.in).nextLine();
        String[] inputList = input.split(" ");

        if (inputList.length < 3) {
            // 입력한 값이 하나 일 경우
            if(inputList.length == 1) {
                int start = Integer.parseInt(inputList[0]) - 1;
                if(checkScope(releaseList, start, start)) {
                    System.out.print("잘못된 입력 범위 값입니다.");
                    System.exit(0);
                }
                selectList = returnList(releaseList, start, start);
            }
            // 입력값이 두 개 일경우
            else {
                int start = Integer.parseInt(inputList[0]) - 1;
                int end = Integer.parseInt(inputList[1]) - 1;
                if(end < start) {
                    System.out.print("잘못된 입력 범위입니다.");
                    System.exit(0);
                } else {
                    if(checkScope(releaseList, start, end)) {
                        System.out.print("잘못된 입력 범위 값입니다.");
                        System.exit(0);
                    }
                    selectList = returnList(releaseList, start, end);
                }
            }
        } else {
            System.out.println("잘못된 입력 값입니다.");
            System.out.print("프로그램 종료");
            System.exit(0);
        }

        return selectList;
    }

    // 입력한 값이 유효범위 내의 값인지 확인하는 함수
    private boolean checkScope(ArrayList<String> releaseList, int start, int end) {
        boolean checkStart = false;
        boolean checkEnd = false;
        for(int i = 0; i < releaseList.size(); i++) {
            if(start == i) {
                checkStart = true;
            }
            if(end == i) {
                checkEnd = true;
            }
        }
        return !checkStart || !checkEnd;
    }

    // 개봉년도 리스트를 가져와서 선택한 번호와 비교하여 선택한 개봉년도의 문자열을 반환
    private ArrayList<String> returnList(ArrayList<String> releaseList, int start, int end) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(releaseList.get(i));
        }

        for (String str : result) {
            System.out.print(str + " ");
        }

        System.out.println();
        return result;
    }
}
