package sa.edu.uqu.cs4108.sudokufx.data;

public enum Difficulty {
    FLASH(20), EASY(30), MEDIUM(40), HARD(50), EXPERT(60);

    private final int cellsToRemove;

    Difficulty(int cellsToRemove) {
        this.cellsToRemove = cellsToRemove;
    }

    public int getCellsToRemove() {
        return cellsToRemove;
    }

    public static Difficulty fromString(String text) {
        return Difficulty.valueOf(text.toUpperCase());
    }
}