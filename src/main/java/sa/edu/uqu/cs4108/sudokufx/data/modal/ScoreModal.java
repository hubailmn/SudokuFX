package sa.edu.uqu.cs4108.sudokufx.data.modal;

import sa.edu.uqu.cs4108.sudokufx.data.Difficulty;

public class ScoreModal {

    int id;

    long score;

    Difficulty difficulty;

    int mistakes;

    long time;

    public ScoreModal(int id, long score, Difficulty difficulty, int mistakes, long time) {
        this.id = id;
        this.score = score;
        this.difficulty = difficulty;
        this.mistakes = mistakes;
        this.time = time;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
