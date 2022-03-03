package com.sound.labsound.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("artists")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Artist {

    @MongoId
    private String id;
    private String title;
    private String image;
    private String artist;
    private long countSound;
}
