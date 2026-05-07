package sa.edu.uqu.cs4108.sudokufx.data.tables;

import sa.edu.uqu.cs4108.sudokufx.data.DatabaseConnection;
import sa.edu.uqu.cs4108.sudokufx.data.DatabaseTable;

public class SavedGameTable implements DatabaseTable {

    @Override
    public String getName() {
        return "saved_games";
    }

    @Override
    public String generateTable() {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    difficulty TEXT CHECK(difficulty IN ('Flash', 'Easy', 'Medium', 'Hard', 'Expert')),
                    elapsed_time INTEGER,
                    initial_grid TEXT,
                    current_grid TEXT,
                    last_saved DATETIME DEFAULT CURRENT_TIMESTAMP
                );
                """, getName()
        );
    }

    public void saveGame(String difficulty, String currentGrid, int time) {
        String query = String.format(
                "INSERT INTO saved_games (difficulty, elapsed_time, current_grid) VALUES ('%s', %d, '%s')",
                difficulty, time, currentGrid
        );
        DatabaseConnection.getInstance().executeQuery(query);
    }


}