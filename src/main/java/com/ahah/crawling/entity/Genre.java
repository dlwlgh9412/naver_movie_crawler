package com.ahah.crawling.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Genre {
    private String genre_num;
    private String genre_name;
}
