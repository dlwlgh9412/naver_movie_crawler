package com.ahah.crawling.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FilmMaker {
    private String id;
    private String title_id;
    private String name;
    private String role;
    private String imgDir;
}
