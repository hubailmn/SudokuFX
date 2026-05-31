package sa.edu.uqu.cs4108.sudokufx.data;

public enum Difficulty {
    FLASH(20, 20),
    EASY(30, 28),
    MEDIUM(40, 56),
    HARD(50, 112),
    EXPERT(60, 156);

    private final int cellsToRemove;
    private final int multiplier;

    Difficulty(int cellsToRemove, int multiplier) {
        this.cellsToRemove = cellsToRemove;
        this.multiplier = multiplier;
    }

    public static Difficulty fromString(String text) {
        return Difficulty.valueOf(text.toUpperCase());
    }

    public int getCellsToRemove() {
        return cellsToRemove;
    }

    public int getMultiplier() {
        return multiplier;
    }

}