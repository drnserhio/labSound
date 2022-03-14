//package com.sound.labsound.cron;
//
//import com.sound.labsound.model.Album;
//import com.sound.labsound.model.Artist;
//import com.sound.labsound.repos.mongo.AlbumRepository;
//import com.sound.labsound.repos.mongo.ArtistRepository;
//import com.sound.labsound.repos.solr.AlbumSolrRepository;
//import com.sound.labsound.repos.solr.ArtistSolrRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class SolrRefreshCron {
//
//    private final ArtistRepository artistRepository;
//    private final AlbumRepository albumRepository;
//    private final AlbumSolrRepository albumSolrRepository;
//    private final ArtistSolrRepository artistSolrRepository;
//
//
//    @Scheduled(fixedDelay = 60000)
//    public void syncSaveAllDataForDataBase() {
//        deleteCache();
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
//}
