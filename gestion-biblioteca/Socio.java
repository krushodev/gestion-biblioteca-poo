import java.util.*;

public abstract class Socio {
    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    private ArrayList<Prestamo> prestamos;

    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo) {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<Prestamo>());
    }

    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo, Prestamo p_prestamo) {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<Prestamo>());
    }

    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos) {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(p_prestamos);
    }

    private void setDniSocio(int p_dniSocio) {
        this.dniSocio = p_dniSocio;
    }

    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    protected void setDiasPrestamo(int p_diasPrestamo) {
        this.diasPrestamo = p_diasPrestamo;
    }

    private void setPrestamos(ArrayList<Prestamo> p_prestamos) {
        this.prestamos = p_prestamos;
    }

    public int getDniSocio() {
        return this.dniSocio;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getDiasPrestamo() {
        return this.diasPrestamo;
    }

    public ArrayList<Prestamo> getPrestamos() {
        return this.prestamos;
    }

    public boolean agregarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().add(p_prestamo);
    }

    public boolean quitarPrestamos(Prestamo p_prestamo) {
        return this.getPrestamos().remove(p_prestamo);
    }

    public int cantLibrosPrestados() {
        int count = 0;
        for (Prestamo unPrestamo : this.getPrestamos()) {
            if (unPrestamo.getFechaDevolucion() == null) {
                count++;
            }
        }
        return count;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("D.N.I.: ").append(this.getDniSocio());
        sb.append(" || ").append(this.getNombre());
        sb.append(" (").append(this.soyDeLaClase()).append(")");
        sb.append(" || Libros Prestados: ").append(this.cantLibrosPrestados());
        return sb.toString();
    }

    public boolean puedePedir() {
        Calendar hoy = Calendar.getInstance();
        for (Prestamo unPrestamo : this.getPrestamos()) {
            if (unPrestamo.getFechaDevolucion() == null && unPrestamo.vencido(hoy)) {
                return false;
            }
        }
        return true;
    }

    public abstract String soyDeLaClase();
}