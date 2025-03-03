package test;

import gui.MenuGui;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Run {

    public static void main(String[] args) {
        try {
            // FlatLaf Dark Look and Feel
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Menü GUI'sini başlat
        new MenuGui();
    }
}
