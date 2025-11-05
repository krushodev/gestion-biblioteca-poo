import javax.swing.UIManager;
import java.awt.event.*;

/**
 * Clase principal (Main) de la aplicación de Gestión de Biblioteca.
 * Su responsabilidad es configurar el "Look and Feel" visual de Swing
 * e iniciar la interfaz gráfica de usuario (GUI) en el hilo de despacho de
 * eventos (EDT).
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class GestionBiblioteca {
    /**
     * Punto de entrada de la aplicación.
     * Establece el Look and Feel del sistema nativo e invoca
     * la creación de la GUI en el hilo de Swing.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        try {
            // Configura el Look and Feel para que coincida con el sistema operativo
            // nativo.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Asegura que la creación de la GUI se ejecute en el Hilo de Despacho de
        // Eventos (EDT).
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            /**
             * Tarea a ejecutar en el EDT para inicializar la GUI.
             */
            @Override
            public void run() {
                crearYMostrarGUI();
            }
        });
    }

    /**
     * Método auxiliar estático para instanciar y mostrar la ventana principal.
     */
    private static void crearYMostrarGUI() {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }
}

/**
 * Manejador de eventos de cierre de ventana.
 * Asegura que la aplicación finalice correctamente (System.exit(0))
 * cuando se cierra la ventana principal.
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 * @deprecated Esta clase no es utilizada en la implementación final, ya que
 *             {@link VentanaPrincipal} utiliza un WindowAdapter anónimo en
 *             línea.
 */
class ManejadorCierre extends WindowAdapter {
    /**
     * Invocado cuando el usuario intenta cerrar la ventana desde los controles
     * nativos.
     * Finaliza la ejecución de la JVM.
     *
     * @param p_evento El evento de ventana generado.
     */
    @Override
    public void windowClosing(WindowEvent p_evento) {
        System.exit(0);
    }
}