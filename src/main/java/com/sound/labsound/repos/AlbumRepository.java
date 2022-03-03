package com.sound.labsound.repos;

import com.sound.labsound.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.Set;

@EnableMongoRepositories
public interface AlbumRepository extends MongoRepository<Album , String> {

    Album findByAlbumName(String albumName);
    Album findByAlbumNameContaining(String albumName);
    Set<Album> findAllByArtistContaining(String artist);

    boolean existsByAlbumName(String albumName);

    List<Album> findAll();
}
