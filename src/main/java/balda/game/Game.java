package balda.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Brought by anatolyd on 08.04.2017.
 */
public class Game {

    private final Logger log = LoggerFactory.getLogger(Game.class);

    private String chosenWord;
    private String description;
    private List<Tile> tiles;

    public Game(Vocabulary vocabulary) {
        chosenWord = vocabulary.findRandomWord().toUpperCase();
        description = vocabulary.getDescription(chosenWord);
        List<Tile> allTiles = new ArrayList<>();
        int wordLength = chosenWord.length();
        boolean isEven = wordLength % 2 == 0;

        int half = wordLength / 2;

        for (int i = 0; i < wordLength + (isEven ? 1 : 0); i++) {
            for (int j = 0; j < wordLength; j++) {
                if (i == half) {
                    allTiles.add(new Tile.TileBuilder(i, j).letter(chosenWord.charAt(j)).build());
                } else {
                    allTiles.add(new Tile.TileBuilder(i, j).build());
                }
            }
        }

        tiles = allTiles;
        log.info("For word " + chosenWord + ". There are " + allTiles.size() + " tiles");
    }

    public String getChosenWord() {
        return chosenWord;
    }

    public void setChosenWord(String chosenWord) {
        this.chosenWord = chosenWord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
