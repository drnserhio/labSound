package com.sound.labsound.repos.elastic;

import com.sound.labsound.model.Artist;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArtistElasticRepository extends ElasticsearchRepository<Artist, String> {


    List<Artist> findAllByArtistContaining(String searchTerm);
}
