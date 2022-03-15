package com.sound.labsound.repos.elastic;

import com.sound.labsound.model.Album;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AlbumElasticRepository extends ElasticsearchRepository<Album, String> {

    List<Album> findAllByAlbumNameContaining(String searchTerm);
}
