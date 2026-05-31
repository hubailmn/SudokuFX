package sa.edu.uqu.cs4108.sudokufx.data.modal;

import sa.edu.uqu.cs4108.sudokufx.data.Difficulty;

public class ScoreModal {

    int id;
    long score;
    Difficulty difficulty;
    int mistakes;
    long time;
    long achievedAt;

    public ScoreModal(int id, long score, Difficulty difficulty, int mistakes, long time) {
        this.id = id;
        this.score = score;
        this.difficulty = difficulty;
        this.mistakes = mistakes;
        this.time = time;
        this.achievedAt = System.currentTimeMillis();
    }

    public ScoreModal(long score, Difficulty difficulty, int mistakes, long time) {
        this(-1, score, difficulty, mistakes, time);
    }

    public ScoreModal() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getAchievedAt() {
        return achievedAt;
    }

    public void setAchievedAt(long achievedAt) {
        this.achievedAt = achievedAt;
    }
}
