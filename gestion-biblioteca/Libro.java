import java.util.ArrayList;

/**
 * Representa una entidad Libro dentro de la biblioteca.
 * Contiene información bibliográfica (título, edición, etc.) y
 * un historial de sus préstamos.
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class Libro {
    /** Título del libro. */
    private String titulo;
    /** Número de edición. */
    private int edicion;
    /** Nombre de la editorial. */
    private String editorial;
    /** Año de publicación. */
    private int anio;
    /** Colección de todos los préstamos históricos y actuales del libro. */
    private ArrayList<Prestamo> prestamos;

    /**
     * Constructor para un nuevo libro sin historial de préstamos.
     *
     * @param p_titulo    Título del libro.
     * @param p_edicion   Número de edición.
     * @param p_editorial Editorial del libro.
     * @param p_anio      Año de publicación.
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio) {
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(new ArrayList<Prestamo>());
    }

    /**
     * Constructor para un libro al que se le asigna un historial de préstamos
     * existente.
     *
     * @param p_titulo    Título del libro.
     * @param p_edicion   Número de edición.
     * @param p_editorial Editorial del libro.
     * @param p_anio      Año de publicación.
     * @param p_prestamos Lista de préstamos del libro.
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio, ArrayList<Prestamo> p_prestamos) {
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(p_prestamos);
    }

    /**
     * Constructor para un libro con un único préstamo inicial.
     *
     * @param p_titulo    Título del libro.
     * @param p_edicion   Número de edición.
     * @param p_editorial Editorial del libro.
     * @param p_anio      Año de publicación.
     * @param p_prestamo  El préstamo inicial a agregar.
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio, Prestamo p_prestamo) {
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(new ArrayList<Prestamo>());
        this.getPrestamos().add(p_prestamo);
    }

    /**
     * Establece el título del libro.
     *
     * @param p_titulo Título del libro.
     */
    private void setTitulo(String p_titulo) {
        this.titulo = p_titulo;
    }

    /**
     * Establece la edición del libro.
     *
     * @param p_edicion Número de edición.
     */
    private void setEdicion(int p_edicion) {
        this.edicion = p_edicion;
    }

    /**
     * Establece la editorial del libro.
     *
     * @param p_editorial Nombre de la editorial.
     */
    private void setEditorial(String p_editorial) {
        this.editorial = p_editorial;
    }

    /**
     * Establece el año de publicación del libro.
     *
     * @param p_anio Año (ej. 2023).
     */
    private void setAnio(int p_anio) {
        this.anio = p_anio;
    }

    /**
     * Establece la colección de préstamos del libro.
     *
     * @param p_prestamos Lista de préstamos.
     */
    private void setPrestamos(ArrayList<Prestamo> p_prestamos) {
        this.prestamos = p_prestamos;
    }

    /**
     * Obtiene la lista completa de préstamos del libro.
     *
     * @return Un ArrayList de objetos Prestamo.
     */
    public ArrayList<Prestamo> getPrestamos() {
        return this.prestamos;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return El título del libro.
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Obtiene la edición del libro.
     *
     * @return El número de edición.
     */
    public int getEdicion() {
        return this.edicion;
    }

    /**
     * Obtiene la editorial del libro.
     *
     * @return El nombre de la editorial.
     */
    public String getEditorial() {
        return this.editorial;
    }

    /**
     * Obtiene el año de publicación del libro.
     *
     * @return El año de publicación.
     */
    public int getAnio() {
        return this.anio;
    }

    /**
     * Añade un nuevo préstamo al historial del libro (ficha de préstamos).
     *
     * @param p_prestamo El préstamo a registrar.
     */
    public void agregarPrestamo(Prestamo p_prestamo) {
        this.getPrestamos().add(p_prestamo);
    }

    /**
     * Elimina un préstamo del historial del libro.
     *
     * @param p_prestamo El préstamo a eliminar.
     */
    public void quitarPrestamo(Prestamo p_prestamo) {
        this.getPrestamos().remove(p_prestamo);
    }

    /**
     * Verifica si el libro está actualmente prestado.
     * Se basa en el último préstamo registrado y comprueba si tiene
     * una fecha de devolución nula.
     *
     * @return true si el libro está prestado, false si está disponible.
     */
    public boolean prestado() {
        Prestamo ultimoPrestamo = this.ultimoPrestamo();
        if (ultimoPrestamo == null) {
            return false; // Nunca fue prestado
        }
        // Está prestado si el último préstamo no tiene fecha de devolución
        return ultimoPrestamo.getFechaDevolucion() == null;
    }

    /**
     * Obtiene el préstamo más reciente registrado para este libro.
     *
     * @return El último objeto {@link Prestamo} en la lista, o null si el libro
     *         nunca ha sido prestado.
     */
    public Prestamo ultimoPrestamo() {
        if (this.getPrestamos().isEmpty()) {
            return null;
        }
        // Retorna el último elemento de la lista
        return this.getPrestamos().get(this.getPrestamos().size() - 1);
    }

    /**
     * Devuelve una representación textual del libro, mostrando su título.
     *
     * @return Un String formateado según la especificación: "Titulo: <<titulo>>".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titulo: ").append(this.getTitulo());
        return sb.toString();
    }
}