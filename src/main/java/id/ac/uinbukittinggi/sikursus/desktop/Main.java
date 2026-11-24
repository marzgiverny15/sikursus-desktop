
package id.ac.uinbukittinggi.sikursus.desktop;

import id.ac.uinbukittinggi.sikursus.auth.LoginFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
