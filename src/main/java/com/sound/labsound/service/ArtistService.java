package com.sound.labsound.service;

import com.sound.labsound.exception.ArtistExistsException;
import com.sound.labsound.exception.ArtistNotFoundException;
import com.sound.labsound.model.Artist;
import org.springframework.web.multipart.MultipartFile;

public interface ArtistService {

    Artist createArtist(MultipartFile fileImage, String artist, String title) throws ArtistExistsException;
    Artist createArtist(MultipartFile fileImage, String artist) throws ArtistExistsException;

    Artist updateArtist(MultipartFile fileImage, String artist, String title) throws ArtistNotFoundException;
    Artist updateArtist(MultipartFile fileImage, String artist) throws ArtistNotFoundException;

    boolean deleteArtist(String artistName) throws ArtistNotFoundException;
    boolean existsByArtist(String artistName);

    Artist findByArtist(String artistName) throws ArtistNotFoundException;
}
