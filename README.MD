# SudokuFX

A simple Sudoku game built with JavaFX.

## Features

*   **Multiple Difficulties:** Play Sudoku in various difficulty levels: Flash, Easy, Medium, Hard, and Expert.
*   **Score Tracking:** The game keeps track of your best scores for each difficulty level.
*   **Clean UI:** A simple and intuitive user interface built with JavaFX.

## Project Structure

The project is organized into the following packages:

*   `controller`: Contains the `SceneController` which manages the different views of the application.
*   `data`: Handles the database connection, tables, and data models.
    *   `modal`: Contains the `ScoreModal` class, which represents a player's score.
    *   `tables`: Contains the `ScoreTable` class, which manages the `score` table in the database.
*   `util`: Utility classes.
*   `view`: Contains the different views of the application.
    *   `game`: Contains the `GameView` and `DifficultyMenuView`.

## How to Run

1.  Clone the repository.
2.  Open the project in your favorite IDE.
3.  Build the project using Maven.
4.  Run the `Launcher` class.

## Dependencies

*   JavaFX
*   SQLite-JDBC
*   JUnit
