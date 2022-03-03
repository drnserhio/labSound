package com.sound.labsound.resource;

import com.sound.labsound.exception.ArtistExistsException;
import com.sound.labsound.exception.ArtistNotFoundException;
import com.sound.labsound.model.Album;
import com.sound.labsound.model.Artist;
import com.sound.labsound.model.Audio;
import com.sound.labsound.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/artist")
public class ArtistResource {

    private final ArtistService artistService;


    @PostMapping("/create_artist")
    public ResponseEntity<Artist> createArtist(
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("artist") String artist,
            @RequestParam("title") String title)
            throws ArtistExistsException {
        Artist art = artistService.createArtist(fileImage, artist, title);
        return new ResponseEntity<>(art, OK);
    }

    @PostMapping("/create_artist")
    public ResponseEntity<Artist> createArtist(
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("artist") String artist)
            throws ArtistExistsException {
        Artist art = artistService.createArtist(fileImage, artist);
        return new ResponseEntity<>(art, OK);
    }

    @PutMapping("/update_artist")
    public ResponseEntity<Artist> updateArtist(
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("artist") String artist,
            @RequestParam("title") String title)
            throws ArtistNotFoundException {
        Artist art = artistService.updateArtist(fileImage, artist, title);
        return new ResponseEntity<>(art, OK);
    }

    @PutMapping("/update_artist")
    public ResponseEntity<Artist> updateArtist(
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("artist") String artist)
            throws ArtistNotFoundException {
        Artist art = artistService.updateArtist(fileImage, artist);
        return new ResponseEntity<>(art, OK);
    }

    @DeleteMapping("/delete_artist/{artistName}")
    public ResponseEntity<Boolean> deleteArtist(
            @PathVariable("artistName") String artistName)
            throws ArtistNotFoundException {
        boolean delete = artistService.deleteArtist(artistName);
        return new ResponseEntity<>(delete, OK);
    }


    @GetMapping("/get_artist/{artistName}")
    public ResponseEntity<Artist> findByArtist(
            @PathVariable("artistName") String artistName)
            throws ArtistNotFoundException {
        Artist art = artistService.findByArtist(artistName);
        return new ResponseEntity<>(art, OK);
    }
}
