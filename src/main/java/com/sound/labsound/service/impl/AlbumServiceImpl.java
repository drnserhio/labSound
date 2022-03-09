package com.sound.labsound.service.impl;

import com.sound.labsound.exception.domain.AlbumExistsException;
import com.sound.labsound.exception.domain.AlbumNotFoundException;
import com.sound.labsound.exception.domain.ArtistNotFoundException;
import com.sound.labsound.model.Album;
import com.sound.labsound.repos.AlbumRepository;
import com.sound.labsound.repos.ArtistRepository;
import com.sound.labsound.service.AlbumService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@AllArgsConstructor
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;

    public static final String ALBUM_IMAGE_PATH = "/album/image/";
    public static final String FORWARD_SLASH = "/";
    public static final String DOT = "."; // filename.jpg
    public static final String JPG_EXSTENSION = "jpg";

    public static final String ALBUM_FOLDER = System.getProperty("user.home") + "/labSound/album/";


    @Override
    public Album createAlbum(MultipartFile imageFile, String albumName, String artist, String yearRelease)
            throws AlbumExistsException, ArtistNotFoundException, IOException {
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
        saveAlbumImage(album, imageFile);
        Album save = albumRepository.save(album);
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
            throws AlbumNotFoundException, ArtistNotFoundException, IOException {
        if (!existsByAlbumName(albumName)) {
            throw new AlbumNotFoundException("Album not found.");
        }
        if (!existsByArtist(artist)) {
            throw new ArtistNotFoundException("Artist not found");
        }
        Album album = albumRepository.findByAlbumName(albumName);
        System.out.println(album.toString());
        album.setAlbumName(albumName);
        album.setArtist(artist);
        album.setYearRelease(yearRelease);
        saveAlbumImage(album, imageFile);
        Album save = albumRepository.save(album);
        return save;
    }

    @Override
    public Album updateAlbumInfo(String albumName, String artist, String yearRelease)
            throws AlbumNotFoundException, ArtistNotFoundException {
        if (!existsByAlbumName(albumName)) {
            throw new AlbumNotFoundException("Album not found.");
        }
        if (!existsByArtist(artist)) {
            throw new ArtistNotFoundException("Artist not found");
        }
        Album album = albumRepository.findByAlbumName(albumName);
        System.out.println(album.toString());
        album.setAlbumName(albumName);
        album.setArtist(artist);
        album.setYearRelease(yearRelease);
        Album save = albumRepository.save(album);
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
            album = albumRepository.findByAlbumName(albumName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return album;
    }

    @Override
    public Map<String, Object> findAllByArtist(String artist, int page, int size, String column) throws ArtistNotFoundException {
        Pageable pg = PageRequest.of(page, size, Sort.by(column));
        Page<Album> p = null;
        List<Album> albums = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            p = albumRepository.findAllByArtistContaining(artist, pg);
            albums = p.getContent();

            response.put("content", albums);
            response.put("currentPage", p.getNumber());
            response.put("totalItems", p.getTotalElements());
            response.put("totalPages", p.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (albums.size() == 0) {
            throw new ArtistNotFoundException("Atrist don't have albums.");
        }
        return response;
    }

    @Override
    public Map<String, Object> findAll(int page, int size, String column) {
        Pageable pg = PageRequest.of(page, size, Sort.by(column));
        Page<Album> p = null;
        List<Album> albums = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            p = albumRepository.findAll(pg);
            albums = p.getContent();

            response.put("content", albums);
            response.put("currentPage", p.getNumber());
            response.put("totalItems", p.getTotalElements());
            response.put("totalPages", p.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public boolean existsByAlbumName(String albumName) {
        if (albumRepository.existsByAlbumName(albumName)) {
            return true;
        }
        return false;
    }


    private void saveAlbumImage(Album album, MultipartFile imageFile) throws IOException {
        if (imageFile != null) {
            Path workFolder = Paths.get(ALBUM_FOLDER + album.getAlbumName())
                    .toAbsolutePath().normalize();
            if (!Files.exists(workFolder)) {
                Files.createDirectories(workFolder);
            }
            Files.deleteIfExists(Paths.get(workFolder + album.getAlbumName() + DOT + JPG_EXSTENSION));
            Files.copy(imageFile.getInputStream(), workFolder.resolve(album.getAlbumName() + DOT + JPG_EXSTENSION), REPLACE_EXISTING);
            album.setImagePoster(setImagePoster(album.getAlbumName()));
            log.info("FILE_SAVED_IN_FILE_SYSTEM" + imageFile.getOriginalFilename());
        }
    }

    private String setImagePoster(String albumName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(
                ALBUM_IMAGE_PATH + albumName + FORWARD_SLASH + albumName + DOT + JPG_EXSTENSION).toUriString();
    }

}
