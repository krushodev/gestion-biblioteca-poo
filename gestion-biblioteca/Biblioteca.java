import java.util.ArrayList;
import java.util.HashSet;
import java.util.Calendar;

/**
 * Clase principal que gestiona la lógica de negocio de la biblioteca.
 * Administra las colecciones de {@link Socio} y {@link Libro}, y centraliza
 * las operaciones de préstamo, devolución y generación de informes.
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class Biblioteca {
    /** Nombre de la biblioteca. */
    private String nombre;
    /** Colección de todos los ejemplares de libros que posee la biblioteca. */
    private ArrayList<Libro> libros;
    /** Colección de todos los socios (Estudiantes y Docentes) registrados. */
    private ArrayList<Socio> socios;

    /**
     * Constructor para una nueva biblioteca.
     * Inicializa las colecciones de libros y socios vacías.
     *
     * @param p_nombre El nombre de la biblioteca.
     */
    public Biblioteca(String p_nombre) {
        this.setNombre(p_nombre);
        this.setLibros(new ArrayList<Libro>());
        this.setSocios(new ArrayList<Socio>());
    }

    /**
     * Constructor para inicializar la biblioteca con colecciones preexistentes.
     *
     * @param p_nombre El nombre de la biblioteca.
     * @param p_libros La lista inicial de libros.
     * @param p_socios La lista inicial de socios.
     */
    public Biblioteca(String p_nombre, ArrayList<Libro> p_libros, ArrayList<Socio> p_socios) {
        this.setNombre(p_nombre);
        this.setLibros(p_libros);
        this.setSocios(p_socios);
    }

    /**
     * Establece el nombre de la biblioteca.
     *
     * @param p_nombre Nombre a asignar.
     */
    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    /**
     * Establece la colección de libros.
     *
     * @param p_libros Colección de libros.
     */
    private void setLibros(ArrayList<Libro> p_libros) {
        this.libros = p_libros;
    }

    /**
     * Establece la colección de socios.
     *
     * @param p_socios Colección de socios.
     */
    private void setSocios(ArrayList<Socio> p_socios) {
        this.socios = p_socios;
    }

    /**
     * Obtiene el nombre de la biblioteca.
     *
     * @return El nombre de la biblioteca.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Obtiene la colección de libros de la biblioteca.
     *
     * @return Un ArrayList de {@link Libro}.
     */
    public ArrayList<Libro> getLibros() {
        return this.libros;
    }

    /**
     * Obtiene la colección de socios de la biblioteca.
     *
     * @return Un ArrayList de {@link Socio}.
     */
    public ArrayList<Socio> getSocios() {
        return this.socios;
    }

    /**
     * Añade un libro a la colección de la biblioteca.
     *
     * @param p_libro El libro a agregar.
     */
    public void agregarLibro(Libro p_libro) {
        this.getLibros().add(p_libro);
    }

    /**
     * Elimina un libro de la colección de la biblioteca.
     *
     * @param p_libro El libro a quitar.
     */
    public void quitarLibro(Libro p_libro) {
        this.getLibros().remove(p_libro);
    }

    /**
     * Añade un socio a la colección de la biblioteca.
     *
     * @param p_socio El socio a agregar.
     */
    public void agregarSocio(Socio p_socio) {
        this.getSocios().add(p_socio);
    }

    /**
     * Elimina un socio de la colección de la biblioteca.
     *
     * @param p_socio El socio a quitar.
     */
    public void quitarSocio(Socio p_socio) {
        this.getSocios().remove(p_socio);
    }

    /**
     * Crea e instancia un nuevo {@link Libro} y lo añade a la colección.
     *
     * @param p_titulo    Título del libro.
     * @param p_edicion   Número de edición.
     * @param p_editorial Editorial del libro.
     * @param p_anio      Año de publicación.
     */
    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio) {
        Libro nuevoLibro = new Libro(p_titulo, p_edicion, p_editorial, p_anio);
        this.agregarLibro(nuevoLibro);
    }

    /**
     * Crea e instancia un nuevo {@link Estudiante} y lo añade a la colección de
     * socios.
     *
     * @param p_dniSocio DNI del estudiante.
     * @param p_nombre   Nombre del estudiante.
     * @param p_carrera  Carrera del estudiante.
     */
    public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String p_carrera) {
        Estudiante nuevoEstudiante = new Estudiante(p_dniSocio, p_nombre, p_carrera);
        this.agregarSocio(nuevoEstudiante);
    }

    /**
     * Crea e instancia un nuevo {@link Docente} y lo añade a la colección de
     * socios.
     *
     * @param p_dniSocio DNI del docente.
     * @param p_nombre   Nombre del docente.
     * @param p_area     Área a la que pertenece el docente.
     */
    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area) {
        Docente nuevoDocente = new Docente(p_dniSocio, p_nombre, p_area);
        this.agregarSocio(nuevoDocente);
    }

    /**
     * Intenta registrar un nuevo préstamo.
     * Verifica que el socio esté habilitado ({@link Socio#puedePedir}) y que el
     * libro no esté prestado ({@link Libro#prestado}). Si ambas condiciones se
     * cumplen,
     * crea el {@link Prestamo} y lo asocia tanto al socio como al libro.
     *
     * @param p_fechaRetiro La fecha en que se realiza el préstamo.
     * @param p_socio       El socio que solicita el préstamo.
     * @param p_libro       El libro a prestar.
     * @return true si el préstamo fue exitoso, false en caso contrario.
     */
    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio,
            Libro p_libro) {
        boolean prestar = false;
        if (p_socio.puedePedir() && !p_libro.prestado()) {
            Prestamo nuevoPrestamo = new Prestamo(p_fechaRetiro, p_socio, p_libro);
            p_libro.agregarPrestamo(nuevoPrestamo); // Asienta en la ficha del libro
            p_socio.agregarPrestamo(nuevoPrestamo); // Asienta en la ficha del socio
            prestar = true;
        }
        return prestar;

    }

    /**
     * Registra la devolución de un libro.
     * Establece la fecha de devolución en el último préstamo del libro con la fecha
     * actual.
     *
     * @param p_libro El libro que se está devolviendo.
     * @throws LibroNoPrestadoException Si el libro no figura como prestado,
     *                                  lanzando
     *                                  un mensaje descriptivo.
     */
    public void devolverLibro(Libro p_libro) throws LibroNoPrestadoException {
        if (!p_libro.prestado()) {
            throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
        }

        Calendar fechaHoy = Calendar.getInstance();
        Prestamo ultimoPrestamo = p_libro.ultimoPrestamo();
        if (ultimoPrestamo != null) {
            // Asigna la fecha actual como fecha de devolución
            ultimoPrestamo.registrarFechaDevolucion(fechaHoy);
        }
    }

    /**
     * Cuenta la cantidad de socios que coinciden con un tipo específico.
     * La comparación es insensible a mayúsculas y minúsculas.
     *
     * @param p_objeto El tipo de socio a buscar (Ej: "Estudiante" o "Docente").
     * @return La cantidad de socios de ese tipo.
     */
    public int cantidadDeSociosPorTipo(String p_objeto) {
        int cantSocios = 0;
        for (Socio unSocio : this.getSocios()) {
            if (unSocio.soyDeLaClase().equalsIgnoreCase(p_objeto)) {
                cantSocios++;
            }
        }

        return cantSocios;
    }

    /**
     * Obtiene una colección de todos los préstamos activos que están vencidos
     * a la fecha actual.
     *
     * @return Un ArrayList de {@link Prestamo} vencidos y no devueltos.
     */
    public ArrayList<Prestamo> prestamosVencidos() {
        ArrayList<Prestamo> vencidos = new ArrayList<Prestamo>();
        Calendar fechaHoy = Calendar.getInstance();
        for (Libro unLibro : this.getLibros()) {
            for (Prestamo unPrestamo : unLibro.getPrestamos()) {
                // Un préstamo está vencido si la fecha de hoy es posterior a la de vencimiento
                // Y si aún no ha sido devuelto (fechaDevolucion == null)
                if (unPrestamo.vencido(fechaHoy) && unPrestamo.getFechaDevolucion() == null) {
                    vencidos.add(unPrestamo);
                }
            }
        }
        return vencidos;
    }

    /**
     * Obtiene una colección de docentes que son considerados "responsables".
     * Un docente es responsable si nunca ha tenido un préstamo vencido.
     *
     * @return Un ArrayList de {@link Docente} responsables.
     */
    public ArrayList<Docente> docentesResponsables() {
        ArrayList<Docente> docentesResponsables = new ArrayList<Docente>();
        for (Socio unSocio : this.getSocios()) {
            if (unSocio instanceof Docente) {
                if (((Docente) unSocio).esResponsable()) {
                    docentesResponsables.add((Docente) unSocio);
                }
            }
        }
        return docentesResponsables;
    }

    /**
     * Identifica qué socio tiene actualmente en su poder un libro determinado.
     *
     * @param p_libro El libro a consultar.
     * @return El nombre del socio que posee el libro.
     * @throws LibroNoPrestadoException Si el libro no está prestado (se encuentra
     *                                  en la biblioteca).
     */
    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException {
        if (p_libro.prestado()) {
            Socio socioConElLibro = p_libro.ultimoPrestamo().getSocio();
            StringBuilder sb = new StringBuilder();
            sb.append(socioConElLibro.getNombre());
            return sb.toString();
        } else {
            throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
        }
    }

    /**
     * Genera un informe (String) con el listado completo de socios,
     * siguiendo el "Formato1" especificado. Incluye un resumen
     * con la cantidad de socios por tipo.
     *
     * @return Un String formateado con la lista de socios.
     */
    public String listaDeSocios() {
        if (this.getSocios().isEmpty()) {
            return "No hay socios registrados.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Socios:\n");
        int i = 1;
        for (Socio unSocio : this.getSocios()) {
            sb.append(i++).append(") ").append(unSocio.toString()).append("\n");
        }

        // Añade el resumen de cantidades por tipo
        sb.append("**************************************\n");
        sb.append("Cantidad de Socios del tipo Estudiante: ")
                .append(cantidadDeSociosPorTipo("Estudiante")).append("\n");
        sb.append("Cantidad de Socios del tipo Docente: ")
                .append(cantidadDeSociosPorTipo("Docente")).append("\n");
        sb.append("**************************************");

        return sb.toString();
    }

    /**
     * Busca un socio en la colección por su DNI.
     *
     * @param p_dni El DNI del socio a buscar.
     * @return El objeto {@link Socio} si se encuentra, o null si no existe.
     */
    public Socio buscarSocio(int p_dni) {
        for (Socio unSocio : this.getSocios()) {
            if (unSocio.getDniSocio() == p_dni) {
                return unSocio;
            }
        }
        return null;
    }

    /**
     * Genera un informe (String) con todos los títulos de libros únicos
     * que posee la biblioteca. No repite títulos si hay varios ejemplares.
     *
     * @return Un String formateado con la lista de títulos.
     */
    public String listaDeTitulos() {
        if (this.getLibros().isEmpty()) {
            return "No hay títulos registrados.";
        }

        // Utiliza un HashSet para garantizar títulos únicos
        HashSet<String> titulos = new HashSet<String>();
        for (Libro unLibro : this.getLibros()) {
            titulos.add(unLibro.getTitulo());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Títulos Disponibles:\n");
        for (String unTitulo : titulos) {
            sb.append("- ").append(unTitulo).append("\n");
        }
        return sb.toString();
    }

    /**
     * Genera un informe (String) con el listado completo de ejemplares de libros,
     * siguiendo el "Formato2" especificado, indicando si está prestado o no.
     *
     * @return Un String formateado con la lista de libros (ejemplares).
     */
    public String listaDeLibros() {
        if (this.getLibros().isEmpty()) {
            return "No hay libros registrados.";
        }

        StringBuilder listaDeLibros = new StringBuilder();
        listaDeLibros.append("Lista de Libros:\n");

        for (int i = 0; i < this.getLibros().size(); i++) {
            listaDeLibros.append(i + 1)
                    .append(") ")
                    .append(this.getLibros().get(i).toString()) // Formato "Titulo: <titulo>"
                    .append(" || Prestado: ")
                    .append(this.getLibros().get(i).prestado() ? "(Si)" : "(No)")
                    .append("\n");
        }

        return listaDeLibros.toString();
    }

    /**
     * Genera un informe (String) con el listado de docentes responsables,
     * siguiendo el "Formato3" especificado.
     *
     * @return Un String formateado con la lista de docentes responsables.
     */
    public String listaDeDocentesResponsables() {
        ArrayList<Docente> docentesResponsables = this.docentesResponsables();

        if (docentesResponsables.isEmpty()) {
            return "No hay docentes responsables.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Docentes Responsables:\n");
        for (Docente unDocente : docentesResponsables) {
            sb.append("* ").append(unDocente.toString()).append("\n");
        }
        return sb.toString();
    }

}