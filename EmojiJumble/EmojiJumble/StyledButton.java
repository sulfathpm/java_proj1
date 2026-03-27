import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class StyledButton extends JButton {

    public enum Style { PRIMARY, GHOST, EMOJI, DANGER }

    private Style style;
    private Color accentColor;
    private boolean hovered = false;
    private boolean pressed = false;
    private float scale = 1.0f;

    public StyledButton(String text, Style style) {
        this(text, style, Theme.ACCENT_PURP);
    }

    public StyledButton(String text, Style style, Color accent) {
        super(text);
        this.style = style;
        this.accentColor = accent;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (style == Style.EMOJI) {
            setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
            setPreferredSize(new Dimension(72, 72));
        } else {
            setFont(Theme.boldFont(14));
            setPreferredSize(new Dimension(140, 44));
        }

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
            public void mouseExited(MouseEvent e)  { hovered = false; pressed = false; repaint(); }
            public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
            public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        float sc = pressed ? 0.93f : (hovered ? 1.05f : 1.0f);
        int ox = (int)((w - w * sc) / 2);
        int oy = (int)((h - h * sc) / 2);
        g2.translate(ox, oy);
        g2.scale(sc, sc);

        int arc = (style == Style.EMOJI) ? 18 : 50;

        switch (style) {
            case PRIMARY -> {
                GradientPaint gp = new GradientPaint(0, 0, Theme.ACCENT_PURP, w, h, Theme.ACCENT_BLUE);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
                if (hovered) {
                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
                }
                g2.setColor(Color.WHITE);
            }
            case GHOST -> {
                g2.setColor(hovered ? new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 20) : new Color(255,255,255,8));
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
                g2.setColor(accentColor);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, w-2, h-2, arc, arc));
                g2.setColor(accentColor);
            }
            case EMOJI -> {
                Color ec = accentColor;
                g2.setColor(new Color(ec.getRed(), ec.getGreen(), ec.getBlue(), hovered ? 50 : 25));
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
                g2.setColor(new Color(ec.getRed(), ec.getGreen(), ec.getBlue(), hovered ? 200 : 130));
                g2.setStroke(new BasicStroke(2f));
                g2.draw(new RoundRectangle2D.Float(1, 1, w-2, h-2, arc, arc));
                g2.setColor(Theme.TEXT_MAIN);
            }
            case DANGER -> {
                g2.setColor(hovered ? new Color(255, 107, 107, 30) : new Color(255,255,255,8));
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
                g2.setColor(Theme.ACCENT_RED);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, w-2, h-2, arc, arc));
                g2.setColor(Theme.ACCENT_RED);
            }
        }

        // Draw text
        FontMetrics fm = g2.getFontMetrics(getFont());
        g2.setFont(getFont());
        String txt = getText();
        int tx = (w - fm.stringWidth(txt)) / 2;
        int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(txt, tx, ty);
        g2.dispose();
    }
}
