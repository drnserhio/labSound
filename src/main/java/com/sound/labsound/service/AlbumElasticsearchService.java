package com.sound.labsound.service;

import com.sound.labsound.model.Album;
import com.sound.labsound.model.Artist;

import java.util.List;

public interface AlbumElasticsearchService {
    List<Album> findAllByAlbum(String searchTerm);
}
