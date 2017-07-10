package com.github.wakingrufus.mastodon.feed;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FeedDefinitionBuilder {
  private final  List<FeedElement> elements;

    public FeedDefinition build(){
        return new FeedDefinition(elements);
    }

    public FeedDefinitionBuilder(){
        elements = new ArrayList<>();
    }

    private FeedDefinitionBuilder(List<FeedElement> elements){
        this.elements = elements;
    }

    public FeedDefinitionBuilder element(FeedElement element){
       List<FeedElement> copy = new ArrayList<>(elements);
        copy.add(element);
        return new FeedDefinitionBuilder(Collections.unmodifiableList(copy));
    }
}
