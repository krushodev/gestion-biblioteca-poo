import javax.swing.UIManager;
import java.awt.event.*;

public class GestionBiblioteca {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                crearYMostrarGUI();
            }
        });
    }

    private static void crearYMostrarGUI() {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }
}

class ManejadorCierre extends WindowAdapter {
    public void windowClosing(WindowEvent p_evento) {
        System.exit(0);
    }
}
