import javax.swing.*;
import java.awt.*;

public class ResultScreen extends JFrame {

    public ResultScreen(int score, int total) {
        setTitle("Emoji Jumble – Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 560);
        setLocationRelativeTo(null);
        setResizable(false);

        int maxScore = total * 2;
        int pct = maxScore > 0 ? (score * 100 / maxScore) : 0;
        String medal = pct >= 80 ? "🥇" : pct >= 50 ? "🥈" : "🥉";
        String message = pct >= 80 ? "Brilliant! You're an emoji genius 🧠"
                       : pct >= 50 ? "Good effort! Keep practicing 💪"
                       : "Don't give up — try again! 🌱";

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

        JLabel medalLbl = new JLabel(medal, SwingConstants.CENTER);
        medalLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        medalLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLbl = new JLabel("Game Over!", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 46));
        titleLbl.setForeground(Theme.TEXT_MAIN);
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Stats row
        RoundPanel statsRow = new RoundPanel(20, new Color(255,255,255,8), null);
        statsRow.setLayout(new GridLayout(1, 3));
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        addStat(statsRow, "⭐", String.valueOf(score), "Points");
        addDivider(statsRow);
        addStat(statsRow, "🧩", String.valueOf(total), "Solved");
        addDivider(statsRow);
        addStat(statsRow, "🎯", pct + "%", "Accuracy");

        JLabel msgLbl = new JLabel("<html><center>" + message + "</center></html>", SwingConstants.CENTER);
        msgLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        msgLbl.setForeground(Theme.TEXT_DIM);
        msgLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        btnRow.setOpaque(false);

        StyledButton replayBtn = new StyledButton("  Play Again 🔄  ", StyledButton.Style.PRIMARY);
        replayBtn.setPreferredSize(new Dimension(180, 48));
        replayBtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        replayBtn.addActionListener(e -> {
            dispose();
            new GameScreen(0, 0).setVisible(true);
        });

        StyledButton homeBtn = new StyledButton("  Home 🏠  ", StyledButton.Style.GHOST, Theme.ACCENT_GOLD);
        homeBtn.setPreferredSize(new Dimension(150, 48));
        homeBtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        homeBtn.addActionListener(e -> {
            dispose();
            new HomeScreen().setVisible(true);
        });

        btnRow.add(replayBtn);
        btnRow.add(homeBtn);
        btnRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(medalLbl);
        card.add(Box.createVerticalStrut(8));
        card.add(titleLbl);
        card.add(Box.createVerticalStrut(22));
        card.add(statsRow);
        card.add(Box.createVerticalStrut(20));
        card.add(msgLbl);
        card.add(Box.createVerticalStrut(28));
        card.add(btnRow);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(16, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; gbc.weighty = 1;
        center.add(card, gbc);

        root.add(center, BorderLayout.CENTER);
        setContentPane(root);
    }

    private void addStat(JPanel parent, String icon, String value, String label) {
        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));
        stat.setOpaque(false);
        stat.setBorder(BorderFactory.createEmptyBorder(14, 6, 14, 6));

        JLabel iconLbl = new JLabel(icon, SwingConstants.CENTER);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        iconLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valLbl = new JLabel(value, SwingConstants.CENTER);
        valLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 34));
        valLbl.setForeground(Theme.TEXT_MAIN);
        valLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl = new JLabel(label.toUpperCase(), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 11));
        lbl.setForeground(Theme.TEXT_HINT);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        stat.add(iconLbl);
        stat.add(Box.createVerticalStrut(2));
        stat.add(valLbl);
        stat.add(Box.createVerticalStrut(2));
        stat.add(lbl);
        parent.add(stat);
    }

    private void addDivider(JPanel parent) {
        JPanel div = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(new Color(255,255,255,18));
                g.fillRect(getWidth()/2, 20, 1, getHeight() - 40);
            }
        };
        div.setOpaque(false);
        parent.add(div);
    }
}
