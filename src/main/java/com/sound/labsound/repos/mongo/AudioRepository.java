package com.sound.labsound.repos.mongo;

import com.sound.labsound.model.Audio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AudioRepository extends MongoRepository<Audio, String> {

    Audio findByArtist(String artist);
    Audio findByAlbumName(String albumName);

    Audio findBySoundName(String soundName);

    Set<Audio> findAllByArtistContaining(String artist);
    Set<Audio> findAllByAlbumNameContaining(String albumName);

    boolean existsByArtist(String artist);
    boolean existsByAlbumName(String albumName);
    boolean existsBySoundName(String soundName);
}
