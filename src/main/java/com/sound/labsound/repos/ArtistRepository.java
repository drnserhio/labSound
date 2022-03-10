package com.sound.labsound.repos;

import com.sound.labsound.model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface ArtistRepository extends MongoRepository<Artist, String> {

    Artist findByArtist(String artistName);
    boolean existsByArtist(String artistName);

}
