package com.javagrunt.task.youtube;

import org.springframework.aot.hint.*;

import java.util.HashSet;
import java.util.Set;

public class RunTimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(com.rometools.modules.atom.io.AtomModuleParser.class);
        classes.add(com.rometools.modules.base.io.CustomTagParser.class);
        classes.add(com.rometools.modules.base.io.GoogleBaseParser.class);
        classes.add(com.rometools.modules.cc.io.ModuleParserRSS1.class);
        classes.add(com.rometools.modules.cc.io.ModuleParserRSS2.class);
        classes.add(com.rometools.modules.content.io.ContentModuleParser.class);
        classes.add(com.rometools.modules.feedpress.io.FeedpressParser.class);
        classes.add(com.rometools.modules.fyyd.io.FyydParser.class);
        classes.add(com.rometools.modules.georss.SimpleParser.class);
        classes.add(com.rometools.modules.georss.W3CGeoParser.class);
        classes.add(com.rometools.modules.itunes.io.ITunesParser.class);
        classes.add(com.rometools.modules.itunes.io.ITunesParserOldNamespace.class);
        classes.add(com.rometools.modules.mediarss.io.AlternateMediaModuleParser.class);
        classes.add(com.rometools.modules.mediarss.io.MediaModuleParser.class);
        classes.add(com.rometools.modules.mediarss.io.RSS20YahooParser.class);
        classes.add(com.rometools.modules.opensearch.impl.OpenSearchModuleParser.class);
        classes.add(com.rometools.modules.photocast.io.Parser.class);
        classes.add(com.rometools.modules.psc.io.PodloveSimpleChapterParser.class);
        classes.add(com.rometools.modules.slash.io.SlashModuleParser.class);
        classes.add(com.rometools.modules.sle.io.ItemParser.class);
        classes.add(com.rometools.modules.sle.io.ModuleParser.class);
        classes.add(com.rometools.modules.thr.io.ThreadingModuleParser.class);
        classes.add(com.rometools.modules.yahooweather.io.WeatherModuleParser.class);
        try {
            for (Class<?> c : classes) {
                hints.reflection().registerType(TypeReference.of(c.getName()),
                        builder -> builder
                                .withMembers(MemberCategory.values()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
