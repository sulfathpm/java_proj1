import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class GameScreen extends JFrame {

    private int puzzleIdx, score;
    private Puzzle puzzle;
    private List<String> arranged = new ArrayList<>(), jumbled = new ArrayList<>();
    private boolean hintUsed, answered;

    private JPanel dropZone, jumbledPanel, actionPanel;
    private JLabel scoreLabel, resultLabel, meaningLabel;
    private RoundPanel meaningBox;

    public GameScreen(int puzzleIdx, int score) {
        this.puzzleIdx = puzzleIdx;
        this.score = score;
        this.puzzle = Puzzle.ALL[puzzleIdx % Puzzle.ALL.length];
        buildUI();
        loadPuzzle();
    }

    private void buildUI() {
        setTitle("Emoji Jumble");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // Root with gradient background
        JPanel root = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                ((Graphics2D) g).setPaint(new GradientPaint(0, 0, new Color(26,5,51), getWidth(), getHeight(), Theme.BG_DARK));
                ((Graphics2D) g).fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setOpaque(false);
        root.add(buildTopBar(), BorderLayout.NORTH);
        root.add(buildCard(), BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(BorderFactory.createEmptyBorder(14, 20, 6, 20));

        StyledButton homeBtn = new StyledButton("🏠", StyledButton.Style.GHOST, Theme.BORDER);
        homeBtn.setPreferredSize(new Dimension(44, 36));
        homeBtn.addActionListener(e -> confirmExit());

        scoreLabel = lbl("⭐ " + score + " pts", Theme.ACCENT_GOLD, 14, Font.BOLD);
        bar.add(homeBtn, BorderLayout.WEST);
        bar.add(scoreLabel, BorderLayout.CENTER);
        bar.add(lbl("#" + (puzzleIdx+1) + " / " + Puzzle.ALL.length, Theme.TEXT_HINT, 13, Font.BOLD), BorderLayout.EAST);
        return bar;
    }

    private JPanel buildCard() {
        RoundPanel card = new RoundPanel(28);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(24, 22, 24, 22));

        // Drop zone
        dropZone = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255,255,255,5));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),18,18);
                g2.setColor(Theme.BORDER);
                g2.setStroke(new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,0,new float[]{6,5},0));
                g2.drawRoundRect(1,1,getWidth()-2,getHeight()-2,18,18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        dropZone.setOpaque(false);
        dropZone.setPreferredSize(new Dimension(420, 100));
        dropZone.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        // Result label
        resultLabel = lbl("", Theme.ACCENT_GREEN, 15, Font.BOLD);
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);
        resultLabel.setVisible(false);

        // Meaning box
        meaningBox = new RoundPanel(14, new Color(255,217,61,15), new Color(255,217,61,40));
        meaningBox.setLayout(new BoxLayout(meaningBox, BoxLayout.Y_AXIS));
        meaningBox.setBorder(BorderFactory.createEmptyBorder(12,16,12,16));
        JLabel mTitle = lbl("MEANING", new Color(120,120,140), 11, Font.BOLD);
        mTitle.setAlignmentX(CENTER_ALIGNMENT);
        meaningLabel = lbl("", Theme.ACCENT_GOLD, 15, Font.BOLD);
        meaningLabel.setAlignmentX(CENTER_ALIGNMENT);
        meaningBox.add(mTitle);
        meaningBox.add(Box.createVerticalStrut(4));
        meaningBox.add(meaningLabel);
        meaningBox.setVisible(false);
        meaningBox.setAlignmentX(CENTER_ALIGNMENT);
        meaningBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Jumbled panel
        jumbledPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        jumbledPanel.setOpaque(false);
        jumbledPanel.setPreferredSize(new Dimension(420, 100));
        jumbledPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Action panel
        actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actionPanel.setOpaque(false);

        JLabel instrLbl = lbl("Arrange emojis in the correct order", Theme.TEXT_HINT, 12, Font.BOLD);
        instrLbl.setAlignmentX(CENTER_ALIGNMENT);
        JLabel jumbleLbl = lbl("Available Emojis", Theme.TEXT_HINT, 11, Font.BOLD);
        jumbleLbl.setAlignmentX(CENTER_ALIGNMENT);

        for (Object[] item : new Object[][]{
            {instrLbl}, {14}, {dropZone}, {10}, {resultLabel}, {6},
            {meaningBox}, {16}, {jumbleLbl}, {8}, {jumbledPanel}, {20}, {actionPanel}
        }) {
            if (item[0] instanceof Integer) card.add(Box.createVerticalStrut((Integer)item[0]));
            else card.add((Component)item[0]);
        }

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(0,16,16,16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        center.add(card, gbc);
        return center;
    }

    private void loadPuzzle() {
        puzzle = Puzzle.ALL[puzzleIdx % Puzzle.ALL.length];
        jumbled = new ArrayList<>(Arrays.asList(puzzle.jumbled));
        Collections.shuffle(jumbled);
        arranged = new ArrayList<>();
        answered = hintUsed = false;
        resultLabel.setVisible(false);
        meaningBox.setVisible(false);
        refresh();
    }

    private void refresh() {
        // Drop zone
        dropZone.removeAll();
        if (arranged.isEmpty()) {
            dropZone.add(lbl("Tap emojis below ↓", new Color(80,80,100), 13, Font.ITALIC));
        } else {
            for (int i = 0; i < arranged.size(); i++) {
                final int idx = i;
                String e = arranged.get(i);
                StyledButton b = emojiBtn(e);
                b.addActionListener(ev -> { if (!answered) { jumbled.add(arranged.remove(idx)); refresh(); } });
                dropZone.add(b);
            }
        }
        dropZone.revalidate(); dropZone.repaint();

        // Jumbled panel
        jumbledPanel.removeAll();
        for (int i = 0; i < jumbled.size(); i++) {
            final int idx = i;
            String e = jumbled.get(i);
            StyledButton b = emojiBtn(e);
            b.addActionListener(ev -> { if (!answered) { arranged.add(jumbled.remove(idx)); refresh(); } });
            jumbledPanel.add(b);
        }
        jumbledPanel.revalidate(); jumbledPanel.repaint();

        // Action buttons
        actionPanel.removeAll();
        if (!answered) {
            actionPanel.add(ghostBtn("🔃 Reset", Theme.TEXT_DIM, e -> loadPuzzle(), 110));
            actionPanel.add(ghostBtn(hintUsed ? "💡 Used" : "💡 Hint −1pt", Theme.ACCENT_GOLD, e -> useHint(), 130));
            actionPanel.add(primaryBtn("Submit ✅", e -> checkAnswer(), 130));
            actionPanel.add(dangerBtn("🚪 Exit", e -> confirmExit(), 100));
        } else {
            boolean last = puzzleIdx >= Puzzle.ALL.length - 1;
            actionPanel.add(primaryBtn(last ? "Results 🏆" : "Next →", e -> {
                if (last) { dispose(); new ResultScreen(score, Puzzle.ALL.length).setVisible(true); }
                else      { dispose(); new GameScreen(puzzleIdx + 1, score).setVisible(true); }
            }, 160));
        }
        actionPanel.revalidate(); actionPanel.repaint();
    }

    private void useHint() {
        if (hintUsed || answered) return;
        hintUsed = true;
        arranged.clear();
        arranged.add(puzzle.correct[0]);
        jumbled = new ArrayList<>(Arrays.asList(puzzle.jumbled));
        jumbled.remove(puzzle.correct[0]);
        Collections.shuffle(jumbled);
        refresh();
    }

    private void checkAnswer() {
        if (arranged.size() != puzzle.correct.length) { shake(); return; }
        boolean correct = IntStream.range(0, puzzle.correct.length)
            .allMatch(i -> arranged.get(i).equals(puzzle.correct[i]));
        answered = true;
        if (correct) {
            int pts = hintUsed ? 1 : 2;
            score += pts;
            scoreLabel.setText("⭐ " + score + " pts");
            resultLabel.setText("🎉  Correct! +" + pts + " pts");
            resultLabel.setForeground(Theme.ACCENT_GREEN);
        } else {
            resultLabel.setText("❌  Not quite! See the answer below.");
            resultLabel.setForeground(Theme.ACCENT_RED);
        }
        meaningLabel.setText("\"" + puzzle.meaning + "\"");
        resultLabel.setVisible(true);
        meaningBox.setVisible(true);
        refresh();
    }

    private void confirmExit() {
        int ok = JOptionPane.showConfirmDialog(this,
            "Exit the game? Your score will be lost.",
            "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok == JOptionPane.YES_OPTION) { dispose(); new HomeScreen().setVisible(true); }
    }

    private void shake() {
        int origX = dropZone.getX();
        int[] offsets = {-8,8,-6,6,-4,4,-2,2,0};
        int[] i = {0};
        Timer t = new Timer(40, null);
        t.addActionListener(e -> {
            if (i[0] < offsets.length) dropZone.setLocation(origX + offsets[i[0]++], dropZone.getY());
            else { ((Timer)e.getSource()).stop(); dropZone.setLocation(origX, dropZone.getY()); }
        });
        t.start();
    }

    // ── Helpers ──────────────────────────────────────────────
    private StyledButton emojiBtn(String emoji) {
        return new StyledButton(emoji, StyledButton.Style.EMOJI, Theme.emojiColor(emoji));
    }
    private StyledButton ghostBtn(String text, Color color, ActionListener al, int w) {
        StyledButton b = new StyledButton(text, StyledButton.Style.GHOST, color);
        b.setPreferredSize(new Dimension(w, 42));
        b.addActionListener(al);
        return b;
    }
    private StyledButton primaryBtn(String text, ActionListener al, int w) {
        StyledButton b = new StyledButton(text, StyledButton.Style.PRIMARY);
        b.setPreferredSize(new Dimension(w, 42));
        b.addActionListener(al);
        return b;
    }
    private StyledButton dangerBtn(String text, ActionListener al, int w) {
        StyledButton b = new StyledButton(text, StyledButton.Style.DANGER);
        b.setPreferredSize(new Dimension(w, 42));
        b.addActionListener(al);
        return b;
    }
    private JLabel lbl(String text, Color color, int size, int style) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(new Font("Segoe UI Emoji", style, size));
        l.setForeground(color);
        return l;
    }
}