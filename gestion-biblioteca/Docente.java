import java.util.Calendar;

/**
 * Representa un socio de tipo Docente.
 * Hereda de {@link Socio} y aplica reglas de negocio específicas para docentes,
 * como la ausencia de límite de préstamos y la capacidad de ser "responsable".
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class Docente extends Socio {
    /** El área o departamento al que pertenece el docente. */
    private String area;

    /**
     * Constructor para un nuevo Docente.
     * Inicializa los días de préstamo en 5, según la regla de negocio.
     *
     * @param p_dniSocio DNI del docente.
     * @param p_nombre   Nombre del docente.
     * @param p_area     Área del docente.
     */
    public Docente(int p_dniSocio, String p_nombre, String p_area) {
        super(p_dniSocio, p_nombre, 5); // 5 días iniciales para docentes
        this.setArea(p_area);
    }

    /**
     * Establece el área del docente.
     *
     * @param p_area Área a asignar.
     */
    private void setArea(String p_area) {
        this.area = p_area;
    }

    /**
     * Obtiene el área del docente.
     *
     * @return El área del docente.
     */
    public String getArea() {
        return this.area;
    }

    /**
     * Verifica si el docente puede solicitar un préstamo.
     * Sobrescribe el método de {@link Socio}. Para un docente, la única
     * condición es no tener préstamos vencidos ({@code super.puedePedir()}).
     * No hay límite en la cantidad de libros.
     *
     * @return true si el docente no tiene préstamos activos vencidos.
     */
    @Override
    public boolean puedePedir() {
        return super.puedePedir();
    }

    /**
     * Verifica si el docente es "responsable".
     * Un docente es responsable si nunca ha devuelto un libro tarde
     * y no tiene préstamos vencidos actualmente.
     *
     * @return true si el docente siempre ha cumplido con las fechas de devolución.
     */
    public boolean esResponsable() {
        Calendar fechaHoy = Calendar.getInstance();
        for (Prestamo unPrestamo : this.getPrestamos()) {
            if (unPrestamo.getFechaDevolucion() == null) {
                // Caso 1: Préstamo activo (aún no devuelto)
                // Si está vencido hoy, no es responsable.
                if (unPrestamo.vencido(fechaHoy)) {
                    return false;
                }
            } else {
                // Caso 2: Préstamo histórico (ya devuelto)
                // Si la fecha de devolución es posterior a la fecha de vencimiento, no es
                // responsable.
                if (unPrestamo.vencido(unPrestamo.getFechaDevolucion())) {
                    return false;
                }
            }
        }
        return true; // Si supera todas las verificaciones, es responsable
    }

    /**
     * Añade días al período de préstamo base del docente.
     * Se utiliza como premio a la responsabilidad.
     *
     * @param p_dias Días adicionales a sumar al total actual.
     */
    public void cambiarDiasDePrestamo(int p_dias) {
        int totalDias = p_dias + this.getDiasPrestamo();
        this.setDiasPrestamo(totalDias);
    }

    /**
     * Implementación del método abstracto de {@link Socio}.
     * Identifica a esta instancia como "Docente".
     *
     * @return El String "Docente".
     */
    @Override
    public String soyDeLaClase() {
        return "Docente";
    }
}