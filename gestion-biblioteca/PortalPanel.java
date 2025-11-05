import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Panel (JPanel) principal de la aplicación.
 * Muestra el menú de acciones (sidebar) y un área de resultados donde se
 * visualiza la información solicitada. Maneja toda la interacción del usuario
 * con la lógica de la biblioteca ({@link Biblioteca}).
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class PortalPanel extends JPanel implements ActionListener {
    /**
     * Referencia a la ventana principal para acceder a la lógica y cambiar de
     * panel.
     */
    private VentanaPrincipal ventana;
    /** Área de texto donde se muestran los resultados de las operaciones. */
    private JTextArea areaResultados;

    // Constantes de colores para la UI del portal
    private final Color colorFondoPrincipal = new Color(0x1F2937);
    private final Color colorSidebar = new Color(0x111827);
    private final Color colorAccion = new Color(0x3B82F6);
    private final Color colorSeparador = new Color(0x374151);
    private final Color colorBoton = Color.WHITE;
    private final Color colorBotonTexto = new Color(0x111827);
    private final Color colorTextoTitulo = colorAccion;
    private final Color colorTextoSeccion = new Color(0xD1D5DB);
    private final Color colorPanelContenido = Color.WHITE;
    private final Color colorTextoContenido = new Color(0x111827);
    private final Color colorRojoBase = new Color(0xDC2626);
    private final Color colorRojoHover = new Color(0xF87171);

    /**
     * Constructor del panel del portal.
     * Inicializa y organiza el menú lateral (sidebar) y el panel de contenido
     * central.
     *
     * @param ventanaPrincipal La {@link VentanaPrincipal} que contiene este panel.
     */
    public PortalPanel(VentanaPrincipal ventanaPrincipal) {
        this.setVentana(ventanaPrincipal);
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(this.getColorFondoPrincipal());

        // --- Panel de Menú (Sidebar Izquierdo) ---
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(this.getColorSidebar());
        panelMenu.setBorder(new MatteBorder(0, 0, 0, 1, this.getColorSeparador()));
        panelMenu.setPreferredSize(new Dimension(320, 0));

        JLabel tituloMenu = new JLabel("Biblioteca");
        tituloMenu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        tituloMenu.setForeground(this.getColorTextoTitulo());
        tituloMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloMenu.setBorder(new EmptyBorder(25, 15, 25, 15));
        panelMenu.add(tituloMenu);

        // --- Secciones y Botones del Menú ---
        panelMenu.add(this.crearTituloSeccion("Gestión de Socios"));
        panelMenu.add(this.crearBotonMenu("Agregar Estudiante", "\uD83D\uDC68\u200D\uD83C\uDF93"));
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(this.crearBotonMenu("Agregar Docente", "\uD83D\uDC68\u200D\uD83C\uDFEB"));
        panelMenu.add(Box.createVerticalStrut(20));

        panelMenu.add(this.crearTituloSeccion("Gestión de Libros"));
        panelMenu.add(this.crearBotonMenu("Agregar Libro", "\u2795"));
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(this.crearBotonMenu("Prestar Libro", "\uD83D\uDCD7"));
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(this.crearBotonMenu("Devolver Libro", "\uD83D\uDCD9"));
        panelMenu.add(Box.createVerticalStrut(20));

        panelMenu.add(this.crearTituloSeccion("Reportes"));
        panelMenu.add(this.crearBotonMenu("Listar Socios", "\uD83D\uDCD1"));
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(this.crearBotonMenu("Listar Libros", "\uD83D\uDCDA"));
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(this.crearBotonMenu("Listar Títulos", "\uD83D\uDCCB"));
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(this.crearBotonMenu("Ver Docentes Responsables", "\uD83D\uDC65"));
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(this.crearBotonMenu("Ver Préstamos Vencidos", "\u23F0"));
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(this.crearBotonMenu("Quién tiene un Libro", "\uD83D\uDD0E"));

        panelMenu.add(Box.createVerticalGlue()); // Empuja el botón de logout hacia abajo

        // --- Botón de Cerrar Sesión ---
        panelMenu.add(Box.createVerticalStrut(10));
        JButton btnLogout = new JButton("\u274C  Cerrar Sesión");
        btnLogout.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(this.getColorRojoBase());

        btnLogout.setBorder(new EmptyBorder(13, 25, 13, 25));
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogout.setOpaque(true);
        btnLogout.setContentAreaFilled(true);
        btnLogout.setBorderPainted(false);

        btnLogout.setMaximumSize(new Dimension(280, 56));
        btnLogout.setActionCommand("Cerrar Sesión");
        btnLogout.addActionListener(this);

        // Efectos Hover del botón Logout
        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnLogout.setBackground(colorRojoHover);
            }

            public void mouseExited(MouseEvent evt) {
                btnLogout.setBackground(colorRojoBase);
            }
        });

        panelMenu.add(btnLogout);
        panelMenu.add(Box.createVerticalStrut(20));

        this.add(panelMenu, BorderLayout.WEST);

        // --- Panel Central (Contenido y Resultados) ---
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(this.getColorFondoPrincipal());
        panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

        this.setAreaResultados(new JTextArea("¡Bienvenido al sistema!\nSeleccione una opción del menú."));
        this.getAreaResultados().setEditable(false);
        this.getAreaResultados().setFont(new Font("Monospaced", Font.PLAIN, 18));
        this.getAreaResultados().setMargin(new Insets(15, 15, 15, 15));
        this.getAreaResultados().setBackground(this.getColorPanelContenido());
        this.getAreaResultados().setForeground(this.getColorTextoContenido());

        JScrollPane scrollArea = new JScrollPane(this.getAreaResultados());
        scrollArea.setBorder(BorderFactory.createLineBorder(this.getColorSeparador()));

        panelCentral.add(scrollArea, BorderLayout.CENTER);
        this.add(panelCentral, BorderLayout.CENTER);
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
     * Establece el área de texto para mostrar resultados.
     *
     * @param p_areaResultados El componente JTextArea.
     */
    private void setAreaResultados(JTextArea p_areaResultados) {
        this.areaResultados = p_areaResultados;
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
     * Obtiene el área de texto de resultados.
     *
     * @return El JTextArea.
     */
    public JTextArea getAreaResultados() {
        return this.areaResultados;
    }

    // --- Getters de Constantes de UI ---

    public Color getColorFondoPrincipal() {
        return this.colorFondoPrincipal;
    }

    public Color getColorSidebar() {
        return this.colorSidebar;
    }

    public Color getColorAccion() {
        return this.colorAccion;
    }

    public Color getColorSeparador() {
        return this.colorSeparador;
    }

    public Color getColorBoton() {
        return this.colorBoton;
    }

    public Color getColorBotonTexto() {
        return this.colorBotonTexto;
    }

    public Color getColorTextoTitulo() {
        return this.colorTextoTitulo;
    }

    public Color getColorTextoSeccion() {
        return this.colorTextoSeccion;
    }

    public Color getColorPanelContenido() {
        return this.colorPanelContenido;
    }

    public Color getColorTextoContenido() {
        return this.colorTextoContenido;
    }

    public Color getColorRojoBase() {
        return this.colorRojoBase;
    }

    public Color getColorRojoHover() {
        return this.colorRojoHover;
    }

    /**
     * Método de fábrica para crear etiquetas de título de sección estandarizadas.
     *
     * @param texto El texto del título.
     * @return Un JLabel formateado como título de sección.
     */
    private JLabel crearTituloSeccion(String texto) {
        JLabel titulo = new JLabel(texto.toUpperCase());
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        titulo.setForeground(this.colorTextoSeccion);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(10, 25, 5, 25));
        titulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, titulo.getPreferredSize().height));
        return titulo;
    }

    /**
     * Método de fábrica para crear botones de menú estandarizados.
     * Incluye estilos, efectos hover y asignación de ActionListener.
     *
     * @param texto El texto del botón (y su ActionCommand).
     * @param icono Un icono (string, ej. emoji) para el botón.
     * @return Un JButton formateado como botón de menú.
     */
    private JButton crearBotonMenu(String texto, String icono) {
        JButton boton = new JButton(icono + "  " + texto);
        boton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        boton.setForeground(this.getColorBotonTexto());
        boton.setBackground(this.getColorBoton());
        boton.setBorder(new EmptyBorder(13, 25, 13, 25));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorderPainted(false);
        boton.setActionCommand(texto); // Usa el texto como comando

        // Efectos Hover del botón de menú
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(colorAccion);
                boton.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent evt) {
                boton.setBackground(colorBoton);
                boton.setForeground(colorBotonTexto);
            }
        });

        boton.addActionListener(this);
        boton.setMinimumSize(new Dimension(280, 56));
        boton.setMaximumSize(new Dimension(280, 56));
        return boton;
    }

    /**
     * Manejador central de eventos para todos los botones del menú.
     * Identifica la acción solicitada por su "ActionCommand", ejecuta la lógica
     * de negocio correspondiente (interactuando con {@link Biblioteca}) y
     * muestra el resultado en el {@code areaResultados}.
     *
     * @param p_evento El evento de acción generado.
     */
    @Override
    public void actionPerformed(ActionEvent p_evento) {
        String comando = p_evento.getActionCommand();
        Biblioteca biblioteca = this.getVentana().getBiblioteca();
        String resultado = "";

        try {
            // ---- Acción de Salida ----
            if (comando.equals("Cerrar Sesión")) {
                this.getVentana().mostrarLogin();
                return; // Termina la ejecución aquí
            }

            // ---- Acciones de Reportes ----
            if (comando.equals("Listar Socios")) {
                resultado = biblioteca.listaDeSocios();
            } else if (comando.equals("Listar Libros")) {
                resultado = biblioteca.listaDeLibros();
            } else if (comando.equals("Listar Títulos")) {
                resultado = biblioteca.listaDeTitulos();
            } else if (comando.equals("Ver Docentes Responsables")) {
                resultado = biblioteca.listaDeDocentesResponsables();
            } else if (comando.equals("Ver Préstamos Vencidos")) {
                ArrayList<Prestamo> vencidos = biblioteca.prestamosVencidos();
                if (vencidos.isEmpty()) {
                    resultado = "No hay préstamos vencidos a la fecha.";
                } else {
                    StringBuilder sb = new StringBuilder("--- PRÉSTAMOS VENCIDOS ---\n\n");
                    for (Prestamo unPrestamo : vencidos) {
                        sb.append(unPrestamo.toString()).append("\n---------------------------------\n");
                    }
                    resultado = sb.toString();
                }

                // ---- Acciones de Gestión de Socios ----
            } else if (comando.equals("Agregar Estudiante")) {
                String dni = JOptionPane.showInputDialog(this, "Ingrese DNI:", "Nuevo Estudiante",
                        JOptionPane.PLAIN_MESSAGE);
                if (dni == null || dni.trim().isEmpty())
                    return; // Cancelado por el usuario
                String nombre = JOptionPane.showInputDialog(this, "Ingrese Nombre:", "Nuevo Estudiante",
                        JOptionPane.PLAIN_MESSAGE);
                if (nombre == null || nombre.trim().isEmpty())
                    return;
                String carrera = JOptionPane.showInputDialog(this, "Ingrese Carrera:", "Nuevo Estudiante",
                        JOptionPane.PLAIN_MESSAGE);
                if (carrera == null || carrera.trim().isEmpty())
                    return;

                biblioteca.nuevoSocioEstudiante(Integer.parseInt(dni), nombre, carrera);
                resultado = "Estudiante agregado exitosamente.\n\n" + biblioteca.listaDeSocios();

            } else if (comando.equals("Agregar Docente")) {
                String dni = JOptionPane.showInputDialog(this, "Ingrese DNI:", "Nuevo Docente",
                        JOptionPane.PLAIN_MESSAGE);
                if (dni == null || dni.trim().isEmpty())
                    return;
                String nombre = JOptionPane.showInputDialog(this, "Ingrese Nombre:", "Nuevo Docente",
                        JOptionPane.PLAIN_MESSAGE);
                if (nombre == null || nombre.trim().isEmpty())
                    return;
                String area = JOptionPane.showInputDialog(this, "Ingrese Área:", "Nuevo Docente",
                        JOptionPane.PLAIN_MESSAGE);
                if (area == null || area.trim().isEmpty())
                    return;

                biblioteca.nuevoSocioDocente(Integer.parseInt(dni), nombre, area);
                resultado = "Docente agregado exitosamente.\n\n" + biblioteca.listaDeSocios();

                // ---- Acciones de Gestión de Libros ----
            } else if (comando.equals("Agregar Libro")) {
                String titulo = JOptionPane.showInputDialog(this, "Ingrese Título:", "Nuevo Libro",
                        JOptionPane.PLAIN_MESSAGE);
                if (titulo == null || titulo.trim().isEmpty())
                    return;
                String ed = JOptionPane.showInputDialog(this, "Ingrese Edición (número):", "Nuevo Libro",
                        JOptionPane.PLAIN_MESSAGE);
                if (ed == null || ed.trim().isEmpty())
                    return;
                String edit = JOptionPane.showInputDialog(this, "Ingrese Editorial:", "Nuevo Libro",
                        JOptionPane.PLAIN_MESSAGE);
                if (edit == null || edit.trim().isEmpty())
                    return;
                String anio = JOptionPane.showInputDialog(this, "Ingrese Año (número):", "Nuevo Libro",
                        JOptionPane.PLAIN_MESSAGE);
                if (anio == null || anio.trim().isEmpty())
                    return;

                biblioteca.nuevoLibro(titulo, Integer.parseInt(ed), edit, Integer.parseInt(anio));
                resultado = "Libro agregado exitosamente.\n\n" + biblioteca.listaDeLibros();

            } else if (comando.equals("Prestar Libro")) {
                String dni = JOptionPane.showInputDialog(this, "DNI del Socio:", "Prestar Libro",
                        JOptionPane.PLAIN_MESSAGE);
                if (dni == null || dni.trim().isEmpty())
                    return;

                String titulo = JOptionPane.showInputDialog(this, "Título del Libro:", "Prestar Libro",
                        JOptionPane.PLAIN_MESSAGE);
                if (titulo == null || titulo.trim().isEmpty())
                    return;

                // Captura de fecha
                String anioStr = JOptionPane.showInputDialog(this, "Ingrese Año (YYYY):", "Fecha de Préstamo",
                        JOptionPane.PLAIN_MESSAGE);
                if (anioStr == null || anioStr.trim().isEmpty())
                    return;
                String mesStr = JOptionPane.showInputDialog(this, "Ingrese Mes (1-12):", "Fecha de Préstamo",
                        JOptionPane.PLAIN_MESSAGE);
                if (mesStr == null || mesStr.trim().isEmpty())
                    return;
                String diaStr = JOptionPane.showInputDialog(this, "Ingrese Día:", "Fecha de Préstamo",
                        JOptionPane.PLAIN_MESSAGE);
                if (diaStr == null || diaStr.trim().isEmpty())
                    return;

                int anio = Integer.parseInt(anioStr);
                int mes = Integer.parseInt(mesStr);
                int dia = Integer.parseInt(diaStr);

                Calendar fechaPrestamo = new GregorianCalendar(anio, mes - 1, dia); // Calendar usa meses 0-11

                Socio socio = biblioteca.buscarSocio(Integer.parseInt(dni));
                // Utiliza el método de búsqueda de la ventana principal
                Libro libro = this.getVentana().buscarLibroPorTitulo(titulo);

                if (socio == null)
                    throw new Exception("Socio no encontrado (DNI: " + dni + ")");
                if (libro == null)
                    throw new Exception("Libro no encontrado (Título: " + titulo + ")");

                if (biblioteca.prestarLibro(fechaPrestamo, socio, libro)) {
                    resultado = "Préstamo registrado exitosamente (Fecha: " + dia + "/" + mes + "/" + anio + ")\n\n"
                            + biblioteca.listaDeLibros();
                } else {
                    // Genera un mensaje de error detallado si el préstamo falla
                    resultado = "Error: No se pudo realizar el préstamo.\n";
                    if (libro.prestado())
                        resultado += "- El libro ya está prestado.\n";
                    if (!socio.puedePedir())
                        resultado += "- El socio no cumple los requisitos para pedir (límite de libros o préstamos vencidos).";
                }

            } else if (comando.equals("Devolver Libro")) {
                String titulo = JOptionPane.showInputDialog(this, "Título del Libro a devolver:", "Devolver Libro",
                        JOptionPane.PLAIN_MESSAGE);
                if (titulo == null || titulo.trim().isEmpty())
                    return;

                Libro libro = this.getVentana().buscarLibroPorTitulo(titulo);
                if (libro == null)
                    throw new Exception("Libro no encontrado (Título: " + titulo + ")");

                // Llama a la lógica de negocio para devolver
                biblioteca.devolverLibro(libro);
                resultado = "Devolución registrada exitosamente.\n\n" + biblioteca.listaDeLibros();

            } else if (comando.equals("Quién tiene un Libro")) {
                String titulo = JOptionPane.showInputDialog(this, "Título del Libro a consultar:", "Consultar Préstamo",
                        JOptionPane.PLAIN_MESSAGE);
                if (titulo == null || titulo.trim().isEmpty())
                    return;

                Libro libro = this.getVentana().buscarLibroPorTitulo(titulo);
                if (libro == null)
                    throw new Exception("Libro no encontrado (Título: " + titulo + ")");

                resultado = "El libro '" + libro.getTitulo() + "' está en poder de: "
                        + biblioteca.quienTieneElLibro(libro);
            }

            // ---- Muestra el Resultado Exitoso ----
            this.getAreaResultados().setForeground(this.getColorTextoContenido());
            this.getAreaResultados().setText(resultado);

        } catch (Exception ex) {
            // ---- Manejo de Excepciones (ej. ParseException, LibroNoPrestadoException)
            // ----
            this.getAreaResultados().setForeground(Color.RED);
            this.getAreaResultados().setText("Error: " + ex.getMessage());
        }

        // Asegura que el scroll del área de resultados vuelva al inicio
        this.getAreaResultados().setCaretPosition(0);
    }
}