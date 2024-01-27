import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main ui = new Main();
                ui.setVisible(true);
            }
        });
    }
}
