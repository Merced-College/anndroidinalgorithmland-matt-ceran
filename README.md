# AnnDroidInAlgorithmLand

Matt Ceran
03/16/2026

A sample project to practice sorting and searching algorithms. This is a Java Swing game called "Ann Droid in Algorithm Land" where you play a rabbit-clicking game and can view a leaderboard with sorting and searching features.

Project setup completed successfully.

## Code Exploration

### Step 1 - Project Structure

1. The src folder contains Java source code files (.java). All the classes are inside the level1 package.

2. The content folder stores game assets like images (png, gif), audio files (wav), an XML file, and the leaderboard.csv data file.

3. The uml folder (uml.txt) holds a UML class diagram that shows how the classes in the project relate to each other.

4. Software projects separate code from content/assets because it keeps things organized. Code changes more often than images or sounds, and separating them makes it easier to swap out assets without touching the logic. It also keeps the project cleaner and easier to navigate.

### Step 2 - Main Program

1. The Main class contains the main() method. It is located in src/level1/Main.java.

2. When the program first starts, it creates a JFrame window, then creates an AppRouter which sets up all three screens (menu, game, leaderboard) and shows the main menu with background music playing.

3. When the program begins it creates a JFrame, an AppRouter, and the AppRouter creates a MainMenuPanel, a RabbitGamePanel, and a LeaderboardPanel. It also uses the Assets class to load images and play audio.

### Step 3 - Program Execution

1. RabbitGamePanel and MainMenuPanel are responsible for drawing graphics. They both override paintComponent() to draw images and text onto the screen.

2. The Assets class is responsible for loading files. It loads images using ImageIO and plays audio using the Java Sound API (Clip). LeaderboardRepository loads the leaderboard.csv file.

3. The game updates the screen using Swing Timers. In RabbitGamePanel, a moveTimer repositions the rabbit every second and a countdownTimer counts down the remaining time. Both call repaint() which triggers paintComponent() to redraw everything.

### Step 4 - UML Diagram

1. The UML diagram helps you see the big picture of how all the classes connect. It shows which class creates which, and what each class is responsible for.

2. AppRouter appears to be the central class. It owns the three main panels (MainMenuPanel, RabbitGamePanel, LeaderboardPanel) and controls which screen is showing.

3. MainMenuPanel and RabbitGamePanel both depend on Assets for loading images and sounds. LeaderboardPanel depends on LeaderboardRepository to load score data and LeaderboardAlgorithms for sorting and searching. LeaderboardTableModel is used by LeaderboardPanel to display ScoreEntry data in a JTable.

### Step 5 - Score System

1. Scores are stored in the leaderboard.csv file in the content folder. They are loaded into an ArrayList of ScoreEntry objects by LeaderboardRepository. The LeaderboardPanel holds this list in its allEntries field.

2. The score data type is int (inside the ScoreEntry class). Each ScoreEntry also has a String username, int hits, int misses, long timestampEpoch, and String level.

3. It makes sense to implement sorting of scores in the LeaderboardAlgorithms class, since that class was specifically created as the place for students to add sorting and searching methods. The LeaderboardPanel already calls those methods when the user clicks the sort and search buttons.

## Sorting

1. I added the sorting code in the LeaderboardAlgorithms class, inside the sortByScoreDescending() and sortByUsernameAscending() methods.

2. I chose that location because the methods were already stubbed out there with TODO comments, and LeaderboardPanel already calls those methods when the buttons are clicked. It was clearly designed to be the place for the algorithms.

3. The data structure being sorted is an ArrayList of ScoreEntry objects.

4. I chose selection sort for sorting scores by descending order, and insertion sort for sorting by username ascending.

5. Selection sort works by going through the list one position at a time. For each position, it scans the rest of the list to find the entry with the highest score, then swaps it into that position. After the first pass the biggest score is in position 0, after the second pass the second biggest is in position 1, and so on until the whole list is sorted.

6. I verified the sort was correct by running the program, clicking "Load CSV" to load the leaderboard data, then clicking "Top 20 (by Score)". The scores showed up from highest to lowest, and I checked that no scores were out of order or missing.

## Searching

1. Binary search works by starting with the full sorted list and checking the middle element. If the middle element matches what you're looking for, you're done. If the target comes before the middle alphabetically, you throw out the right half and search the left. If it comes after, you throw out the left half. You keep cutting the list in half like this until you either find the target or run out of elements to check.

2. The data must be sorted before binary search because the algorithm relies on knowing that everything to the left of the middle is smaller (or earlier alphabetically) and everything to the right is bigger (or later). If the data isn't sorted, cutting the list in half doesn't tell you anything useful about where the target might be.

3. I used low, high, and mid as my variables. low starts at 0 (beginning of the list), high starts at list.size() - 1 (end of the list), and mid is calculated as (low + high) / 2 each time through the loop.

4. When the score is not found, the while loop keeps going until low becomes greater than high, which means there's nothing left to search. At that point the method returns -1 to indicate the username wasn't in the list.
