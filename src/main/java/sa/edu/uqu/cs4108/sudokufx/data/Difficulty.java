package sa.edu.uqu.cs4108.sudokufx.data;

public enum Difficulty {
    FLASH(25),
    EASY(35),
    MEDIUM(45),
    HARD(55),
    EXPERT(65);

    final int missingCells;

    Difficulty(int missingCells) {
        this.missingCells = missingCells;
    }

    public int getMissingCells() {
        return missingCells;
    }

    public Difficulty getDifficulty(String difficulty) {
        return Difficulty.valueOf(difficulty.toUpperCase());
    }

}
