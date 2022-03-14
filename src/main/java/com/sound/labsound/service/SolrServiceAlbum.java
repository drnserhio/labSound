package com.sound.labsound.service;

import com.sound.labsound.model.Album;

import java.util.List;

public interface SolrServiceAlbum {
    List<Album> findAlbumBySearchTerm(String searchTerm);
}
