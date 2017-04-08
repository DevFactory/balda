package balda.service.game;

import balda.game.Tile;
import balda.game.Vocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Brought by anatolyd on 08.04.2017.
 */
@Service
@RestController
public class Generator {

    private final Logger log = LoggerFactory.getLogger(Generator.class);

    private final Vocabulary vocabulary;

    @Autowired
    public Generator(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/new-game")
    @ResponseBody
    public List<Tile> generateNewGame() {
        String coolWord = vocabulary.findRandomWord().toUpperCase();
        List<Tile> allTiles = new ArrayList<>();
        int wordLength = coolWord.length();
        boolean isEven = wordLength % 2 == 0;

        int half = wordLength / 2;

        for (int i = 0; i < wordLength + (isEven ? 1 : 0); i++) {
            for (int j = 0; j < wordLength; j++) {
                if (i == half) {
                    allTiles.add(new Tile.TileBuilder(i, j).letter(coolWord.charAt(j)).build());
                } else {
                    allTiles.add(new Tile.TileBuilder(i, j).build());
                }
            }
        }


        log.info("For word " + coolWord + ". There are " + allTiles.size() + " tiles");
        return allTiles;
    }
}
