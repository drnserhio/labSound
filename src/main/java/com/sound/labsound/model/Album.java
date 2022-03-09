package com.sound.labsound.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("albums")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Album {

    @Id
    private String albumName;
    private String artist;
    private String yearRelease;
    private String imagePoster;
}
