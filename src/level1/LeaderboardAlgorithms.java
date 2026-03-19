package level1;

import java.util.ArrayList;

// Matt Ceran
// 03/16/2026
// LeaderboardAlgorithms - handles sorting and searching for the leaderboard

public class LeaderboardAlgorithms {

    /**
     * Sorts scores from highest to lowest using selection sort.
     * Goes through the list and finds the biggest score each time,
     * then puts it in the right spot.
     */
    public static void sortByScoreDescending(ArrayList<ScoreEntry> list) {
        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            // assume the current position has the max
            int maxIndex = i;

            // look through the rest of the list for a bigger score
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).getScore() > list.get(maxIndex).getScore()) {
                    maxIndex = j;
                }
            }

            // swap if we found something bigger
            if (maxIndex != i) {
                ScoreEntry temp = list.get(i);
                list.set(i, list.get(maxIndex));
                list.set(maxIndex, temp);
            }
        }
    }

    /**
     * Sorts usernames alphabetically A to Z using insertion sort.
     * Works like sorting cards in your hand - picks each one
     * and slides it into the right spot.
     */
    public static void sortByUsernameAscending(ArrayList<ScoreEntry> list) {
        int n = list.size();

        for (int i = 1; i < n; i++) {
            ScoreEntry current = list.get(i);
            String currentName = current.getUsername();
            int j = i - 1;

            // shift entries that come after current alphabetically
            while (j >= 0 && list.get(j).getUsername().compareToIgnoreCase(currentName) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }

            // drop current into the gap
            list.set(j + 1, current);
        }
    }

    /**
     * Binary search for a username in a sorted list.
     * Keeps cutting the search area in half until it finds
     * the name or runs out of places to look.
     *
     * The list MUST be sorted by username first or this won't work right.
     *
     * Returns the index if found, -1 if not found.
     */
    public static int binarySearchByUsername(ArrayList<ScoreEntry> list, String username) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midName = list.get(mid).getUsername();

            int comparison = midName.compareToIgnoreCase(username);

            if (comparison == 0) {
                // found it
                return mid;
            } else if (comparison < 0) {
                // midName comes before target, search right half
                low = mid + 1;
            } else {
                // midName comes after target, search left half
                high = mid - 1;
            }
        }

        // not in the list
        return -1;
    }
}
