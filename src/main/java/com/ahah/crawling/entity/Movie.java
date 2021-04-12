package com.ahah.crawling.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Movie {
    private String title_id;
    private String title;
    private String title2;
    private String plot;
    private String rating;
    private String runtime;
    private String release_date;
    private String imgDir;
}
