package balda.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Brought by anatolyd on 08.04.2017.
 */
@Component
public class OzhegovVocabulary implements Vocabulary {

    public static final String SOURCE_FILE = "OZHEGOV.TXT";
    public static final List<String> NON_NOUN_SUFFIXES =
            Arrays.asList("ая", "ий", "ый", "ое", "ые", "ой", "ся", "ся", "ть", "шь", "ую", "уй", "те");
    private final Logger log = LoggerFactory.getLogger(OzhegovVocabulary.class);

    Map<String, String> vocabulary = new HashMap<>();

    @PostConstruct
    public void initVocabulary() {
        try (ZipInputStream contentsStream = new ZipInputStream(this.getClass().getResourceAsStream("/ozhegovw.zip"))) {
            ZipEntry vocubularyContents = contentsStream.getNextEntry();
            if (vocubularyContents == null || !SOURCE_FILE.equals(vocubularyContents.getName())) {
                log.error(SOURCE_FILE + " is not found");
                return;
            }
            String name = vocubularyContents.getName();
            log.info("Found " + name);

            BufferedReader buffered = new BufferedReader(new InputStreamReader(contentsStream, "windows-1251"));
            String headers = buffered.readLine();
            List<String> header = Arrays.asList(headers.split("\\|"));


            int wordIndex = header.indexOf("VOCAB");

            int definitionIndex = header.indexOf("DEF");

            String nextLine;
            while ((nextLine = buffered.readLine()) != null) {
                String[] line = nextLine.split("\\|");
                String word = line[wordIndex].toLowerCase();
                if (isValidForGame(word)) {
                    vocabulary.put(word, line[definitionIndex]);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    private boolean isValidForGame(String word) {
        if (word.length() < 2)
            return false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) < 'а' || word.charAt(i) > 'я') {
                return false;
            }
        }

        return !NON_NOUN_SUFFIXES.contains(word.substring(word.length() - 2));
    }

    @Override
    public String findRandomWord() {
        int random = new Random().nextInt(vocabulary.size());
        int counter = 0;
        for (Map.Entry<String, String> stringStringEntry : vocabulary.entrySet()) {
            if (random == counter++) {
                return stringStringEntry.getKey();
            }
        }
        return "NOTFOUND";
    }

    @Override
    public String getDescription(String word) {
        return vocabulary.get(word.toLowerCase());
    }
}
