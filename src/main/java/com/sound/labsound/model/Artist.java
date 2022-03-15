package com.sound.labsound.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("artists")
@NoArgsConstructor
@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName = "arts", type = "arts")
public class Artist {

    @Id
    private String artist;
    private String title;
    private String image;
    private long countSound;
    private Date dateCreate;

    public Artist(String artist, String title, String image, long countSound) {
        this.artist = artist;
        this.title = title;
        this.image = image;
        this.countSound = countSound;
        dateCreate = new Date();
    }

}
