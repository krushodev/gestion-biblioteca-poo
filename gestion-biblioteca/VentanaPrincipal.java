import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class VentanaPrincipal extends JFrame {
    private Biblioteca biblioteca;
    private LoginPanel panelLogin;
    private PortalPanel panelPortal;

    private final Color colorFondoBase = new Color(0x1F2937);

    private final String ARCHIVO_SOCIOS = "socios.dat";
    private final String ARCHIVO_LIBROS = "libros.dat";
    private final String ARCHIVO_PRESTAMOS = "prestamos.dat";

    public VentanaPrincipal() {
        this.setBiblioteca(new Biblioteca("Biblioteca Central"));
        this.setPanelLogin(new LoginPanel(this));
        this.setPanelPortal(new PortalPanel(this));

        this.cargarDatosDesdeArchivos();

        this.setTitle("Gestión de Biblioteca");
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(colorFondoBase);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarDatosEnArchivos();
                System.exit(0);
            }
        });

        Container container = this.getContentPane();
        container.add(this.getPanelLogin());
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

    private void guardarDatosEnArchivos() {
        try (DataOutputStream dosSocios = new DataOutputStream(new FileOutputStream(ARCHIVO_SOCIOS))) {
            for (Socio unSocio : this.getBiblioteca().getSocios()) {
                dosSocios.writeUTF(unSocio.soyDeLaClase());
                dosSocios.writeInt(unSocio.getDniSocio());
                dosSocios.writeUTF(unSocio.getNombre());

                if (unSocio instanceof Estudiante) {
                    dosSocios.writeUTF(((Estudiante) unSocio).getCarrera());
                } else if (unSocio instanceof Docente) {
                    dosSocios.writeUTF(((Docente) unSocio).getArea());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar socios: " + e.getMessage());
        }

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

        try (DataOutputStream dosPrestamos = new DataOutputStream(new FileOutputStream(ARCHIVO_PRESTAMOS))) {
            for (Socio unSocio : this.getBiblioteca().getSocios()) {
                for (Prestamo unPrestamo : unSocio.getPrestamos()) {
                    if (unPrestamo.getFechaDevolucion() == null) {
                        dosPrestamos.writeInt(unSocio.getDniSocio());
                        dosPrestamos.writeUTF(unPrestamo.getLibro().getTitulo());
                        dosPrestamos.writeLong(unPrestamo.getFechaRetiro().getTimeInMillis());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar préstamos: " + e.getMessage());
        }
    }

    private void cargarDatosDesdeArchivos() {
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

        try (DataInputStream disPrestamos = new DataInputStream(new FileInputStream(ARCHIVO_PRESTAMOS))) {
            while (disPrestamos.available() > 0) {
                int dniSocio = disPrestamos.readInt();
                String tituloLibro = disPrestamos.readUTF();
                long fechaMillis = disPrestamos.readLong();

                Socio unSocio = this.getBiblioteca().buscarSocio(dniSocio);
                Libro unLibro = this.buscarLibroPorTitulo(tituloLibro);

                if (unSocio != null && unLibro != null) {
                    Calendar fechaRetiro = new GregorianCalendar();
                    fechaRetiro.setTimeInMillis(fechaMillis);

                    this.getBiblioteca().prestarLibro(fechaRetiro, unSocio, unLibro);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró " + ARCHIVO_PRESTAMOS + ", se creará uno nuevo al cerrar.");
        } catch (IOException e) {
            System.err.println("Error al cargar préstamos: " + e.getMessage());
        }
    }

    public void mostrarPortal() {
        Container container = this.getContentPane();
        container.removeAll();
        container.add(this.getPanelPortal());
        container.revalidate();
        container.repaint();
    }

    public void mostrarLogin() {
        Container container = this.getContentPane();
        container.removeAll();
        container.add(this.getPanelLogin());
        this.getPanelLogin().resetearCampos();
        container.revalidate();
        container.repaint();
    }

    public Libro buscarLibroPorTitulo(String p_titulo) {
        for (Libro unLibro : this.getBiblioteca().getLibros()) {
            if (unLibro.getTitulo().equalsIgnoreCase(p_titulo)) {
                return unLibro;
            }
        }
        return null;
    }
}