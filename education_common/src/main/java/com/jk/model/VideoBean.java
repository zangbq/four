package com.jk.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Document(indexName = "ordeo",type = "orvideo")
public class VideoBean implements Serializable {

    private static final long serialVersionUID = -6914584810275278086L;
    @Id
    private  String courseId;
    @Field(analyzer = "ik_max_word")

    private  String courseName;

    private  String courseHour;

    private  String courseGrade;

    private  String coursePrice;

    private  String courseImg;

}
