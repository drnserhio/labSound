package com.sound.labsound.repos.solr;

import com.sound.labsound.model.Album;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface AlbumSolrRepository extends SolrCrudRepository<Album,String> {

    @Query("albumName:*?0* OR artist:*?0* OR yearRelease:*?0*")
    List<Album> findAlbumBySearchTerm(String searchTerm);
}
