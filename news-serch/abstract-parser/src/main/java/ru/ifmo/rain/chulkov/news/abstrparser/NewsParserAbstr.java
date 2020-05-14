package ru.ifmo.rain.chulkov.news.abstrparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class NewsParserAbstr implements NewsParser {
    protected Set<String> prepositions = new HashSet<>(Arrays.asList(
            "о", "об", "по", "под", "от", "около", "за", "на", "над", "с", "к", "из", "в", "без",
            "и", "да", "а", "ни-ни", "но", "или", "либо", "то-то", "то", "не",
            "что", "чтобы", "если", "когда", "будто", "хотя", "пока", "для"
    ));

    protected String getContent(String path) {
        try {
            URL url = new URL(path);

            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
                );
                StringBuilder content = new StringBuilder();

                String inputLine = in.readLine();
                while (inputLine != null) {
                    content.append(inputLine);
                    inputLine = in.readLine();
                }
                in.close();

                return content.toString().toLowerCase();

            } catch (IOException e) {
                System.err.println("Failed to read response");
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + path);
        } catch (IOException e) {
            System.err.println("Failed to connect to \"" + path + "\"");
        }
        return "";
    }


}
