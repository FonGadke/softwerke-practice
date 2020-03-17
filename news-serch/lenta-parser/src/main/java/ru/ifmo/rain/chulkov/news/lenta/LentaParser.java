package ru.ifmo.rain.chulkov.news.lenta;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import ru.ifmo.rain.chulkov.news.abstrparser.NewsParser;
import ru.ifmo.rain.chulkov.news.abstrparser.NewsParserAbstr;
import ru.ifmo.rain.chulkov.news.lenta.util.ArticleInfo;

import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
@Component()
@Service(value = NewsParser.class)
public class LentaParser extends NewsParserAbstr {
    private final String url = "https://api.lenta.ru/lists/latest";

    @Override
    public Map<String, Integer> parseNews() {
        Map<String, Integer> result = new HashMap<>();
        String content = getContent(url);
        content = content.substring(content.indexOf('['), content.length() - 1);

        ArticleInfo[] info = new Gson().fromJson(content, ArticleInfo[].class);

        for (ArticleInfo i: info) {
            String[] words = i.toString().split("[\\p{Blank}\\p{Punct}]");
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
        return "Lenta.ru";
    }
}
