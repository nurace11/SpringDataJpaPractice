package com.nuracell.datajpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Apod {
    String date;
    String explanation;
    String url;
    //    String hdurl;
    String title;
//    String media_type;
//    String service_version;
}