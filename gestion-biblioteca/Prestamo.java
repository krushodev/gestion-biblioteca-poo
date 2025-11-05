import java.util.Calendar;
import java.util.*;
import java.text.SimpleDateFormat;

public class Prestamo {
    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    private Socio socio;
    private Libro libro;

    public Prestamo(Calendar p_fechaRetiro, Calendar p_fechaDevolucion, Socio p_socio, Libro p_libro) {
        this.setFechaRetiro(p_fechaRetiro);
        this.setFechaDevolucion(p_fechaDevolucion);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }

    public Prestamo(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        this.setFechaRetiro(p_fechaRetiro);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
        this.setFechaDevolucion(null);
    }

    private void setFechaRetiro(Calendar p_fechaRetiro) {
        this.fechaRetiro = p_fechaRetiro;
    }

    private void setFechaDevolucion(Calendar p_fechaDevolucion) {
        this.fechaDevolucion = p_fechaDevolucion;
    }

    private void setSocio(Socio p_socio) {
        this.socio = p_socio;
    }

    private void setLibro(Libro p_libro) {
        this.libro = p_libro;
    }

    public Calendar getFechaRetiro() {
        return this.fechaRetiro;
    }

    public Calendar getFechaDevolucion() {
        return this.fechaDevolucion;
    }

    public Socio getSocio() {
        return this.socio;
    }

    public Libro getLibro() {
        return this.libro;
    }

    public void registrarFechaDevolucion(Calendar p_fecha) {
        this.setFechaDevolucion(p_fecha);
    }

    public boolean vencido(Calendar p_fecha) {
        Calendar fechaRetiro = this.getFechaRetiro();

        Calendar fechaVencimiento = new GregorianCalendar(fechaRetiro.get(Calendar.YEAR),
                fechaRetiro.get(Calendar.MONTH), fechaRetiro.get(Calendar.DAY_OF_MONTH));

        fechaVencimiento.add(Calendar.DAY_OF_YEAR, this.getSocio().getDiasPrestamo());

        return p_fecha.after(fechaVencimiento);
    }

    private String formatFecha(Calendar fecha) {
        if (fecha == null) {
            return "----/--/--";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(fecha.getTime());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Retiro: ").append(this.formatFecha(this.getFechaRetiro()));
        sb.append(" - Devolución: ").append(this.formatFecha(this.getFechaDevolucion()));
        sb.append("\nLibro: ").append(this.getLibro().getTitulo());
        sb.append("\nSocio: ").append(this.getSocio().getNombre());
        return sb.toString();
    }
}