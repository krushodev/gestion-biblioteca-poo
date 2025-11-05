import javax.swing.JFrame;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final Color colorFondoBase = new Color(0x1F2937);

    private Biblioteca biblioteca;
    private LoginPanel panelLogin;
    private PortalPanel panelPortal;

    public VentanaPrincipal() {
        super("Gestión de Biblioteca");
        this.setBiblioteca(new Biblioteca("Biblioteca Central"));
        /* cargarDatosIniciales(); */

        this.setPanelLogin(new LoginPanel(this));
        this.setPanelPortal(new PortalPanel(this));

        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);

        this.getContentPane().setBackground(colorFondoBase);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new ManejadorCierre());

        Container container = this.getContentPane();
        container.add(panelLogin);
    }

    private void setBiblioteca(Biblioteca p_biblioteca) {
        this.biblioteca = p_biblioteca;
    }

    private void setPanelLogin(LoginPanel p_panelLogin) {
        this.panelLogin = p_panelLogin;
    }

    private void setPanelPortal(PortalPanel p_panelPortal) {
        this.panelPortal = p_panelPortal;
    }

    public Biblioteca getBiblioteca() {
        return this.biblioteca;
    }

    public LoginPanel getPanelLogin() {
        return this.panelLogin;
    }

    public PortalPanel getPanelPortal() {
        return this.panelPortal;
    }

    public void mostrarPortal() {
        Container container = this.getContentPane();
        container.removeAll();
        container.add(panelPortal);
        container.revalidate();
        container.repaint();
    }

    public void mostrarLogin() {
        Container container = this.getContentPane();
        container.removeAll();
        container.add(panelLogin);
        panelLogin.resetearCampos();
        container.revalidate();
        container.repaint();
    }

    public Libro buscarLibroPorTitulo(String titulo) {
        for (Libro libro : this.biblioteca.getLibros()) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    /*
     * private void cargarDatosIniciales() {
     * 
     * }
     */
}