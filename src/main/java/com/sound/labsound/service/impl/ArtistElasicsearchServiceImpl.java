package com.sound.labsound.service.impl;

import com.sound.labsound.model.Artist;
import com.sound.labsound.repos.elastic.ArtistElasticRepository;
import com.sound.labsound.service.ArtistElasicsearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArtistElasicsearchServiceImpl implements ArtistElasicsearchService {

    private final ArtistElasticRepository elasticRepository;

    @Override
    public List<Artist> findAllByArtist(String searchTerm) {
        return elasticRepository.findAllByArtistContaining(searchTerm);
    }
}
