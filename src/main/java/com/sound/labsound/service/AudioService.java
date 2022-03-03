package com.sound.labsound.service;

import com.sound.labsound.exception.domain.AlbumNotFoundException;
import com.sound.labsound.exception.domain.AudioExistsException;
import com.sound.labsound.exception.domain.AudioNotFoundException;
import com.sound.labsound.model.Audio;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface AudioService {

    boolean uploadAudio(String artist, String albumName, String soundName, MultipartFile file) throws AudioExistsException, IOException, AlbumNotFoundException;

    boolean deleteAudio(String soundName) throws AudioNotFoundException;
    Optional<GridFsResource> getAudioBySoundName(String soundName) throws AudioExistsException;
    Optional<Set<Audio>> getAllAudiosByAlbumName(String albumName) throws AlbumNotFoundException;
    Optional<Set<Audio>> getAllAudiosByArtist(String artist);

    boolean isExistsBySoundName(String soundName);
//    boolean isExistsByArtist(String artist);
//    boolean isExistsByAlbum(String albumName);
    //TODO: refactor create album, artist entity

}
