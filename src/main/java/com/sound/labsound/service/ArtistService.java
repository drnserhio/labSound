package com.sound.labsound.service;

import com.sound.labsound.exception.domain.ArtistExistsException;
import com.sound.labsound.exception.domain.ArtistNotFoundException;
import com.sound.labsound.model.Artist;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArtistService {

    Artist createArtist(MultipartFile fileImage, String artist, String title) throws ArtistExistsException, IOException;
    Artist createArtist(MultipartFile fileImage, String artist) throws ArtistExistsException, IOException;

    Artist updateArtist(MultipartFile fileImage, String artist, String title) throws ArtistNotFoundException, IOException;
    Artist updateArtist(MultipartFile fileImage, String artist) throws ArtistNotFoundException, IOException;

    boolean deleteArtist(String artistName) throws ArtistNotFoundException;
    boolean existsByArtist(String artistName);

    Artist findByArtist(String artistName) throws ArtistNotFoundException;

    Map<String, Object> findAll(int page, int size) throws ArtistNotFoundException;

    List<Artist> findAll();
}
