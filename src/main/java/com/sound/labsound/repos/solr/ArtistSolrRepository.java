package com.sound.labsound.repos.solr;

import com.sound.labsound.model.Artist;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface ArtistSolrRepository extends SolrCrudRepository<Artist, String> {
    @Query("artist:*?0*")
    List<Artist> findArtistBySearchTerm(String searchTerm);
}
