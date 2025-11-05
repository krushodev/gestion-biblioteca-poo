import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel implements ActionListener {

    private VentanaPrincipal ventana;
    private JTextField campoUsuario;
    private JPasswordField campoPass;
    private JButton botonLogin;
    private JLabel etiquetaError;

    private final Color colorAccion = new Color(0x3B82F6);
    private final Color colorPanel = Color.WHITE;
    private final Color colorTexto = new Color(0x1F2937);
    private final Color colorTextoBlanco = Color.WHITE;

    public LoginPanel(VentanaPrincipal ventanaPrincipal) {
        this.setVentana(ventanaPrincipal);
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(this.colorPanel);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(this.colorAccion);

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.gridwidth = GridBagConstraints.REMAINDER;
        gbcInfo.insets = new Insets(5, 20, 5, 20);

        JLabel tituloApp = new JLabel("Gestión de");
        tituloApp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        tituloApp.setForeground(this.colorTextoBlanco);
        infoPanel.add(tituloApp, gbcInfo);

        JLabel subTituloApp = new JLabel("Biblioteca");
        subTituloApp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        subTituloApp.setForeground(this.colorTextoBlanco);
        infoPanel.add(subTituloApp, gbcInfo);

        this.add(infoPanel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(this.colorPanel);

        JPanel formCentrado = new JPanel(new GridBagLayout());
        formCentrado.setBackground(this.colorPanel);

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(8, 8, 8, 8);
        gbcForm.anchor = GridBagConstraints.WEST;

        JLabel tituloForm = new JLabel("Iniciar Sesión");
        tituloForm.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        tituloForm.setForeground(this.colorTexto);
        gbcForm.gridwidth = 2;
        gbcForm.anchor = GridBagConstraints.CENTER;
        gbcForm.insets = new Insets(10, 8, 25, 8);
        formCentrado.add(tituloForm, gbcForm);

        gbcForm.gridwidth = 1;
        gbcForm.anchor = GridBagConstraints.WEST;
        gbcForm.insets = new Insets(8, 8, 8, 8);

        JLabel eUsuario = new JLabel("\uD83D\uDC64 Usuario:");
        eUsuario.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        gbcForm.gridy = 1;
        gbcForm.gridx = 0;
        formCentrado.add(eUsuario, gbcForm);

        this.setCampoUsuario(new JTextField("admin", 20));
        this.getCampoUsuario().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        gbcForm.gridy = 1;
        gbcForm.gridx = 1;
        formCentrado.add(this.getCampoUsuario(), gbcForm);

        JLabel ePass = new JLabel("\uD83D\uDD12 Contraseña:");
        ePass.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        gbcForm.gridy = 2;
        gbcForm.gridx = 0;
        formCentrado.add(ePass, gbcForm);

        this.setCampoPass(new JPasswordField("admin123", 20));
        this.getCampoPass().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        gbcForm.gridy = 2;
        gbcForm.gridx = 1;
        formCentrado.add(this.getCampoPass(), gbcForm);

        this.setEtiquetaError(new JLabel(" "));
        this.getEtiquetaError().setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        this.getEtiquetaError().setForeground(Color.RED);
        gbcForm.gridy = 3;
        gbcForm.gridx = 1;
        formCentrado.add(this.getEtiquetaError(), gbcForm);

        this.setBotonLogin(new JButton("Ingresar \u279C"));
        this.getBotonLogin().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        this.getBotonLogin().setBackground(this.colorPanel);
        this.getBotonLogin().setForeground(this.colorTexto);
        this.getBotonLogin().setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.getBotonLogin().setFocusPainted(false);
        this.getBotonLogin().setOpaque(true);
        this.getBotonLogin().setContentAreaFilled(true);
        this.getBotonLogin().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(this.colorAccion, 2),
                new EmptyBorder(10, 28, 10, 28)));

        // Se guarda referencia final a botonLogin para usar en el MouseAdapter
        final JButton botonLoginReferencia = this.getBotonLogin();
        final Color colorAccionFinal = this.colorAccion;
        final Color colorPanelFinal = this.colorPanel;
        final Color colorTextoBlancoFinal = this.colorTextoBlanco;
        final Color colorTextoFinal = this.colorTexto;

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

        gbcForm.gridy = 4;
        gbcForm.gridx = 1;
        gbcForm.anchor = GridBagConstraints.EAST;
        formCentrado.add(this.getBotonLogin(), gbcForm);

        formPanel.add(formCentrado);
        this.add(formPanel);

        this.getBotonLogin().addActionListener(this);
        this.getCampoPass().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object fuente = event.getSource();
        if (fuente == this.getBotonLogin() || fuente == this.getCampoPass()) {
            String user = this.getCampoUsuario().getText();
            String pass = new String(this.getCampoPass().getPassword());

            if (user.equals("admin") && pass.equals("admin123")) {
                this.getVentana().mostrarPortal();
            } else {
                this.getEtiquetaError().setText("Usuario o contraseña incorrectos.");
            }
        }
    }

    private void setVentana(VentanaPrincipal p_ventana) {
        this.ventana = p_ventana;
    }

    public VentanaPrincipal getVentana() {
        return this.ventana;
    }

    private void setCampoUsuario(JTextField p_campoUsuario) {
        this.campoUsuario = p_campoUsuario;
    }

    public JTextField getCampoUsuario() {
        return this.campoUsuario;
    }

    private void setCampoPass(JPasswordField p_campoPass) {
        this.campoPass = p_campoPass;
    }

    public JPasswordField getCampoPass() {
        return this.campoPass;
    }

    private void setEtiquetaError(JLabel p_etiquetaError) {
        this.etiquetaError = p_etiquetaError;
    }

    public JLabel getEtiquetaError() {
        return this.etiquetaError;
    }

    private void setBotonLogin(JButton p_botonLogin) {
        this.botonLogin = p_botonLogin;
    }

    public JButton getBotonLogin() {
        return this.botonLogin;
    }

    public void resetearCampos() {
        this.getCampoUsuario().setText("admin");
        this.getCampoPass().setText("admin123");
        this.getEtiquetaError().setText(" ");
    }
}