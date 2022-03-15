package com.sound.labsound.resource;

import com.sound.labsound.model.Album;
import com.sound.labsound.model.Artist;
import com.sound.labsound.service.AlbumElasticsearchService;
import com.sound.labsound.service.ArtistElasicsearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/search")
public class ElasticsearchResource {

    private final ArtistElasicsearchService artistElasicsearch;
    private final AlbumElasticsearchService albumElasticsearch;


    @GetMapping("/get/artists/{searchTerm}")
    public ResponseEntity<List<Artist>> searchTermArtist(
            @PathVariable("searchTerm") String searchTerm) {
        List<Artist> artists = artistElasicsearch.findAllByArtist(searchTerm);
        return new ResponseEntity<>(artists, OK);
    }

    @GetMapping("/get/albums/{searchTerm}")
    public ResponseEntity<List<Album>> searchTermAlbum(
            @PathVariable("searchTerm") String searchTerm) {
        List<Album> albums = albumElasticsearch.findAllByAlbum(searchTerm);
        return new ResponseEntity<>(albums, OK);
    }

}
