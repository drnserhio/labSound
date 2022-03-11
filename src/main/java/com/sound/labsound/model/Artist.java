package com.sound.labsound.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("artists")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Artist {

    @Id
    private String artist;
    private String title;
    private String image;
    private long countSound;
}
