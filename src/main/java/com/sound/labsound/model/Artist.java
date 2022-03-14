package com.sound.labsound.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

@Document("artists")
@NoArgsConstructor
@Data
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
