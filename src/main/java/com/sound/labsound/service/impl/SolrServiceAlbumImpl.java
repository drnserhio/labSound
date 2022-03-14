package com.sound.labsound.service.impl;

import com.sound.labsound.model.Album;
import com.sound.labsound.repos.solr.AlbumSolrRepository;
import com.sound.labsound.service.SolrServiceAlbum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolrServiceAlbumImpl implements SolrServiceAlbum {
    private final AlbumSolrRepository albumSolrRepository;

    @Override
    public List<Album> findAlbumBySearchTerm(String searchTerm) {
        return albumSolrRepository.findAlbumBySearchTerm(searchTerm);
    }

}
