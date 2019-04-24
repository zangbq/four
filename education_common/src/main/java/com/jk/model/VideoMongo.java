package com.jk.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection="t_videoid")
public class VideoMongo implements Serializable {

    private static final long serialVersionUID = 1566090082014160410L;

    private  String userId;

    private String shoppingId;


}
