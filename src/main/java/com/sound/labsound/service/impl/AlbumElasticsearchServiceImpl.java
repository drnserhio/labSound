package com.sound.labsound.service.impl;

import com.sound.labsound.model.Album;
import com.sound.labsound.repos.elastic.AlbumElasticRepository;
import com.sound.labsound.service.AlbumElasticsearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumElasticsearchServiceImpl implements AlbumElasticsearchService {

    private final AlbumElasticRepository elasticRepository;

    @Override
    public List<Album> findAllByAlbum(String searchTerm) {
        return elasticRepository.findAllByAlbumNameContaining(searchTerm);
    }
}
