package level1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Leaderboard Terminal screen.
 *
 * Uses LeaderboardRepository to load data from leaderboard.csv
 * and uses LeaderboardAlgorithms (student TODOs) for sorting/search.
 */
public class LeaderboardPanel extends JPanel {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 540;

    private final AppRouter router;

    private final LeaderboardTableModel tableModel = new LeaderboardTableModel();
    private final JTable table = new JTable(tableModel);

    private final JTextField searchField = new JTextField(18);
    private final JLabel statusLabel = new JLabel("Load leaderboard.csv to begin.");

    private ArrayList<ScoreEntry> allEntries = new ArrayList<>();

    public LeaderboardPanel(AppRouter router) {
        this.router = router;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        // Top controls
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadBtn = new JButton("Load CSV");
        JButton top20Btn = new JButton("Top 20 (by Score)");
        JButton sortNameBtn = new JButton("Sort by Username");
        JButton searchBtn = new JButton("Search (Binary)");
        JButton backBtn = new JButton("Back to Menu");

        top.add(loadBtn);
        top.add(top20Btn);
        top.add(sortNameBtn);
        top.add(new JLabel("Username:"));
        top.add(searchField);
        top.add(searchBtn);
        top.add(backBtn);

        add(top, BorderLayout.NORTH);

        // Table center
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Status bottom
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(statusLabel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // Actions
        loadBtn.addActionListener(e -> doLoad());

        top20Btn.addActionListener(e -> {
            if (allEntries.isEmpty()) { status("Load first."); return; }

            // TODO: student sorts by score descending
            ArrayList<ScoreEntry> copy = new ArrayList<>(allEntries);
            LeaderboardAlgorithms.sortByScoreDescending(copy);

            showRows(copy, 20);
            status("Showing Top 20 by score (requires sortByScoreDescending).");
        });

        sortNameBtn.addActionListener(e -> {
            if (allEntries.isEmpty()) { status("Load first."); return; }

            ArrayList<ScoreEntry> copy = new ArrayList<>(allEntries);
            LeaderboardAlgorithms.sortByUsernameAscending(copy);
            showRows(copy, 50);
            status("Showing first 50 sorted by username (requires sortByUsernameAscending).");
        });

        searchBtn.addActionListener(e -> {
            if (allEntries.isEmpty()) { status("Load first."); return; }

            String target = searchField.getText().trim();
            if (target.isEmpty()) { status("Enter a username."); return; }

            ArrayList<ScoreEntry> copy = new ArrayList<>(allEntries);

            // Ensure sorted before binary search
            LeaderboardAlgorithms.sortByUsernameAscending(copy);

            int idx = LeaderboardAlgorithms.binarySearchByUsername(copy, target);

            if (idx >= 0) {
                tableModel.setData(List.of(copy.get(idx)));
                status("Found user: " + target + " (binary search index " + idx + ")");
            } else {
                tableModel.setData(List.of());
                status("Not found: " + target + " (binary search returned -1)");
            }
        });

        backBtn.addActionListener(e -> router.goToMenu());
    }

    private void doLoad() {
        try {
            allEntries = LeaderboardRepository.loadFromResource("leaderboard.csv");
            status("Loaded " + allEntries.size() + " entries from leaderboard.csv");
            // Show a preview
            showRows(allEntries, 10000);
        } catch (Exception ex) {
            ex.printStackTrace();
            status("ERROR loading leaderboard.csv: " + ex.getMessage());
        }
    }

    private void showRows(ArrayList<ScoreEntry> list, int max) {
        int n = Math.min(max, list.size());
        ArrayList<ScoreEntry> subset = new ArrayList<>(list.subList(0, n));
        tableModel.setData(subset);
    }

    private void status(String msg) {
        statusLabel.setText(msg);
    }
}
