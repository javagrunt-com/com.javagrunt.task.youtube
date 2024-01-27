package com.javagrunt.task.youtube;

import org.springframework.data.annotation.Id;

import java.util.Date;

record YouTubeVideo(
        @Id String id, 
        String link, 
        String description, 
        String title, 
        String thumbnail, 
        String date, 
        long dateVal){}