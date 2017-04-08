package balda.game;

/**
 * Brought by anatolyd on 08.04.2017.
 */
public class Tile {

    private final int row;
    private final int column;
    private final char letter;

    private Tile(TileBuilder builder) {
        this.row = builder.row;
        this.column = builder.column;
        this.letter = builder.letter;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getLetter() {
        return letter;
    }

    /**
     * Brought by anatolyd on 08.04.2017.
     */
    public static class TileBuilder {

        private final int row;
        private final int column;
        private char letter = ' ';

        public TileBuilder(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public TileBuilder letter(char letter) {
            this.letter = letter;
            return this;
        }

        public Tile build() {
            return new Tile(this);
        }
    }

    @Override
    public String toString() {
        return "Tile{" +
                "row=" + row +
                ", column=" + column +
                ", letter=" + letter +
                '}';
    }
}
