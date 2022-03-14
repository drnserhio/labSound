//package com.sound.labsound.resource;
//
//import com.sound.labsound.model.Album;
//import com.sound.labsound.model.Artist;
//
//import com.sound.labsound.repos.mongo.AlbumRepository;
//import com.sound.labsound.repos.mongo.ArtistRepository;
//import com.sound.labsound.repos.solr.AlbumSolrRepository;
//import com.sound.labsound.repos.solr.ArtistSolrRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//import static org.springframework.http.HttpStatus.OK;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/solr")
//public class SolrResource {
//
//
//    private final AlbumSolrRepository albumSolrRepository;
//    private final ArtistSolrRepository artistSolrRepository;
//
//
//    private final ArtistRepository artistRepository;
//    private final AlbumRepository albumRepository;
//
//
//    @PostConstruct
//    public void syncSaveAllDataForDataBase() {
////        deleteCache();
//        saveCache();
//    }
//
//    private void saveCache() {
//        List<Album> albums = albumRepository.findAll();
//        List<Artist> artists = artistRepository.findAll();
//        albumSolrRepository.saveAll(albums);
//        artistSolrRepository.saveAll(artists);
//    }
//    private void deleteCache() {
//        albumSolrRepository.deleteAll();
//        artistSolrRepository.deleteAll();
//    }
//
//
//
//    @GetMapping(value = "/get/artist/{searchTerm}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Artist>> serchTermArtist(
//            @PathVariable("searchTerm") String searchTerm) {
//        List<Artist> artists = artistSolrRepository.findArtistBySearchTerm(searchTerm);
//        return new ResponseEntity<>(artists, OK);
//    }
//
//    @GetMapping(value = "/get/album/{searchTerm}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Album>> serchTermAlbum(
//            @PathVariable("searchTerm") String searchTerm) {
//        List<Album> album = albumSolrRepository.findAlbumBySearchTerm(searchTerm);
//        return new ResponseEntity<>(album, OK);
//    }
//}
