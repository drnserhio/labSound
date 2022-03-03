package com.sound.labsound.resource;

import com.sound.labsound.exception.AlbumNotFoundException;
import com.sound.labsound.exception.AudioExistsException;
import com.sound.labsound.exception.AudioNotFoundException;
import com.sound.labsound.model.Audio;
import com.sound.labsound.service.AudioService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/audio")
public class AudioResource {

    private final AudioService audioService;

    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadAudio(
            @RequestParam("artist") String artist,
            @RequestParam("album") String album,
            @RequestParam("soundName") String soundName,
            @RequestParam("file")MultipartFile file)
            throws IOException, AudioExistsException, AlbumNotFoundException {
        boolean upload = audioService.uploadAudio(artist, album, soundName, file);
        return new ResponseEntity<>(upload, OK);
    }


    @GetMapping(value = "/get/{soundName}", produces = "audio/mpeg")
    public ResponseEntity<?> getAudio(
            @PathVariable("soundName") String soundName)
            throws AudioExistsException {
        GridFsResource gridFsResource = audioService.getAudioBySoundName(soundName).get();
        return new ResponseEntity<>(gridFsResource, OK);
    }

    @GetMapping("/all_audio_by_artist/{artist}")
    public ResponseEntity<Set<Audio>> getAllAudiosByArtist(
            @PathVariable("artist") String artist) {
        Set<Audio> allAudiosByArtist = audioService.getAllAudiosByArtist(artist).get();
        return new ResponseEntity<>(allAudiosByArtist, OK);
    }

    @GetMapping("/all_audio_by_album/{album}")
    public ResponseEntity<Set<Audio>> getAllAudiosByAlbumName(
            @PathVariable("album") String album) throws AlbumNotFoundException {
        Set<Audio> allAudiosByArtist = audioService.getAllAudiosByAlbumName(album).get();
        return new ResponseEntity<>(allAudiosByArtist, OK);
    }

    @DeleteMapping("/delete_audio/{soundName}")
    public ResponseEntity<Boolean> deleteAudio(
            @PathVariable("soundName") String soundName)
            throws AudioNotFoundException {
        boolean delete = audioService.deleteAudio(soundName);
        return new ResponseEntity<>(delete, OK);
    }

}
