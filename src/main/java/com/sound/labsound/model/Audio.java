package com.sound.labsound.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "audio")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Audio {

    @MongoId
    private String id;
    private String artist;
    private String albumName;
    private String soundName;
}
