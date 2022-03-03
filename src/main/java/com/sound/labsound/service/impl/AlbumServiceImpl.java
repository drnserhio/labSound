package com.sound.labsound.service.impl;

import com.sound.labsound.exception.domain.AlbumExistsException;
import com.sound.labsound.exception.domain.AlbumNotFoundException;
import com.sound.labsound.exception.domain.ArtistNotFoundException;
import com.sound.labsound.model.Album;
import com.sound.labsound.repos.AlbumRepository;
import com.sound.labsound.repos.ArtistRepository;
import com.sound.labsound.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;

    @Override
    public Album createAlbum(MultipartFile imageFile, String albumName, String artist, String yearRelease)
            throws AlbumExistsException, ArtistNotFoundException {
        if (existsByAlbumName(albumName)) {
            throw new AlbumExistsException("Album already exists.");
        }
        if (!existsByArtist(artist)) {
            throw new ArtistNotFoundException("Artist not found");
        }
        Album album = new Album();
        album.setAlbumName(albumName);
        album.setArtist(artist);
        album.setYearRelease(yearRelease);
        Album save = albumRepository.save(album);
        //TODO: add image saver
        return save;
    }

    private boolean existsByArtist(String artist) {
        if (artistRepository.existsByArtist(artist)) {
            return true;
        }
        return false;
    }

    @Override
    public Album updateAlbum(MultipartFile imageFile, String albumName, String artist, String yearRelease)
            throws AlbumNotFoundException, ArtistNotFoundException {
        if (!existsByAlbumName(albumName)) {
            throw new AlbumNotFoundException("Album not found.");
        }
        if (!existsByArtist(artist)) {
            throw new ArtistNotFoundException("Artist not found");
        }
        Album album = albumRepository.findByAlbumName(albumName);
        album.setAlbumName(albumName);
        album.setArtist(artist);
        album.setYearRelease(yearRelease);
        Album save = albumRepository.save(album);
        //TODO: add image saver
        return save;
    }

    @Override
    public boolean deleteAlbum(String album)
            throws AlbumNotFoundException {
        if (!existsByAlbumName(album)) {
            throw new AlbumNotFoundException("Album not found");
        }
        Album alb = findByAlbumName(album);
        albumRepository.delete(alb);
        return true;
    }

    @Override
    public Album findByAlbumName(String albumName) {
        Album album = null;
        try {
           album = albumRepository.findByAlbumNameContaining(albumName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return album;
    }

    @Override
    public Set<Album> findAllByArtist(String artist) {
        Set<Album> albums = new HashSet<>();
        try {
           albums = albumRepository.findAllByArtistContaining(artist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    @Override
    public List<Album> findAll() {
        List<Album> albums = new ArrayList<>();
        try {
           albums = albumRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    @Override
    public boolean existsByAlbumName(String albumName) {
        if (albumRepository.existsByAlbumName(albumName)) {
            return true;
        }
        return false;
    }
}
