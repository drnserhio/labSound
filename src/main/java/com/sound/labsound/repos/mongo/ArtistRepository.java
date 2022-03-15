package com.sound.labsound.repos.mongo;

import com.sound.labsound.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {

    Artist findByArtist(String artistName);
    boolean existsByArtist(String artistName);
    Page<Artist> findAll(Pageable pegeable);
    List<Artist> findAll();


}
