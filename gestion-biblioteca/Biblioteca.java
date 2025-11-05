import java.util.ArrayList;
import java.util.HashSet;
import java.util.Calendar;

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
        Libro nuevoLibro = new Libro(p_titulo, p_edicion, p_editorial, p_anio);
        this.agregarLibro(nuevoLibro);
    }

    public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String p_carrera) {
        Estudiante nuevoEstudiante = new Estudiante(p_dniSocio, p_nombre, p_carrera);
        this.agregarSocio(nuevoEstudiante);
    }

    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area) {
        Docente nuevoDocente = new Docente(p_dniSocio, p_nombre, p_area);
        this.agregarSocio(nuevoDocente);
    }

    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio,
            Libro p_libro) {
        boolean prestar = false;
        if (p_socio.puedePedir() && !p_libro.prestado()) {
            Prestamo nuevoPrestamo = new Prestamo(p_fechaRetiro, p_socio, p_libro);
            p_libro.agregarPrestamo(nuevoPrestamo);
            p_socio.agregarPrestamo(nuevoPrestamo);
            prestar = true;
        }
        return prestar;

    }

    public void devolverLibro(Libro p_libro) throws LibroNoPrestadoException {
        if (!p_libro.prestado()) {
            throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
        }

        Calendar fechaHoy = Calendar.getInstance();
        Prestamo ultimoPrestamo = p_libro.ultimoPrestamo();
        if (ultimoPrestamo != null) {
            ultimoPrestamo.registrarFechaDevolucion(fechaHoy);
        }
    }

    public int cantidadDeSociosPorTipo(String p_objeto) {
        int cantSocios = 0;
        for (Socio unSocio : this.getSocios()) {
            if (unSocio.soyDeLaClase().equalsIgnoreCase(p_objeto)) {
                cantSocios++;
            }
        }

        return cantSocios;
    }

    public ArrayList<Prestamo> prestamosVencidos() {
        ArrayList<Prestamo> vencidos = new ArrayList<Prestamo>();
        Calendar fechaHoy = Calendar.getInstance();
        for (Libro unLibro : this.getLibros()) {
            for (Prestamo unPrestamo : unLibro.getPrestamos()) {
                if (unPrestamo.vencido(fechaHoy) && unPrestamo.getFechaDevolucion() == null) {
                    vencidos.add(unPrestamo);
                }
            }
        }
        return vencidos;
    }

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

        sb.append("**************************************\n");
        sb.append("Cantidad de Socios del tipo Estudiante: ")
                .append(cantidadDeSociosPorTipo("Estudiante")).append("\n");
        sb.append("Cantidad de Socios del tipo Docente: ")
                .append(cantidadDeSociosPorTipo("Docente")).append("\n");
        sb.append("**************************************");

        return sb.toString();
    }

    public Socio buscarSocio(int p_dni) {
        for (Socio unSocio : this.getSocios()) {
            if (unSocio.getDniSocio() == p_dni) {
                return unSocio;
            }
        }
        return null;
    }

    public String listaDeTitulos() {
        if (this.getLibros().isEmpty()) {
            return "No hay títulos registrados.";
        }

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

    public String listaDeLibros() {
        if (this.getLibros().isEmpty()) {
            return "No hay libros registrados.";
        }

        StringBuilder listaDeLibros = new StringBuilder();
        listaDeLibros.append("Lista de Libros:\n");

        for (int i = 0; i < this.getLibros().size(); i++) {
            listaDeLibros.append(i + 1)
                    .append(") ")
                    .append(this.getLibros().get(i).toString())
                    .append(" || Prestado: ")
                    .append(this.getLibros().get(i).prestado() ? "(Si)" : "(No)")
                    .append("\n");
        }

        return listaDeLibros.toString();
    }

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