package com.sound.labsound.service;

import com.sound.labsound.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtistElasicsearchService {
    List<Artist> findAllByArtist(String searchTerm);
}
