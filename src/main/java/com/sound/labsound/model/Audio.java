package com.sound.labsound.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "audio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Audio {

    @Id
    private String soundName;
    private String artist;
    private String albumName;
}
