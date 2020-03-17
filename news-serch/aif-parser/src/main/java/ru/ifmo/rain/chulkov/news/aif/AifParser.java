package ru.ifmo.rain.chulkov.news.aif;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.jsoup.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import ru.ifmo.rain.chulkov.news.abstrparser.NewsParser;
import ru.ifmo.rain.chulkov.news.abstrparser.NewsParserAbstr;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
@Component()
@Service(value = NewsParser.class)
public class AifParser extends NewsParserAbstr {
    private final String url = "https://aif.ru/rss/news.php";

    @Override
    public Map<String, Integer> parseNews() {
        Map<String, Integer> result = new HashMap<>();
        String content = getContent(url);

        Document document = Jsoup.parse(content, "", Parser.xmlParser());
        List<Element> titles = new ArrayList<>(document.select("title"));

        titles.remove(0);
        List<String> strTitles = titles.stream()
                .map(Objects::toString)
                .map(title -> title.substring(16))
                .map(title -> title.substring(0, title.length() - 11))
                .collect(Collectors.toList());

        for (String title: strTitles) {
            String[] words = title.split("[\\p{Blank}\\p{Punct}]");
            for (String w: words) {
                String word = w.toLowerCase();
                if (!prepositions.contains(word)) {
                    if (result.containsKey(word)) {
                        result.replace(word, result.get(word) + 1);
                    } else {
                        result.put(word, 1);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "aif.ru";
    }
}
