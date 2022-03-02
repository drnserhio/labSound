package com.sound.labsound.service;

import com.sound.labsound.exception.AudioExistsException;
import com.sound.labsound.model.Audio;
import com.sound.labsound.repos.AudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AudioServiceImpl implements AudioService{

    private final AudioRepository audioRepository;
    private final GridFsTemplate gridFsTemplate;


    @Override
    public boolean uploadAudio(String artist, String albumName, String soundName, MultipartFile file)
            throws AudioExistsException {
        if (file.isEmpty()) {
            throw new AudioExistsException("Audio file empty or damage upload.");
        }
        if (isExistsBySoundName(soundName)) {
            throw new AudioExistsException("Audio sound name already exists.");
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
    public Optional<Set<Audio>> getAllAudiosByAlbumName(String albumName) {
        Optional<Set<Audio>> audio = Optional.empty();
        try {
          audio = Optional.ofNullable(audioRepository.findAllByAlbumNameContaining(albumName));
        } catch (Exception e) {
            e.printStackTrace();
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
