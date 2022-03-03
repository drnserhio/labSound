package com.sound.labsound.service.impl;

import com.sound.labsound.exception.ArtistExistsException;
import com.sound.labsound.exception.ArtistNotFoundException;
import com.sound.labsound.model.Album;
import com.sound.labsound.model.Artist;
import com.sound.labsound.model.Audio;
import com.sound.labsound.repos.AlbumRepository;
import com.sound.labsound.repos.AtristRepository;
import com.sound.labsound.repos.AudioRepository;
import com.sound.labsound.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final AtristRepository atristRepository;
    private final AlbumRepository albumRepository;
    private final AudioRepository audioRepository;

    @Override
    public Artist createArtist(MultipartFile fileImage, String artist, String title)
            throws ArtistExistsException {
        if (existsByArtist(artist)) {
            throw new ArtistExistsException("Artist already exists.");
        }
        Artist art = new Artist();
        art.setArtist(artist);
        art.setTitle(title);
        art.setCountSound(0);
        Artist save = atristRepository.save(art);
        //TODO: add save image
        return save;
    }

    @Override
    public Artist createArtist(MultipartFile fileImage, String artist)
            throws ArtistExistsException {
        if (existsByArtist(artist)) {
            throw new ArtistExistsException("Artist already exists.");
        }
        Artist art = new Artist();
        art.setArtist(artist);
        art.setTitle("...?");
        art.setCountSound(0);
        Artist save = atristRepository.save(art);
        //TODO: add save image
        return save;
    }

    @Override
    public Artist updateArtist(MultipartFile fileImage, String artist, String title)
            throws ArtistNotFoundException {
        if (!existsByArtist(artist)) {
            throw new ArtistNotFoundException("Artist not found.");
        }
        Artist art = atristRepository.findByArtist(artist);
        art.setArtist(artist);
        art.setTitle(title);
        Artist save = atristRepository.save(art);
        //TODO: add save image
        return save;
    }

    @Override
    public Artist updateArtist(MultipartFile fileImage, String artist)
            throws ArtistNotFoundException {
        if (!existsByArtist(artist)) {
            throw new ArtistNotFoundException("Artist not found.");
        }
        Artist art = atristRepository.findByArtist(artist);
        art.setArtist(artist);
        Artist save = atristRepository.save(art);
        //TODO: add save image
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
        atristRepository.delete(artist);
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
        if (atristRepository.existsByArtist(artistName)) {
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
        Artist artist = atristRepository.findByArtist(artistName);
        return artist;
    }
}
