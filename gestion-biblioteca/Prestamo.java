import java.util.Calendar;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Representa un préstamo de un {@link Libro} a un {@link Socio}.
 * Esta clase actúa como el vínculo entre un socio y un libro, registrando
 * las fechas clave de la transacción (retiro y devolución).
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class Prestamo {
    /** Fecha en que el libro fue retirado por el socio. */
    private Calendar fechaRetiro;
    /**
     * Fecha en que el libro fue devuelto. Null si aún está prestado.
     */
    private Calendar fechaDevolucion;
    /** El socio que realizó el préstamo. */
    private Socio socio;
    /** El libro que fue objeto del préstamo. */
    private Libro libro;

    /**
     * Constructor para un préstamo que ya ha sido completado (histórico).
     *
     * @param p_fechaRetiro     La fecha de retiro.
     * @param p_fechaDevolucion La fecha de devolución.
     * @param p_socio           El socio involucrado.
     * @param p_libro           El libro involucrado.
     */
    public Prestamo(Calendar p_fechaRetiro, Calendar p_fechaDevolucion, Socio p_socio, Libro p_libro) {
        this.setFechaRetiro(p_fechaRetiro);
        this.setFechaDevolucion(p_fechaDevolucion);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }

    /**
     * Constructor para un nuevo préstamo que se está realizando.
     * La fecha de devolución se inicializa automáticamente como nula.
     *
     * @param p_fechaRetiro La fecha de retiro.
     * @param p_socio       El socio que retira el libro.
     * @param p_libro       El libro que es retirado.
     */
    public Prestamo(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        this.setFechaRetiro(p_fechaRetiro);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
        this.setFechaDevolucion(null); // Indica que es un préstamo activo
    }

    /**
     * Establece la fecha de retiro del préstamo.
     *
     * @param p_fechaRetiro Objeto Calendar con la fecha de retiro.
     */
    private void setFechaRetiro(Calendar p_fechaRetiro) {
        this.fechaRetiro = p_fechaRetiro;
    }

    /**
     * Establece la fecha de devolución del préstamo.
     *
     * @param p_fechaDevolucion Objeto Calendar con la fecha de devolución (puede
     *                          ser
     *                          null).
     */
    private void setFechaDevolucion(Calendar p_fechaDevolucion) {
        this.fechaDevolucion = p_fechaDevolucion;
    }

    /**
     * Asigna el socio al préstamo.
     *
     * @param p_socio El objeto Socio involucrado.
     */
    private void setSocio(Socio p_socio) {
        this.socio = p_socio;
    }

    /**
     * Asigna el libro al préstamo.
     *
     * @param p_libro El objeto Libro involucrado.
     */
    private void setLibro(Libro p_libro) {
        this.libro = p_libro;
    }

    /**
     * Obtiene la fecha de retiro del préstamo.
     *
     * @return Objeto Calendar con la fecha de retiro.
     */
    public Calendar getFechaRetiro() {
        return this.fechaRetiro;
    }

    /**
     * Obtiene la fecha de devolución del préstamo.
     *
     * @return Objeto Calendar con la fecha de devolución, o null si no ha sido
     *         devuelto.
     */
    public Calendar getFechaDevolucion() {
        return this.fechaDevolucion;
    }

    /**
     * Obtiene el socio asociado a este préstamo.
     *
     * @return El objeto Socio.
     */
    public Socio getSocio() {
        return this.socio;
    }

    /**
     * Obtiene el libro asociado a este préstamo.
     *
     * @return El objeto Libro.
     */
    public Libro getLibro() {
        return this.libro;
    }

    /**
     * Registra la fecha en que el libro fue devuelto, completando el
     * préstamo.
     *
     * @param p_fecha La fecha de devolución (normalmente la fecha
     *                actual).
     */
    public void registrarFechaDevolucion(Calendar p_fecha) {
        this.setFechaDevolucion(p_fecha);
    }

    /**
     * Verifica si el préstamo está vencido en una fecha determinada.
     * Calcula la fecha de vencimiento sumando los {@code diasPrestamo} del socio
     * a la {@code fechaRetiro}, y la compara con la fecha proporcionada.
     *
     * @param p_fecha La fecha contra la cual se compara (normalmente la fecha
     *                actual).
     * @return true si {@code p_fecha} es posterior a la fecha de vencimiento,
     *         false en caso contrario.
     */
    public boolean vencido(Calendar p_fecha) {
        Calendar fechaRetiro = this.getFechaRetiro();

        // Se clona la fecha de retiro para no modificar el original al calcular
        Calendar fechaVencimiento = new GregorianCalendar(fechaRetiro.get(Calendar.YEAR),
                fechaRetiro.get(Calendar.MONTH), fechaRetiro.get(Calendar.DAY_OF_MONTH));

        // Añade los días permitidos según el tipo de socio
        fechaVencimiento.add(Calendar.DAY_OF_YEAR, this.getSocio().getDiasPrestamo());

        // Un préstamo está vencido si la fecha actual (p_fecha) es posterior (after)
        // a la fecha de vencimiento.
        return p_fecha.after(fechaVencimiento);
    }

    /**
     * Método auxiliar para formatear un objeto Calendar a un String legible
     * (yyyy/MM/dd).
     *
     * @param fecha El objeto Calendar a formatear.
     * @return String con la fecha formateada, o "----/--/--" si la fecha es nula.
     */
    private String formatFecha(Calendar fecha) {
        if (fecha == null) {
            return "----/--/--";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(fecha.getTime());
    }

    /**
     * Proporciona una representación textual del préstamo, según el formato
     * especificado.
     *
     * @return Un String formateado con los detalles del préstamo (fechas, libro,
     *         socio).
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Retiro: ").append(this.formatFecha(this.getFechaRetiro()));
        sb.append(" - Devolución: ").append(this.formatFecha(this.getFechaDevolucion()));
        sb.append("\nLibro: ").append(this.getLibro().getTitulo());
        sb.append("\nSocio: ").append(this.getSocio().getNombre());
        return sb.toString();
    }
}