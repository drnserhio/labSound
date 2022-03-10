package com.sound.labsound.resource;

import com.sound.labsound.exception.domain.ArtistExistsException;
import com.sound.labsound.exception.domain.ArtistNotFoundException;
import com.sound.labsound.model.Artist;
import com.sound.labsound.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.sound.labsound.service.impl.AlbumServiceImpl.FORWARD_SLASH;
import static com.sound.labsound.service.impl.ArtistServiceImpl.ARTIST_FOLDER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/artist")
public class ArtistResource {

    private final ArtistService artistService;


    @PostMapping("/create")
    public ResponseEntity<Artist> createArtist(
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("artist") String artist,
            @RequestParam("title") String title)
            throws ArtistExistsException, IOException {
        Artist art = artistService.createArtist(fileImage, artist, title);
        return new ResponseEntity<>(art, OK);
    }

    @PostMapping("/create_artist")
    public ResponseEntity<Artist> createArtist(
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("artist") String artist)
            throws ArtistExistsException, IOException {
        Artist art = artistService.createArtist(fileImage, artist);
        return new ResponseEntity<>(art, OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Artist> updateArtist(
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("artist") String artist,
            @RequestParam("title") String title)
            throws ArtistNotFoundException, IOException {
        Artist art = artistService.updateArtist(fileImage, artist, title);
        return new ResponseEntity<>(art, OK);
    }

    @PutMapping("/update_artist")
    public ResponseEntity<Artist> updateArtist(
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("artist") String artist)
            throws ArtistNotFoundException, IOException {
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

    @GetMapping(path = "image/{artist}/{filename}", produces = IMAGE_JPEG_VALUE)
    public byte[] getAlbumImage(
            @PathVariable("artist") String artist,
            @PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(ARTIST_FOLDER + artist + FORWARD_SLASH + filename));
    }

    @GetMapping("/all_artist")
    public ResponseEntity<List<Artist>> getAllArtist() {
        List<Artist> artists = artistService.findAll();
        return new ResponseEntity<>(artists, OK);
    }
}
