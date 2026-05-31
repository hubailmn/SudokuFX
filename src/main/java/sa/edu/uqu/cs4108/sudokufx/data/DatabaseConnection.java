package sa.edu.uqu.cs4108.sudokufx.data;

import sa.edu.uqu.cs4108.sudokufx.data.tables.ScoreTable;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private static final List<DatabaseTable> tablesList = new ArrayList<>(
            List.of(
                    new ScoreTable()
            )
    );
    private static DatabaseConnection instance;
    private Connection connection;

    public DatabaseConnection() {
//        dropTables();

        this.connection = null;
        try {
            connect();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        tablesList.forEach(table -> {
            System.out.println("Generating table " + table.getClass().getSimpleName());
            executeQuery(table.generateTable());
        });

    }

    public static DatabaseConnection getInstance() {
        return instance == null ? instance = new DatabaseConnection() : instance;
    }

    private void dropTables() {
        tablesList.forEach(table -> {
            System.out.println("Dropping table " + table.getClass().getSimpleName());
            executeQuery("drop table if exists " + table.getName());
        });
    }

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            System.out.println("initializing a new Database Connection.");
            connect();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void executeQuery(String query) {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void connect() throws SQLException, IOException {
        String DATABASE_FILE_PATH = "suduku.db";
        File databaseFile = new File(DATABASE_FILE_PATH);

        if (!databaseFile.exists()) {
            boolean created = databaseFile.createNewFile();
        }

        this.connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE_PATH);
    }

    private void closeConnection() throws SQLException {
        if (!getConnection().isClosed() && getConnection() != null) {
            getConnection().close();
        }
    }
}
