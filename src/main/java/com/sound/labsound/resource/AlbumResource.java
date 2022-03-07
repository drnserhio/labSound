package com.sound.labsound.resource;

import com.sound.labsound.exception.domain.AlbumExistsException;
import com.sound.labsound.exception.domain.AlbumNotFoundException;
import com.sound.labsound.exception.domain.ArtistNotFoundException;
import com.sound.labsound.model.Album;
import com.sound.labsound.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static com.sound.labsound.service.impl.AlbumServiceImpl.ALBUM_FOLDER;
import static com.sound.labsound.service.impl.AlbumServiceImpl.FORWARD_SLASH;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/album")
@CrossOrigin("http://localhost:4200")
public class AlbumResource {

    private final AlbumService albumService;


    @PostMapping("/create_album/{albumName}")
    public ResponseEntity<Album> createAlbum(
            @RequestParam("imageFile") MultipartFile imageFile,
            @PathVariable("albumName") String albumName,
            @RequestParam("artist") String artist,
            @RequestParam("yearRelease") String yearRelease)
            throws AlbumExistsException, ArtistNotFoundException, IOException {
        Album album = albumService.createAlbum(imageFile, albumName, artist, yearRelease);
        return new ResponseEntity<>(album, OK);
    }

    @PutMapping("/update_album/{albumName}")
    public ResponseEntity<Album> updateAlbum(
            @RequestParam("imageFile") MultipartFile imageFile,
            @PathVariable("albumName") String albumName,
            @RequestParam("artist") String artist,
            @RequestParam("yearRelease") String yearRelease)
            throws AlbumNotFoundException, ArtistNotFoundException, IOException {
        Album album = albumService.updateAlbum(imageFile, albumName, artist, yearRelease);
        return new ResponseEntity<>(album, CREATED);
    }


    @GetMapping("/get_album/{albumName}")
    public ResponseEntity<Album> findByAlbum(
            @PathVariable("albumName") String albumName) {
        Album album = albumService.findByAlbumName(albumName);
        return new ResponseEntity<>(album, OK);
    }

    @GetMapping("/get_all_album_artist/{artist}")
    public ResponseEntity<Set<Album>> findAllByArtist(
            @PathVariable("artist") String artist)
            throws ArtistNotFoundException {
        Set<Album> albums = albumService.findAllByArtist(artist);
        return new ResponseEntity<>(albums, OK);
    }

    @GetMapping("/all_albums")
    public ResponseEntity<List<Album>> findAllAlbum() {
        List<Album> all = albumService.findAll();
        return new ResponseEntity<>(all, OK);
    }

    @DeleteMapping("/delete_album/{album}")
    public ResponseEntity<Boolean> deleteAlbum(
            @PathVariable("album") String album)
            throws AlbumNotFoundException {
        boolean delete = albumService.deleteAlbum(album);
        return new ResponseEntity<>(delete, OK);
    }

    @GetMapping(path = "image/{albumName}/{filename}", produces = IMAGE_JPEG_VALUE)
    public byte[] getAlbumImage(
            @PathVariable("albumName") String albumName,
            @PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(ALBUM_FOLDER + albumName + FORWARD_SLASH + filename));
    }
}
