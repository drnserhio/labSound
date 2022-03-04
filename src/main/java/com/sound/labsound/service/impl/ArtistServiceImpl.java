package com.sound.labsound.service.impl;

import com.sound.labsound.exception.domain.ArtistExistsException;
import com.sound.labsound.exception.domain.ArtistNotFoundException;
import com.sound.labsound.model.Album;
import com.sound.labsound.model.Artist;
import com.sound.labsound.model.Audio;
import com.sound.labsound.repos.AlbumRepository;
import com.sound.labsound.repos.ArtistRepository;
import com.sound.labsound.repos.AudioRepository;
import com.sound.labsound.service.ArtistService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@AllArgsConstructor
@Slf4j
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final AudioRepository audioRepository;


    public static final String ARTIST_IMAGE_PATH = "/artist/image/";
    public static final String FORWARD_SLASH = "/";
    public static final String DOT = "."; // filename.jpg
    public static final String JPG_EXSTENSION = "jpg";

    public static final String ARTIST_FOLDER = System.getProperty("user.home") + "/labSound/artist/";

    @Override
    public Artist createArtist(MultipartFile fileImage, String artist, String title)
            throws ArtistExistsException, IOException {
        if (existsByArtist(artist)) {
            throw new ArtistExistsException("Artist already exists.");
        }
        Artist art = new Artist();
        art.setArtist(artist);
        art.setTitle(title);
        art.setCountSound(0);
        saveImage(art, fileImage);
        Artist save = artistRepository.save(art);
        return save;
    }

    @Override
    public Artist createArtist(MultipartFile fileImage, String artist)
            throws ArtistExistsException, IOException {
        if (existsByArtist(artist)) {
            throw new ArtistExistsException("Artist already exists.");
        }
        Artist art = new Artist();
        art.setArtist(artist);
        art.setTitle("...?");
        art.setCountSound(0);
        saveImage(art, fileImage);
        Artist save = artistRepository.save(art);
        return save;
    }

    @Override
    public Artist updateArtist(MultipartFile fileImage, String artist, String title)
            throws ArtistNotFoundException, IOException {
        if (!existsByArtist(artist)) {
            throw new ArtistNotFoundException("Artist not found.");
        }
        Artist art = artistRepository.findByArtist(artist);
        art.setArtist(artist);
        art.setTitle(title);
        saveImage(art, fileImage);
        Artist save = artistRepository.save(art);
        return save;
    }

    @Override
    public Artist updateArtist(MultipartFile fileImage, String artist)
            throws ArtistNotFoundException, IOException {
        if (!existsByArtist(artist)) {
            throw new ArtistNotFoundException("Artist not found.");
        }
        Artist art = artistRepository.findByArtist(artist);
        art.setArtist(artist);
        saveImage(art, fileImage);
        Artist save = artistRepository.save(art);
        return save;
    }

    @Override
    public boolean deleteArtist(String artistName)
            throws ArtistNotFoundException {
        if (!existsByArtist(artistName)) {
            throw new ArtistNotFoundException("Artist not found.");
        }
        deleteInAlbum(artistName);
        deleteInAudio(artistName);
        Artist artist = findByArtist(artistName);
        artistRepository.delete(artist);
        return true;
    }

    private void deleteInAudio(String artistName) {
        Set<Audio> allByArtistContaining1 = audioRepository.findAllByArtistContaining(artistName);
        audioRepository.deleteAll(allByArtistContaining1);
    }

    private void deleteInAlbum(String artistName) {
        Set<Album> allByArtistContaining = albumRepository.findAllByArtistContaining(artistName);
        albumRepository.deleteAll(allByArtistContaining);
    }

    @Override
    public boolean existsByArtist(String artistName) {
        if (artistRepository.existsByArtist(artistName)) {
            return true;
        }
        return false;
    }

    @Override
    public Artist findByArtist(String artistName)
            throws ArtistNotFoundException {
        if (!existsByArtist(artistName)) {
            throw new ArtistNotFoundException("Artist not found.");
        }
        Artist artist = artistRepository.findByArtist(artistName);
        return artist;
    }

    private void saveImage(Artist artist, MultipartFile imageFile) throws IOException {
        if (imageFile != null) {
            Path workFolder = Paths.get(ARTIST_FOLDER + artist.getArtist())
                    .toAbsolutePath().normalize();
            if (!Files.exists(workFolder)) {
                Files.createDirectories(workFolder);
            }
            Files.deleteIfExists(Paths.get(workFolder + artist.getArtist() + DOT + JPG_EXSTENSION));
            Files.copy(imageFile.getInputStream(), workFolder.resolve(artist.getArtist() + DOT + JPG_EXSTENSION), REPLACE_EXISTING);
            artist.setImage(setImage(artist.getArtist()));
            log.info("FILE_SAVED_IN_FILE_SYSTEM " + imageFile.getOriginalFilename());
        }
    }

    private String setImage(String albumName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(
                ARTIST_IMAGE_PATH + albumName + FORWARD_SLASH + albumName + DOT + JPG_EXSTENSION).toUriString();
    }

}
