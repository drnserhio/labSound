package com.sound.labsound.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "audio")
@NoArgsConstructor
@Data
public class Audio {

    @Id
    private String soundName;
    private String artist;
    private String albumName;
    private Date dateCreate;

    public Audio(String soundName, String artist, String albumName) {
        this.soundName = soundName;
        this.artist = artist;
        this.albumName = albumName;
        dateCreate = new Date();
    }
}
