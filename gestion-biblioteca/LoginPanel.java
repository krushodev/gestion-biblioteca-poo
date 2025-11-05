import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel (JPanel) que implementa la interfaz de inicio de sesión (Login).
 * Contiene campos para usuario y contraseña, y maneja la lógica de
 * autenticación
 * básica para dar acceso al portal principal.
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class LoginPanel extends JPanel implements ActionListener {
    /** Referencia a la ventana principal para gestionar el cambio de paneles. */
    private VentanaPrincipal ventana;
    /** Campo de texto para el nombre de usuario. */
    private JTextField campoUsuario;
    /** Campo de texto para la contraseña. */
    private JPasswordField campoPass;
    /** Botón para iniciar el intento de login. */
    private JButton botonLogin;
    /** Etiqueta para mostrar mensajes de error de autenticación. */
    private JLabel etiquetaError;

    // Constantes de colores para la UI
    private final Color colorAccion = new Color(0x3B82F6);
    private final Color colorPanel = Color.WHITE;
    private final Color colorTexto = new Color(0x1F2937);
    private final Color colorTextoBlanco = Color.WHITE;

    // Credenciales de autenticación (hardcodeadas)
    private final String USUARIO_DEFAULT = "admin";
    private final String CONTRASEÑA_DEFAULT = "admin123";

    /**
     * Constructor del panel de Login.
     * Inicializa y organiza todos los componentes de la interfaz gráfica.
     *
     * @param p_ventana La {@link VentanaPrincipal} que contiene este panel.
     */
    public LoginPanel(VentanaPrincipal p_ventana) {
        this.setVentana(p_ventana);
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(this.getColorPanel());

        // --- Panel Izquierdo (Decorativo) ---
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(this.getColorAccion());

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.gridwidth = GridBagConstraints.REMAINDER;
        gbcInfo.insets = new Insets(5, 20, 5, 20);

        JLabel tituloApp = new JLabel("Gestión de");
        tituloApp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        tituloApp.setForeground(this.getColorTextoBlanco());
        infoPanel.add(tituloApp, gbcInfo);

        JLabel subTituloApp = new JLabel("Biblioteca");
        subTituloApp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        subTituloApp.setForeground(this.getColorTextoBlanco());
        infoPanel.add(subTituloApp, gbcInfo);

        this.add(infoPanel);

        // --- Panel Derecho (Formulario) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(this.getColorPanel());

        JPanel formCentrado = new JPanel(new GridBagLayout());
        formCentrado.setBackground(this.getColorPanel());

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(8, 8, 8, 8);
        gbcForm.anchor = GridBagConstraints.WEST;

        JLabel tituloForm = new JLabel("Iniciar Sesión");
        tituloForm.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        tituloForm.setForeground(this.getColorTexto());
        gbcForm.gridwidth = 2;
        gbcForm.anchor = GridBagConstraints.CENTER;
        gbcForm.insets = new Insets(10, 8, 25, 8);
        formCentrado.add(tituloForm, gbcForm);

        // Restablece gbc a valores por defecto
        gbcForm.gridwidth = 1;
        gbcForm.anchor = GridBagConstraints.WEST;
        gbcForm.insets = new Insets(8, 8, 8, 8);

        JLabel eUsuario = new JLabel("\uD83D\uDC64 Usuario:");
        eUsuario.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        gbcForm.gridy = 1;
        gbcForm.gridx = 0;
        formCentrado.add(eUsuario, gbcForm);

        this.setCampoUsuario(new JTextField(this.getUsuarioDefault(), 20));
        this.getCampoUsuario().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        gbcForm.gridy = 1;
        gbcForm.gridx = 1;
        formCentrado.add(this.getCampoUsuario(), gbcForm);

        JLabel ePass = new JLabel("\uD83D\uDD12 Contraseña:");
        ePass.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        gbcForm.gridy = 2;
        gbcForm.gridx = 0;
        formCentrado.add(ePass, gbcForm);

        this.setCampoPass(new JPasswordField(this.getContraseñaDefault(), 20));
        this.getCampoPass().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        gbcForm.gridy = 2;
        gbcForm.gridx = 1;
        formCentrado.add(this.getCampoPass(), gbcForm);

        this.setEtiquetaError(new JLabel(" ")); // Espacio para mantener el layout
        this.getEtiquetaError().setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        this.getEtiquetaError().setForeground(Color.RED);
        gbcForm.gridy = 3;
        gbcForm.gridx = 1;
        formCentrado.add(this.getEtiquetaError(), gbcForm);

        this.setBotonLogin(new JButton("Ingresar \u279C"));
        this.getBotonLogin().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        this.getBotonLogin().setBackground(this.getColorPanel());
        this.getBotonLogin().setForeground(this.getColorTexto());
        this.getBotonLogin().setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.getBotonLogin().setFocusPainted(false);
        this.getBotonLogin().setOpaque(true);
        this.getBotonLogin().setContentAreaFilled(true);
        this.getBotonLogin().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(this.getColorAccion(), 2),
                new EmptyBorder(10, 28, 10, 28)));

        // --- Efectos Hover del Botón ---
        // Se guardan referencias finales para usar en el MouseAdapter anónimo
        final JButton botonLoginReferencia = this.getBotonLogin();
        final Color colorAccionFinal = this.getColorAccion();
        final Color colorPanelFinal = this.getColorPanel();
        final Color colorTextoBlancoFinal = this.getColorTextoBlanco();
        final Color colorTextoFinal = this.getColorTexto();

        this.getBotonLogin().addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                botonLoginReferencia.setBackground(colorAccionFinal);
                botonLoginReferencia.setForeground(colorTextoBlancoFinal);
            }

            public void mouseExited(MouseEvent evt) {
                botonLoginReferencia.setBackground(colorPanelFinal);
                botonLoginReferencia.setForeground(colorTextoFinal);
            }
        });
        // --- Fin Efectos Hover ---

        gbcForm.gridy = 4;
        gbcForm.gridx = 1;
        gbcForm.anchor = GridBagConstraints.EAST;
        formCentrado.add(this.getBotonLogin(), gbcForm);

        formPanel.add(formCentrado);
        this.add(formPanel);

        // --- Listeners ---
        this.getBotonLogin().addActionListener(this);
        this.getCampoPass().addActionListener(this); // Permite login con "Enter" en el campo pass
    }

    /**
     * Maneja los eventos de acción del panel.
     * Escucha el clic en {@code botonLogin} o la tecla "Enter" en
     * {@code campoPass}.
     * Valida las credenciales y, si son correctas, notifica a la ventana principal
     * para cambiar al {@code PortalPanel}.
     *
     * @param p_evento El evento de acción generado.
     */
    @Override
    public void actionPerformed(ActionEvent p_evento) {
        Object fuente = p_evento.getSource();
        if (fuente == this.getBotonLogin() || fuente == this.getCampoPass()) {
            String usuario = this.getCampoUsuario().getText();
            String contraseña = new String(this.getCampoPass().getPassword());

            if (usuario.equals(this.getUsuarioDefault()) && contraseña.equals(this.getContraseñaDefault())) {
                this.getVentana().mostrarPortal(); // Autenticación exitosa
            } else {
                this.getEtiquetaError().setText("Usuario o contraseña incorrectos.");
            }
        }
    }

    /**
     * Establece la referencia a la ventana principal.
     *
     * @param p_ventana La instancia de VentanaPrincipal.
     */
    private void setVentana(VentanaPrincipal p_ventana) {
        this.ventana = p_ventana;
    }

    /**
     * Establece el componente JTextField para el usuario.
     *
     * @param p_campoUsuario El campo de texto.
     */
    private void setCampoUsuario(JTextField p_campoUsuario) {
        this.campoUsuario = p_campoUsuario;
    }

    /**
     * Establece el componente JPasswordField para la contraseña.
     *
     * @param p_campoPass El campo de contraseña.
     */
    private void setCampoPass(JPasswordField p_campoPass) {
        this.campoPass = p_campoPass;
    }

    /**
     * Establece el componente JButton para el botón de login.
     *
     * @param p_botonLogin El botón de ingreso.
     */
    private void setBotonLogin(JButton p_botonLogin) {
        this.botonLogin = p_botonLogin;
    }

    /**
     * Establece el componente JLabel para los mensajes de error.
     *
     * @param p_etiquetaError La etiqueta de error.
     */
    private void setEtiquetaError(JLabel p_etiquetaError) {
        this.etiquetaError = p_etiquetaError;
    }

    /**
     * Obtiene la referencia a la ventana principal.
     *
     * @return La VentanaPrincipal.
     */
    public VentanaPrincipal getVentana() {
        return this.ventana;
    }

    /**
     * Obtiene el campo de texto del usuario.
     *
     * @return El JTextField del usuario.
     */
    public JTextField getCampoUsuario() {
        return this.campoUsuario;
    }

    /**
     * Obtiene el campo de contraseña.
     *
     * @return El JPasswordField de la contraseña.
     */
    public JPasswordField getCampoPass() {
        return this.campoPass;
    }

    /**
     * Obtiene el botón de login.
     *
     * @return El JButton de login.
     */
    public JButton getBotonLogin() {
        return this.botonLogin;
    }

    /**
     * Obtiene la etiqueta de error.
     *
     * @return El JLabel de error.
     */
    public JLabel getEtiquetaError() {
        return this.etiquetaError;
    }

    // --- Getters de Constantes de UI ---

    /**
     * Obtiene el color de acento principal.
     * 
     * @return Color de acento.
     */
    public Color getColorAccion() {
        return this.colorAccion;
    }

    /**
     * Obtiene el color de fondo del panel de formulario.
     * 
     * @return Color del panel (blanco).
     */
    public Color getColorPanel() {
        return this.colorPanel;
    }

    /**
     * Obtiene el color de texto principal (oscuro).
     * 
     * @return Color de texto.
     */
    public Color getColorTexto() {
        return this.colorTexto;
    }

    /**
     * Obtiene el color de texto secundario (blanco).
     * 
     * @return Color de texto (blanco).
     */
    public Color getColorTextoBlanco() {
        return this.colorTextoBlanco;
    }

    /**
     * Obtiene el nombre de usuario por defecto.
     * 
     * @return String del usuario.
     */
    public String getUsuarioDefault() {
        return this.USUARIO_DEFAULT;
    }

    /**
     * Obtiene la contraseña por defecto.
     * 
     * @return String de la contraseña.
     */
    public String getContraseñaDefault() {
        return this.CONTRASEÑA_DEFAULT;
    }

    /**
     * Restablece los campos del formulario a sus valores por defecto
     * y limpia la etiqueta de error.
     * Se utiliza al cerrar sesión para limpiar el panel.
     */
    public void resetearCampos() {
        this.getCampoUsuario().setText(this.getUsuarioDefault());
        this.getCampoPass().setText(this.getContraseñaDefault());
        this.getEtiquetaError().setText(" "); // Restablece el espacio
    }
}