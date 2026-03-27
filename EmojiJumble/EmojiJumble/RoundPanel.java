import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundPanel extends JPanel {
    private int arc;
    private Color bg;
    private Color border;

    public RoundPanel(int arc) {
        this(arc, Theme.BG_CARD, Theme.BORDER);
    }

    public RoundPanel(int arc, Color bg, Color border) {
        this.arc = arc;
        this.bg = bg;
        this.border = border;
        setOpaque(false);
    }

    public void setBg(Color c) { this.bg = c; repaint(); }
    public void setBorder2(Color c) { this.border = c; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc));
        if (border != null) {
            g2.setColor(border);
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, arc, arc));
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
