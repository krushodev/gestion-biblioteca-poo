/**
 * Representa un socio de tipo Estudiante.
 * Hereda de {@link Socio} y aplica reglas de negocio específicas para
 * estudiantes,
 * como un límite de 3 préstamos simultáneos y un período de préstamo fijo de 20
 * días.
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class Estudiante extends Socio {
    /** La carrera que cursa el estudiante. */
    private String carrera;

    /**
     * Constructor para un nuevo Estudiante.
     * Inicializa los días de préstamo en 20, según la regla de negocio.
     *
     * @param p_dniSocio DNI del estudiante.
     * @param p_nombre   Nombre del estudiante.
     * @param p_carrera  Carrera que cursa el estudiante.
     */
    public Estudiante(int p_dniSocio, String p_nombre, String p_carrera) {
        super(p_dniSocio, p_nombre, 20); // 20 días fijos para estudiantes
        this.setCarrera(p_carrera);
    }

    /**
     * Establece la carrera del estudiante.
     *
     * @param p_carrera Nombre de la carrera.
     */
    private void setCarrera(String p_carrera) {
        this.carrera = p_carrera;
    }

    /**
     * Obtiene la carrera del estudiante.
     *
     * @return El nombre de la carrera.
     */
    public String getCarrera() {
        return this.carrera;
    }

    /**
     * Implementación del método abstracto de {@link Socio}.
     * Identifica a esta instancia como "Estudiante".
     *
     * @return El String "Estudiante".
     */
    @Override
    public String soyDeLaClase() {
        return "Estudiante";
    }

    /**
     * Verifica si el estudiante puede solicitar un préstamo.
     * Sobrescribe el método de {@link Socio}. Para un estudiante, se deben
     * cumplir dos condiciones:
     * 1. No tener préstamos vencidos (verificado por {@code super.puedePedir()}).
     * 2. Tener menos de 3 libros en su poder actualmente.
     *
     * @return true si el estudiante cumple ambas condiciones, false en caso
     *         contrario.
     */
    @Override
    public boolean puedePedir() {
        // Verifica la condición base (sin préstamos vencidos)
        // Y la condición específica (límite de 3 libros)
        return super.puedePedir() && this.cantLibrosPrestados() < 3;
    }
}