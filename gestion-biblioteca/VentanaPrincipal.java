import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Ventana principal (JFrame) de la aplicación de gestión de biblioteca.
 * Actúa como el contenedor central que gestiona la lógica de la
 * {@link Biblioteca},
 * la persistencia de datos (guardado y carga en archivos) y la navegación
 * entre los paneles principales ({@link LoginPanel} y {@link PortalPanel}).
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class VentanaPrincipal extends JFrame {
    /** Instancia central de la lógica de negocio de la biblioteca. */
    private Biblioteca biblioteca;
    /** Panel que gestiona la interfaz de inicio de sesión. */
    private LoginPanel panelLogin;
    /**
     * Panel que gestiona la interfaz principal de la aplicación (menú y reportes).
     */
    private PortalPanel panelPortal;

    /** Color de fondo base para el contenedor principal. */
    private final Color colorFondoBase = new Color(0x1F2937);

    // Nombres de archivos para la persistencia de datos
    private final String ARCHIVO_SOCIOS = "socios.dat";
    private final String ARCHIVO_LIBROS = "libros.dat";
    private final String ARCHIVO_PRESTAMOS = "prestamos.dat";

    /**
     * Constructor de la VentanaPrincipal.
     * Inicializa la biblioteca, los paneles de Login y Portal, carga los
     * datos persistidos desde archivos y configura las propiedades de la ventana.
     */
    public VentanaPrincipal() {
        this.setBiblioteca(new Biblioteca("Biblioteca Central"));
        this.setPanelLogin(new LoginPanel(this));
        this.setPanelPortal(new PortalPanel(this));

        this.cargarDatosDesdeArchivos(); // Carga el estado anterior

        this.setTitle("Gestión de Biblioteca");
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null); // Centra la ventana
        this.getContentPane().setBackground(colorFondoBase);

        // Configura el guardado de datos al cerrar la ventana
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            /**
             * Intercepta el evento de cierre de ventana para ejecutar el guardado
             * antes de finalizar la aplicación.
             * 
             * @param e El evento de ventana.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                guardarDatosEnArchivos();
                System.exit(0); // Cierra la aplicación
            }
        });

        // Inicia mostrando el panel de Login
        Container container = this.getContentPane();
        container.add(this.getPanelLogin());
    }

    /**
     * Establece la instancia de la biblioteca.
     *
     * @param p_biblioteca La instancia de Biblioteca.
     */
    private void setBiblioteca(Biblioteca p_biblioteca) {
        this.biblioteca = p_biblioteca;
    }

    /**
     * Establece el panel de inicio de sesión.
     *
     * @param p_panelLogin La instancia de LoginPanel.
     */
    private void setPanelLogin(LoginPanel p_panelLogin) {
        this.panelLogin = p_panelLogin;
    }

    /**
     * Establece el panel del portal principal.
     *
     * @param p_panelPortal La instancia de PortalPanel.
     */
    private void setPanelPortal(PortalPanel p_panelPortal) {
        this.panelPortal = p_panelPortal;
    }

    /**
     * Obtiene la instancia central de la biblioteca.
     *
     * @return La instancia de Biblioteca.
     */
    public Biblioteca getBiblioteca() {
        return this.biblioteca;
    }

    /**
     * Obtiene el panel de inicio de sesión.
     *
     * @return La instancia de LoginPanel.
     */
    public LoginPanel getPanelLogin() {
        return this.panelLogin;
    }

    /**
     * Obtiene el panel del portal principal.
     *
     * @return La instancia de PortalPanel.
     */
    public PortalPanel getPanelPortal() {
        return this.panelPortal;
    }

    /**
     * Guarda el estado actual de la biblioteca (Socios, Libros y Préstamos activos)
     * en archivos binarios (.dat) para persistencia.
     */
    private void guardarDatosEnArchivos() {

        // --- Guardar Socios ---
        try (DataOutputStream dosSocios = new DataOutputStream(new FileOutputStream(ARCHIVO_SOCIOS))) {
            for (Socio unSocio : this.getBiblioteca().getSocios()) {
                dosSocios.writeUTF(unSocio.soyDeLaClase()); // Identificador de tipo
                dosSocios.writeInt(unSocio.getDniSocio());
                dosSocios.writeUTF(unSocio.getNombre());

                // Guarda el atributo específico de la subclase
                if (unSocio instanceof Estudiante) {
                    dosSocios.writeUTF(((Estudiante) unSocio).getCarrera());
                } else if (unSocio instanceof Docente) {
                    dosSocios.writeUTF(((Docente) unSocio).getArea());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar socios: " + e.getMessage());
        }

        // --- Guardar Libros ---
        try (DataOutputStream dosLibros = new DataOutputStream(new FileOutputStream(ARCHIVO_LIBROS))) {
            for (Libro unLibro : this.getBiblioteca().getLibros()) {
                dosLibros.writeUTF(unLibro.getTitulo());
                dosLibros.writeInt(unLibro.getEdicion());
                dosLibros.writeUTF(unLibro.getEditorial());
                dosLibros.writeInt(unLibro.getAnio());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar libros: " + e.getMessage());
        }

        // --- Guardar Préstamos Activos ---
        try (DataOutputStream dosPrestamos = new DataOutputStream(new FileOutputStream(ARCHIVO_PRESTAMOS))) {
            for (Socio unSocio : this.getBiblioteca().getSocios()) {
                for (Prestamo unPrestamo : unSocio.getPrestamos()) {
                    // Solo guarda préstamos que no han sido devueltos
                    if (unPrestamo.getFechaDevolucion() == null) {
                        dosPrestamos.writeInt(unSocio.getDniSocio()); // DNI para vincular al Socio
                        dosPrestamos.writeUTF(unPrestamo.getLibro().getTitulo()); // Título para vincular al Libro
                        dosPrestamos.writeLong(unPrestamo.getFechaRetiro().getTimeInMillis()); // Fecha como long
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar préstamos: " + e.getMessage());
        }
    }

    /**
     * Carga el estado de la biblioteca desde los archivos binarios (.dat).
     * Reconstruye los objetos Socio, Libro y Prestamo.
     * Maneja {@link FileNotFoundException} si es la primera ejecución.
     */
    private void cargarDatosDesdeArchivos() {

        // --- Cargar Socios ---
        try (DataInputStream disSocios = new DataInputStream(new FileInputStream(ARCHIVO_SOCIOS))) {
            while (disSocios.available() > 0) {
                String tipo = disSocios.readUTF();
                int dni = disSocios.readInt();
                String nombre = disSocios.readUTF();

                if (tipo.equals("Estudiante")) {
                    String carrera = disSocios.readUTF();
                    this.getBiblioteca().nuevoSocioEstudiante(dni, nombre, carrera);
                } else if (tipo.equals("Docente")) {
                    String area = disSocios.readUTF();
                    this.getBiblioteca().nuevoSocioDocente(dni, nombre, area);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró " + ARCHIVO_SOCIOS + ", se creará uno nuevo al cerrar.");
        } catch (IOException e) {
            System.err.println("Error al cargar socios: " + e.getMessage());
        }

        // --- Cargar Libros ---
        try (DataInputStream disLibros = new DataInputStream(new FileInputStream(ARCHIVO_LIBROS))) {
            while (disLibros.available() > 0) {
                String titulo = disLibros.readUTF();
                int edicion = disLibros.readInt();
                String editorial = disLibros.readUTF();
                int anio = disLibros.readInt();
                this.getBiblioteca().nuevoLibro(titulo, edicion, editorial, anio);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró " + ARCHIVO_LIBROS + ", se creará uno nuevo al cerrar.");
        } catch (IOException e) {
            System.err.println("Error al cargar libros: " + e.getMessage());
        }

        // --- Cargar Préstamos Activos ---
        // (Debe ejecutarse después de cargar Socios y Libros)
        try (DataInputStream disPrestamos = new DataInputStream(new FileInputStream(ARCHIVO_PRESTAMOS))) {
            while (disPrestamos.available() > 0) {
                int dniSocio = disPrestamos.readInt();
                String tituloLibro = disPrestamos.readUTF();
                long fechaMillis = disPrestamos.readLong();

                Socio unSocio = this.getBiblioteca().buscarSocio(dniSocio);
                Libro unLibro = this.buscarLibroPorTitulo(tituloLibro); // Usa el método de esta clase

                if (unSocio != null && unLibro != null) {
                    Calendar fechaRetiro = new GregorianCalendar();
                    fechaRetiro.setTimeInMillis(fechaMillis);

                    // Re-crea el préstamo
                    this.getBiblioteca().prestarLibro(fechaRetiro, unSocio, unLibro);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró " + ARCHIVO_PRESTAMOS + ", se creará uno nuevo al cerrar.");
        } catch (IOException e) {
            System.err.println("Error al cargar préstamos: " + e.getMessage());
        }
    }

    /**
     * Cambia el panel visible del contenedor principal al {@link PortalPanel}.
     * Se invoca tras un inicio de sesión exitoso.
     */
    public void mostrarPortal() {
        Container container = this.getContentPane();
        container.removeAll();
        container.add(this.getPanelPortal());
        container.revalidate(); // Actualiza el layout
        container.repaint(); // Redibuja la ventana
    }

    /**
     * Cambia el panel visible del contenedor principal al {@link LoginPanel}.
     * Se invoca al cerrar sesión desde el portal.
     */
    public void mostrarLogin() {
        Container container = this.getContentPane();
        container.removeAll();
        container.add(this.getPanelLogin());
        this.getPanelLogin().resetearCampos(); // Limpia los campos de la sesión anterior
        container.revalidate();
        container.repaint();
    }

    /**
     * Método de búsqueda auxiliar para encontrar un libro por su título.
     * La búsqueda es insensible a mayúsculas/minúsculas.
     *
     * @param p_titulo El título del libro a buscar.
     * @return El objeto {@link Libro} si se encuentra, o null si no existe.
     */
    public Libro buscarLibroPorTitulo(String p_titulo) {
        for (Libro unLibro : this.getBiblioteca().getLibros()) {
            if (unLibro.getTitulo().equalsIgnoreCase(p_titulo)) {
                return unLibro;
            }
        }
        return null; // No encontrado
    }
}