package com.sound.labsound.repos;

import com.sound.labsound.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface AlbumRepository extends MongoRepository<Album , String> {

    Album findByAlbumName(String albumName);
    Album findByAlbumNameContaining(String albumName);
    Page<Album> findAllByArtistContaining(String artist, Pageable pageable);
    Set<Album> findAllByArtistContaining(String atrtist);

    boolean existsByAlbumName(String albumName);

    Page<Album> findAll(Pageable pageable);
}
