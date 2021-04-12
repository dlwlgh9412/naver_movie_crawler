package com.ahah.crawling.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Country {
    private String country_num;
    private String country_name;
}
