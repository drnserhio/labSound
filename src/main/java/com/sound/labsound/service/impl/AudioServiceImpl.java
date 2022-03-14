package com.sound.labsound.service.impl;

import com.sound.labsound.exception.domain.AlbumNotFoundException;
import com.sound.labsound.exception.domain.AudioExistsException;
import com.sound.labsound.exception.domain.AudioNotFoundException;
import com.sound.labsound.model.Audio;
import com.sound.labsound.repos.mongo.AlbumRepository;
import com.sound.labsound.repos.mongo.AudioRepository;
import com.sound.labsound.service.AudioService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AudioServiceImpl implements AudioService {

    private final AudioRepository audioRepository;
    private final AlbumRepository albumRepository;
    private final GridFsTemplate gridFsTemplate;


    @Override
    public boolean uploadAudio(String artist, String albumName, String soundName, MultipartFile file)
            throws AudioExistsException, AlbumNotFoundException {
        if (file.isEmpty()) {
            throw new AudioExistsException("Audio file empty or damage upload.");
        }
        if (isExistsBySoundName(soundName)) {
            throw new AudioExistsException("Audio sound name already exists.");
        }
        if (!isExistsByAlbumName(albumName)) {
            throw new AlbumNotFoundException("Album not found.");
        }
        Audio audio = new Audio();
        audio.setArtist(artist);
        audio.setAlbumName(albumName);
        audio.setSoundName(soundName);
        try {
            gridFsTemplate.store(file.getInputStream(), soundName);
            audioRepository.save(audio);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAudio(String soundName)
            throws AudioNotFoundException {
        if(!isExistsBySoundName(soundName)) {
            throw new AudioNotFoundException("Audio not found.");
        }
        Audio audio = audioRepository.findBySoundName(soundName);
        audioRepository.delete(audio);
        gridFsTemplate.delete(Query.query(Criteria.where("soundName").is(audio.getSoundName())));
        return true;
    }

    private boolean isExistsByAlbumName(String albumName) {
        if (albumRepository.existsByAlbumName(albumName)) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<GridFsResource> getAudioBySoundName(String soundName)
            throws AudioExistsException {
        Optional<GridFsResource> gridFsResource = Optional.empty();
        if (!audioRepository.existsBySoundName(soundName)) {
            throw new AudioExistsException("Audio with sound name: " + soundName + " not exists.");
        }
        try {
         gridFsResource = Optional.ofNullable(gridFsTemplate.getResource(soundName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridFsResource;
    }

    @Override
    public Optional<Set<Audio>> getAllAudiosByAlbumName(String albumName)
            throws AlbumNotFoundException, AudioExistsException {
        Optional<Set<Audio>> audio = Optional.empty();
        if (!isExistsByAlbumName(albumName)) {
            throw new AlbumNotFoundException("Album not found");
        }
        try {
          audio = Optional.ofNullable(audioRepository.findAllByAlbumNameContaining(albumName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (audio.get().size() == 0) {
            throw new AudioExistsException("Album don't have sound.");
        }
        return audio;
    }

    @Override
    public Optional<Set<Audio>> getAllAudiosByArtist(String artist) {
        Optional<Set<Audio>> audio = Optional.empty();
        try {
            audio = Optional.ofNullable(audioRepository.findAllByArtistContaining(artist));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audio;
    }

    @Override
    public boolean isExistsBySoundName(String soundName) {
        if (audioRepository.existsBySoundName(soundName)) {
            return true;
        }
        return false;
    }
}
