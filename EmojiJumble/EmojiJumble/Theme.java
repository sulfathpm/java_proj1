import java.awt.*;

public class Theme {
    public static final Color BG_DARK      = new Color(13, 13, 25);
    public static final Color BG_CARD      = new Color(18, 18, 42);
    public static final Color BG_CARD2     = new Color(14, 14, 32);
    public static final Color BORDER       = new Color(30, 30, 58);
    public static final Color ACCENT_GOLD  = new Color(255, 217, 61);
    public static final Color ACCENT_PURP  = new Color(199, 125, 255);
    public static final Color ACCENT_BLUE  = new Color(77, 150, 255);
    public static final Color ACCENT_GREEN = new Color(107, 203, 119);
    public static final Color ACCENT_RED   = new Color(255, 107, 107);
    public static final Color TEXT_MAIN    = new Color(230, 230, 240);
    public static final Color TEXT_DIM     = new Color(120, 120, 150);
    public static final Color TEXT_HINT    = new Color(90, 90, 110);

    public static final Color[] EMOJI_COLORS = {
        new Color(255, 107, 107),
        new Color(255, 217, 61),
        new Color(107, 203, 119),
        new Color(77, 150, 255),
        new Color(199, 125, 255),
        new Color(255, 154, 60),
        new Color(0, 201, 167),
        new Color(247, 37, 133),
    };

    public static Color emojiColor(String emoji) {
        int hash = 0;
        for (char c : emoji.toCharArray()) hash += c;
        return EMOJI_COLORS[Math.abs(hash) % EMOJI_COLORS.length];
    }

    public static Font titleFont(float size) {
        return new Font("Segoe UI Emoji", Font.BOLD, (int) size);
    }
    public static Font bodyFont(float size) {
        return new Font("Segoe UI Emoji", Font.PLAIN, (int) size);
    }
    public static Font boldFont(float size) {
        return new Font("Segoe UI Emoji", Font.BOLD, (int) size);
    }
}
