import java.util.ArrayList;
import java.util.Calendar;

public class Biblioteca {
    private String nombre;
    private ArrayList<Libro> libros;
    private ArrayList<Socio> socios;

    public Biblioteca(String p_nombre) {
        this.setNombre(p_nombre);
    }

    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public ArrayList<Libro> getLibros() {
        return this.libros;
    }

    public ArrayList<Socio> getSocios() {
        return this.socios;
    }

    private void agregarLibro(Libro p_libro) {
        this.getLibros().add(p_libro);
    }

    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio) {
        this.agregarLibro(new Libro(p_titulo, p_edicion, p_editorial, p_anio));
    }

    /*
     * public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String
     * p_carrera) {
     * this.getSocios().add(new Estudiante(p_dniSocio, p_nombre, p_carrera));
     * }
     * 
     * public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area)
     * {
     * this.getSocios().add(new Docente(p_dniSocio, p_nombre, p_area));
     * }
     */

    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        return true;
    }

    public void devolverLibro(Libro p_libro) {
        return;
    }

    public int cantidadDeSociosPorTipo(String p_objeto) {
        return 0;
    }

    public ArrayList<Prestamo> prestamosVencidos() {
        return new ArrayList<Prestamo>();
    }

    public ArrayList<Docente> docentesResponsables() {
        return new ArrayList<Docente>();
    }

    public String quienTieneElLibro(Libro p_libro) {
        return "";
    }

    public String listaDeSocios() {
        return "";
    }

    public Socio buscarSocio(int p_dni) {
        return null;
    }

    public String listaDeTitulos() {
        return "";
    }

    public String listaDeLibros() {
        return "";
    }

    public String listaDeDocentesResponsables() {
        return "";
    }

}