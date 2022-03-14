package com.sound.labsound.service;

import com.sound.labsound.model.Artist;

import java.util.List;


public interface SolrServiceArtist {
    List<Artist> findArtistBySearchTerm(String searchTerm);

}
