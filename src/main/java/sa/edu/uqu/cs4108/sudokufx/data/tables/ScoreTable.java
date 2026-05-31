package sa.edu.uqu.cs4108.sudokufx.data.tables;

import sa.edu.uqu.cs4108.sudokufx.data.DatabaseTable;

public class ScoreTable implements DatabaseTable {
    @Override
    public String getName() {
        return "score";
    }

    @Override
    public String generateTable() {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      difficulty TEXT CHECK(difficulty IN ('Flash', 'Easy', 'Medium', 'Hard', 'Expert')),
                      score INTEGER,
                      mistakes INTEGER,
                      completion_time_seconds INTEGER,
                      achieved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                  );
                """, getName()
        );
    }


}
