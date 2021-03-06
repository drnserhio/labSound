package com.sound.labsound.resource;

import com.sound.labsound.exception.domain.AlbumNotFoundException;
import com.sound.labsound.exception.domain.AudioExistsException;
import com.sound.labsound.exception.domain.AudioNotFoundException;
import com.sound.labsound.model.Audio;
import com.sound.labsound.service.AudioService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> getAudio(
            @PathVariable("soundName") String soundName)
            throws AudioExistsException {
        GridFsResource gridFsResource = audioService.getAudioBySoundName(soundName).get();
        return new ResponseEntity<>(gridFsResource, OK);
    }

    @GetMapping("/all_audio_by_artist/{artist}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<Set<Audio>> getAllAudiosByArtist(
            @PathVariable("artist") String artist) {
        Set<Audio> allAudiosByArtist = audioService.getAllAudiosByArtist(artist).get();
        return new ResponseEntity<>(allAudiosByArtist, OK);
    }

    @GetMapping("/all_audio_by_album/{album}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<Set<Audio>> getAllAudiosByAlbumName(
            @PathVariable("album") String album) throws AlbumNotFoundException, AudioExistsException {
        Set<Audio> allAudiosByArtist = audioService.getAllAudiosByAlbumName(album).get();
        return new ResponseEntity<>(allAudiosByArtist, OK);
    }

    @DeleteMapping("/delete_audio/{soundName}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<Boolean> deleteAudio(
            @PathVariable("soundName") String soundName)
            throws AudioNotFoundException {
        boolean delete = audioService.deleteAudio(soundName);
        return new ResponseEntity<>(delete, OK);
    }

}
