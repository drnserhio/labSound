package com.sound.labsound.service;

import com.sound.labsound.exception.domain.AlbumExistsException;
import com.sound.labsound.exception.domain.AlbumNotFoundException;
import com.sound.labsound.exception.domain.ArtistNotFoundException;
import com.sound.labsound.model.Album;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface AlbumService {

    Album createAlbum(MultipartFile imageFile, String albumName, String artist, String yearRelease) throws AlbumExistsException, ArtistNotFoundException;
    Album updateAlbum(MultipartFile imageFile, String albumName, String artist, String yearRelease) throws AlbumNotFoundException, ArtistNotFoundException;

    boolean deleteAlbum(String album) throws AlbumNotFoundException;


    Album findByAlbumName(String albumName);
    Set<Album> findAllByArtist(String artist);

    List<Album> findAll();

    boolean existsByAlbumName(String albumName);

}
