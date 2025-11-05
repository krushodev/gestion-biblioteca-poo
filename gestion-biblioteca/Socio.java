import java.util.*;

/**
 * Clase abstracta que representa la entidad base de un miembro de la
 * biblioteca.
 * Define los atributos y comportamientos comunes para todos los tipos de
 * socios,
 * como la gestión de su historial de préstamos y la verificación de
 * condiciones básicas para solicitar nuevos préstamos.
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public abstract class Socio {
    /** Documento Nacional de Identidad del socio (clave única). */
    private int dniSocio;
    /** Nombre y apellido del socio. */
    private String nombre;
    /** Días base permitidos para la duración de un préstamo. */
    private int diasPrestamo;
    /** Colección de todos los préstamos históricos y actuales del socio. */
    private ArrayList<Prestamo> prestamos;

    /**
     * Constructor para un nuevo socio sin préstamos iniciales.
     *
     * @param p_dniSocio     DNI del socio.
     * @param p_nombre       Nombre del socio.
     * @param p_diasPrestamo Días de préstamo base asignados.
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo) {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<Prestamo>());
    }

    /**
     * Constructor para un socio con un préstamo inicial.
     *
     * @param p_dniSocio     DNI del socio.
     * @param p_nombre       Nombre del socio.
     * @param p_diasPrestamo Días de préstamo base asignados.
     * @param p_prestamo     Un préstamo inicial (actualmente no se utiliza en el
     *                       cuerpo).
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo, Prestamo p_prestamo) {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<Prestamo>());
        // Nota: El parámetro p_prestamo no se agrega a la lista en este constructor.
    }

    /**
     * Constructor para un socio al que se le asigna una lista de préstamos
     * existente.
     *
     * @param p_dniSocio     DNI del socio.
     * @param p_nombre       Nombre del socio.
     * @param p_diasPrestamo Días de préstamo base asignados.
     * @param p_prestamos    Lista de préstamos del socio.
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos) {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(p_prestamos);
    }

    /**
     * Establece el DNI del socio.
     *
     * @param p_dniSocio DNI numérico.
     */
    private void setDniSocio(int p_dniSocio) {
        this.dniSocio = p_dniSocio;
    }

    /**
     * Establece el nombre del socio.
     *
     * @param p_nombre Nombre y apellido.
     */
    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    /**
     * Establece la cantidad de días de préstamo permitidos.
     * Accesible por clases hijas (ej. Docente) para modificarlo.
     *
     * @param p_diasPrestamo Número de días.
     */
    protected void setDiasPrestamo(int p_diasPrestamo) {
        this.diasPrestamo = p_diasPrestamo;
    }

    /**
     * Establece la colección de préstamos del socio.
     *
     * @param p_prestamos Lista de préstamos.
     */
    private void setPrestamos(ArrayList<Prestamo> p_prestamos) {
        this.prestamos = p_prestamos;
    }

    /**
     * Obtiene el DNI del socio.
     *
     * @return El DNI del socio.
     */
    public int getDniSocio() {
        return this.dniSocio;
    }

    /**
     * Obtiene el nombre del socio.
     *
     * @return El nombre del socio.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Obtiene la cantidad de días de préstamo permitidos para este socio.
     *
     * @return El número de días.
     */
    public int getDiasPrestamo() {
        return this.diasPrestamo;
    }

    /**
     * Obtiene la lista completa de préstamos del socio (activos e históricos).
     *
     * @return Un ArrayList de objetos {@link Prestamo}.
     */
    public ArrayList<Prestamo> getPrestamos() {
        return this.prestamos;
    }

    /**
     * Añade un nuevo préstamo al historial del socio.
     *
     * @param p_prestamo El préstamo a registrar.
     * @return true si la adición fue exitosa (definido por ArrayList.add).
     */
    public boolean agregarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().add(p_prestamo);
    }

    /**
     * Elimina un préstamo del historial del socio.
     *
     * @param p_prestamo El préstamo a eliminar.
     * @return true si la eliminación fue exitosa (definido por ArrayList.remove).
     */
    public boolean quitarPrestamos(Prestamo p_prestamo) {
        return this.getPrestamos().remove(p_prestamo);
    }

    /**
     * Calcula la cantidad de libros que el socio tiene actualmente en su poder
     * (es decir, préstamos sin fecha de devolución registrada).
     *
     * @return El número de préstamos activos.
     */
    public int cantLibrosPrestados() {
        int count = 0;
        for (Prestamo unPrestamo : this.getPrestamos()) {
            // Cuenta solo los préstamos que no han sido devueltos
            if (unPrestamo.getFechaDevolucion() == null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Devuelve una representación textual del socio, formateada según las
     * especificaciones del proyecto (Formato1).
     * Utiliza el método abstracto {@link #soyDeLaClase} para el polimorfismo.
     *
     * @return Un String formateado con DNI, nombre, tipo y cantidad de libros
     *         prestados.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("D.N.I.: ").append(this.getDniSocio());
        sb.append(" || ").append(this.getNombre());
        sb.append(" (").append(this.soyDeLaClase()).append(")"); // Polimorfismo
        sb.append(" || Libros Prestados: ").append(this.cantLibrosPrestados());
        return sb.toString();
    }

    /**
     * Verifica la condición base para que un socio pueda solicitar un préstamo.
     * La regla base, aplicable a todos los socios, es no tener préstamos vencidos
     * que aún no hayan sido devueltos.
     * Este método está diseñado para ser extendido por las subclases.
     *
     * @return true si el socio no tiene préstamos activos vencidos, false en caso
     *         contrario.
     */
    public boolean puedePedir() {
        Calendar hoy = Calendar.getInstance();
        for (Prestamo unPrestamo : this.getPrestamos()) {
            // Verifica solo los préstamos activos (no devueltos)
            if (unPrestamo.getFechaDevolucion() == null && unPrestamo.vencido(hoy)) {
                return false; // Encontró un préstamo activo y vencido
            }
        }
        return true; // No se encontraron préstamos activos vencidos
    }

    /**
     * Método abstracto (plantilla) que debe ser implementado por las clases
     * concretas
     * (Estudiante, Docente) para devolver una cadena que identifique su tipo.
     *
     * @return Un String que representa el tipo de socio (ej. "Docente").
     */
    public abstract String soyDeLaClase();
}