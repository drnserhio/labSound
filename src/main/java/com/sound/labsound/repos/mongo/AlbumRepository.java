package com.sound.labsound.repos.mongo;

import com.sound.labsound.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface AlbumRepository extends MongoRepository<Album , String>{

    Album findByAlbumName(String albumName);
    Page<Album> findAllByArtistContaining(String artist, Pageable pageable);
    Set<Album> findAllByArtistContaining(String atrtist);
    boolean existsByAlbumName(String albumName);
    Page<Album> findAll(Pageable pageable);


}
