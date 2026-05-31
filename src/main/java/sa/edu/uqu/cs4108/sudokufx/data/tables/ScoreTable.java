package sa.edu.uqu.cs4108.sudokufx.data.tables;

import sa.edu.uqu.cs4108.sudokufx.data.DatabaseConnection;
import sa.edu.uqu.cs4108.sudokufx.data.DatabaseTable;
import sa.edu.uqu.cs4108.sudokufx.data.Difficulty;
import sa.edu.uqu.cs4108.sudokufx.data.modal.ScoreModal;

import java.sql.ResultSet;
import java.sql.Statement;

public class ScoreTable implements DatabaseTable {

    private static final ScoreTable instance = new ScoreTable();

    public static ScoreTable getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "score";
    }

    @Override
    public String generateTable() {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      difficulty TEXT CHECK(difficulty IN ('FLASH', 'EASY', 'MEDIUM', 'HARD', 'EXPERT')),
                      score INTEGER,
                      mistakes INTEGER,
                      completion_time_seconds INTEGER,
                      achieved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                  );
                """, getName()
        );
    }


    public void saveScore(ScoreModal scoreModal) {
        String query = String.format(
                "INSERT INTO %s (difficulty, score, mistakes, completion_time_seconds, achieved_at) VALUES ('%s', %d, %d, %d, %d)",
                getName(), scoreModal.getDifficulty().name(), scoreModal.getScore(), scoreModal.getMistakes(), scoreModal.getTime(), scoreModal.getAchievedAt()
        );
        DatabaseConnection.getInstance().executeQuery(query);
    }


    public ScoreModal getHighScores(Difficulty difficulty) {
        ScoreModal scoreModal = new ScoreModal();

        String query = String.format(
                "SELECT * FROM %s WHERE difficulty = '%s' ORDER BY score DESC LIMIT 1",
                getName(), difficulty.name()
        );

        try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                scoreModal.setId(rs.getInt("id"));
                scoreModal.setDifficulty(Difficulty.valueOf(rs.getString("difficulty")));
                scoreModal.setScore(rs.getLong("score"));
                scoreModal.setMistakes(rs.getInt("mistakes"));
                scoreModal.setTime(rs.getLong("completion_time_seconds"));
                scoreModal.setAchievedAt(rs.getLong("achieved_at"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return scoreModal;
    }


}
