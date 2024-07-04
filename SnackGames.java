
import javax.swing.*;

public class SnackGames extends JFrame {
    SnackGames() {
        super("Snack Game");
        add(new Board());
        pack();
        setResizable(rootPaneCheckingEnabled);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SnackGames();
    }
}