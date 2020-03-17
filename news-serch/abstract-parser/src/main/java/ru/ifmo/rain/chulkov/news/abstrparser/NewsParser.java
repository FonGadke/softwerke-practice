package ru.ifmo.rain.chulkov.news.abstrparser;

import java.util.Map;

public interface NewsParser {
    Map<String, Integer> parseNews();
    String getName();
}
