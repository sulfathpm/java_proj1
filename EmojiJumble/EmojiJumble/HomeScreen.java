import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeScreen extends JFrame {

    public HomeScreen() {
        setTitle("Emoji Jumble");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(26, 5, 51), getWidth(), getHeight(), Theme.BG_DARK);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setOpaque(false);

        RoundPanel card = new RoundPanel(28);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(36, 32, 36, 32));

        // Badge
        JLabel badge = new JLabel("🧩  PUZZLE GAME", SwingConstants.CENTER);
        badge.setFont(Theme.boldFont(12));
        badge.setForeground(Theme.ACCENT_PURP);
        badge.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel title = new JLabel("<html><center>Emoji<br>Jumble</center></html>", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 56));
        title.setForeground(Theme.TEXT_MAIN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel sub = new JLabel("<html><center>Rearrange the emojis to<br>reveal a hidden meaning!</center></html>", SwingConstants.CENTER);
        sub.setFont(Theme.bodyFont(15));
        sub.setForeground(Theme.TEXT_DIM);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Preview emojis
        JPanel emojiRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        emojiRow.setOpaque(false);
        for (String e : new String[]{"😴","⏰","⏱️","🚫"}) {
            JLabel lbl = new JLabel(e);
            lbl.setFont(Theme.bodyFont(32));
            emojiRow.add(lbl);
        }

        // Rules panel
        RoundPanel rulesPanel = new RoundPanel(16, new Color(255,255,255,12), null);
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.Y_AXIS));
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        String[][] rules = {
            {"1", "Tap emojis to place them in order"},
            {"2", "Submit to check your answer"},
            {"3", "Correct = 2 pts · With hint = 1 pt"},
        };
        for (String[] r : rules) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
            row.setOpaque(false);
            JLabel num = new JLabel(r[0]);
            num.setFont(Theme.boldFont(13));
            num.setForeground(Theme.ACCENT_PURP);
            JLabel txt = new JLabel(r[1]);
            txt.setFont(Theme.bodyFont(14));
            txt.setForeground(new Color(190,190,210));
            row.add(num);
            row.add(txt);
            rulesPanel.add(row);
        }

        // Start button
        StyledButton startBtn = new StyledButton("  Start Game 🚀  ", StyledButton.Style.PRIMARY);
        startBtn.setFont(Theme.boldFont(17));
        startBtn.setPreferredSize(new Dimension(220, 50));
        startBtn.setMaximumSize(new Dimension(220, 50));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(e -> {
            dispose();
            new GameScreen(0, 0).setVisible(true);
        });

        card.add(badge);
        card.add(Box.createVerticalStrut(6));
        card.add(title);
        card.add(Box.createVerticalStrut(10));
        card.add(sub);
        card.add(Box.createVerticalStrut(14));
        card.add(emojiRow);
        card.add(Box.createVerticalStrut(18));
        card.add(rulesPanel);
        card.add(Box.createVerticalStrut(24));
        card.add(startBtn);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(card);

        root.add(center, BorderLayout.CENTER);
        setContentPane(root);
    }
}
