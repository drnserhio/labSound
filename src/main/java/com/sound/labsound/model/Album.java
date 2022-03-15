package com.sound.labsound.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("albums")
@NoArgsConstructor
@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName = "albms", type = "albms")
public class Album {

    @Id
    private String albumName;
    private String artist;
    private String yearRelease;
    private String imagePoster;
    private Date dateCreate;

    public Album(String albumName, String artist, String yearRelease, String imagePoster) {
        this.albumName = albumName;
        this.artist = artist;
        this.yearRelease = yearRelease;
        this.imagePoster = imagePoster;
        dateCreate = new Date();
    }
}
