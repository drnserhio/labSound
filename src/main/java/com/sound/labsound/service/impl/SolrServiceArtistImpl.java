package com.sound.labsound.service.impl;

import com.sound.labsound.model.Artist;
import com.sound.labsound.repos.solr.ArtistSolrRepository;
import com.sound.labsound.service.SolrServiceArtist;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolrServiceArtistImpl implements SolrServiceArtist {
    private final ArtistSolrRepository artistSolrRepository;

    @Override
    public List<Artist> findArtistBySearchTerm(String searchTerm) {
        return artistSolrRepository.findArtistBySearchTerm(searchTerm);
    }
}
