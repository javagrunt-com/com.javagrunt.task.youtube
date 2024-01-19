package com.javagrunt.task.youtube;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.modules.mediarss.types.MediaGroup;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
class YouTubeFeedRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(YouTubeFeedRunner.class);
    
    @Value("${youtube.feed.url}")
    private String YOUTUBE_FEED_URL;

    private final YouTubeVideoRepository youTubeVideoRepository;
    private final JdbcAggregateTemplate template;
    private final RestClient restClient;


    YouTubeFeedRunner(YouTubeVideoRepository youTubeVideoRepository, JdbcAggregateTemplate template) {
        this.youTubeVideoRepository = youTubeVideoRepository;
        this.template = template;
        this.restClient = RestClient.create();
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("YouTube Feed URL: " + YOUTUBE_FEED_URL);
        try {
            String result = fetchFeedData();
            processFeed(result);
        } catch (Exception e) {
            logger.error("Error in processing YouTube feed", e);
        }
    }

    private String fetchFeedData() {
        return restClient.get()
                .uri(YOUTUBE_FEED_URL)
                .retrieve()
                .body(String.class);
    }

    private void processFeed(String feedData) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new ByteArrayInputStream(feedData.getBytes(StandardCharsets.UTF_8))));
        feed.getEntries().forEach(this::processEntry);
    }

    private void processEntry(SyndEntry entry) {
        logger.debug(entry.toString());
        YouTubeVideo youTubeVideo = createNewYouTubeVideo(entry);
        saveYouTubeVideo(youTubeVideo);
    }

    private YouTubeVideo createNewYouTubeVideo(SyndEntry entry) {
        return parseEntry(entry);
    }

    private YouTubeVideo parseEntry(SyndEntry entry) {
        com.rometools.rome.feed.module.Module module = entry.getModule(MediaEntryModule.URI);
        MediaGroup[] mediaGroups = ((MediaEntryModule) module).getMediaGroups();
        logger.debug("Media Groups: " + mediaGroups.length);
        
        String thumbnail;
        String description;
        if (mediaGroups.length > 0) {
            thumbnail = mediaGroups[0].getMetadata().getThumbnail()[0].getUrl().toString();
            description = mediaGroups[0].getMetadata().getDescription();
        } else {
            thumbnail = "";
            description = "";
        }
        return new YouTubeVideo(entry.getUri(), entry.getLink(), description, entry.getTitle(), thumbnail, entry.getPublishedDate().toString());
    }

    private String parseDescription(SyndEntry entry) {
        if (entry.getDescription() != null && entry.getDescription().getValue() != null) {
            return entry.getDescription().getValue();
        } else {
            return "";
        }
    }

    private void saveYouTubeVideo(YouTubeVideo video) {
        try {
            if (youTubeVideoRepository.findById(video.id()).isEmpty()) {
                template.insert(video);
            } else {
                youTubeVideoRepository.save(video);
            }
            logger.info("Saved video: " + video);
        } catch (Exception e) {
            logger.error("Error saving video: " + video.toString(), e);
        }
    }
}