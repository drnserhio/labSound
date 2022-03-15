package com.sound.labsound.cron;

import com.sound.labsound.model.Album;
import com.sound.labsound.model.Artist;
import com.sound.labsound.repos.elastic.AlbumElasticRepository;
import com.sound.labsound.repos.elastic.ArtistElasticRepository;
import com.sound.labsound.repos.mongo.AlbumRepository;
import com.sound.labsound.repos.mongo.ArtistRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ElsasicsearchRefreshCron {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final ArtistElasticRepository artistElasticRepository;
    private final AlbumElasticRepository albumElasticRepository;


    @Scheduled(fixedDelay = 60000)
    public void syncSaveAllDataForDataBase() {
        deleteCache();
        saveCache();
    }

    private void saveCache() {
        List<Artist> artists = artistRepository.findAll();
        List<Album> albums = albumRepository.findAll();
        artistElasticRepository.saveAll(artists);
        albumElasticRepository.saveAll(albums);
    }
    private void deleteCache() {
        artistElasticRepository.deleteAll();
    }
}
