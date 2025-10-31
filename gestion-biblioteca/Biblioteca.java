import java.util.ArrayList;

public class Biblioteca {
    private String nombre;
    private ArrayList<Libro> libros;
    private ArrayList<Socio> socios;

    public Biblioteca(String p_nombre) {
        this.setNombre(p_nombre);
        this.setLibros(new ArrayList<Libro>());
        this.setSocios(new ArrayList<Socio>());
    }

    public Biblioteca(String p_nombre, ArrayList<Libro> p_libros, ArrayList<Socio> p_socios) {
        this.setNombre(p_nombre);
        this.setLibros(p_libros);
        this.setSocios(p_socios);
    }

    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    private void setLibros(ArrayList<Libro> p_libros) {
        this.libros = p_libros;
    }

    private void setSocios(ArrayList<Socio> p_socios) {
        this.socios = p_socios;
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

    public void agregarLibro(Libro p_libro) {
        this.getLibros().add(p_libro);
    }

    public void quitarLibro(Libro p_libro) {
        this.getLibros().remove(p_libro);
    }

    public void agregarSocio(Socio p_socio) {
        this.getSocios().add(p_socio);
    }

    public void quitarSocio(Socio p_socio) {
        this.getSocios().remove(p_socio);
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

    /*
     * public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro
     * p_libro) {
     * return true;
     * }
     */

    /*
     * public void devolverLibro(Libro p_libro) {
     * return;
     * }
     * 
     * public int cantidadDeSociosPorTipo(String p_objeto) {
     * return 0;
     * }
     */

    public ArrayList<Prestamo> prestamosVencidos() {
        return new ArrayList<Prestamo>();
    }

    public ArrayList<Docente> docentesResponsables() {
        return new ArrayList<Docente>();
    }

    /*
     * public String quienTieneElLibro(Libro p_libro) {
     * return "";
     * }
     */
    /*
     * public String listaDeSocios() {
     * return "";
     * }
     */

    /*
     * public Socio buscarSocio(int p_dni) {
     * Socio socioBuscado = null;
     * 
     * for (Socio socio : this.getSocios()) {
     * if (socio.getDni() == p_dni) {
     * socioBuscado = socio;
     * break;
     * }
     * }
     * return socioBuscado;
     * 
     * }
     */

    public String listaDeTitulos() {
        String listaDeTitulos = "";

        for (Libro libro : this.getLibros()) {
            listaDeTitulos += libro.getTitulo() + "\n";
        }

        return listaDeTitulos;
    }

    public String listaDeLibros() {
        String listaDeLibros = "";

        for (int i = 0; i < this.getLibros().size(); i++) {
            listaDeLibros += (i + 1) + ") " + this.getLibros().get(i).toString() + " || Prestado: "
                    + (this.getLibros().get(i).prestado() ? "(Si)" : "(No)") + "\n";
        }

        return listaDeLibros;
    }

    /*
     * public String listaDeDocentesResponsables() {
     * return "";
     * }
     */

}