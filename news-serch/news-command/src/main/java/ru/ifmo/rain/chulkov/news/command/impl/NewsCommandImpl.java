package ru.ifmo.rain.chulkov.news.command.impl;

import org.apache.felix.scr.annotations.*;
import org.apache.felix.scr.annotations.Properties;

import java.util.*;
import static java.util.Map.Entry.comparingByValue;

import ru.ifmo.rain.chulkov.news.abstrparser.NewsParser;
import ru.ifmo.rain.chulkov.news.command.api.NewsCommand;

@SuppressWarnings("deprecation")
@Component
@Service(value = NewsCommand.class)
@Properties({
        @Property(name = "osgi.command.scope", value = "news"),
        @Property(name = "osgi.command.function", value = "stats")
})
public class NewsCommandImpl implements NewsCommand {
    @Reference(
            policy = ReferencePolicy.DYNAMIC,
            referenceInterface = NewsParser.class,
            cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE,
            bind = "setParser",
            unbind = "unsetParser"
    )
    private volatile List<NewsParser> parsers = new ArrayList<>();

    protected void setParser(NewsParser parser) {
        parsers.add(parser);
    }

    protected void unsetParser(NewsParser parser) {
        parsers.remove(parser);
    }

    @Override
    public void stats() {
        if (parsers.isEmpty()) {
            System.out.println("No parsers available");
            return;
        }

        System.out.println("Print \"all\" if you need all, or chose one");
        for (NewsParser p : parsers) {
            System.out.println(p.getName());
        }

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if (answer.equals("all")) {
            Map<String, Integer> stats = new HashMap<>();
            for (NewsParser p : parsers) {
                if (stats.isEmpty()) {
                    stats.putAll(p.parseNews());
                } else {
                    mergeMaps(stats, p.parseNews());
                }
            }
            printStats(stats);
        } else {
            NewsParser chosen = null;
            for (NewsParser p : parsers) {
                if (p.getName().equals(answer)) {
                    chosen = p;
                }
            }
            if (chosen == null) {
                System.out.println("You have chosen invalid token");
                return;
            }
            printStats(chosen.parseNews());
        }
    }

    @Override
    public void stats(String parserName) {
        NewsParser chosen = null;
        for (NewsParser p : parsers) {
            if (p.getName().equals(parserName)) {
                chosen = p;
            }
        }
        if (chosen == null) {
            System.out.println("You have chosen invalid token");
            return;
        }
        printStats(chosen.parseNews());
    }

    private void mergeMaps(Map<String, Integer> left, Map<String, Integer> right) {
        left.forEach(
                (key, value) -> right.merge(key, value, Integer::sum)
        );
    }

    private void printStats(Map<String, Integer> stats) {
        stats.entrySet().stream().sorted(comparingByValue(Comparator.reverseOrder()))
                .limit(10).forEach(System.out::println);
    }

}
