import java.util.ArrayList;
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
  Libro p_libro){
      boolean prestar=false;
      if(p_socio.puedePedir(){
          Prestamo nuevoPrestamo = new Prestamo(p_fechaRetiro,p_socio, p_libro);
          p_libro.agregarPrestamo(nuevoPrestamo);
          p_socio.agregarPrestamo(nuevoPrestamo);
          prestar= true
      }
      return prestar;
      }

    public void devolverLibro(Libro p_libro) {
        Calendar hoy = Calendar.getInstance();
        p_libro.getPrestamo().registrarFechaDevolucion(hoy);
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
                if (unPrestamo.estaVencido(fechaHoy)) {
                    vencidos.add(unPrestamo);
                }
            }
        }
        return vencidos;
    }

    public ArrayList<Docente> docentesResponsables() {
        ArrayList<Docente> docentesResponsables = new ArrayList<Docente>();
        for (Socio unSocio : this.getSocios()) {
            if (unSocio.soyDeLaClase().equalsIgnoreCase("Docente") && unSocio.esResponsable()) {
                docentesResponsables.add((Docente) unSocio);
            }
        }
        return docentesResponsables;
    }

    public String quienTieneElLibro(Libro p_libro) {
        if (p_libro.prestado()) {
            Socio socioConElLibro = p_libro.ultimoPrestamo().getSocio();
            return ("El libro " + p_libro.getTitulo() + " lo tiene el socio " + socioConElLibro.getNombre());
        } else {
            throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
        }
    }

    public String listaDeSocios() {
        StringBuilder lista = new StringBuilder();
        int contador = 1;
        for (Socio unSocio : this.getSocios()) {
            lista.append(contador)
                    .append(") D.N.I.: ")
                    .append(unSocio.getDNI())
                    .append(" || ")
                    .append(unSocio.getNombre())
                    .append(" (")
                    .append(unSocio.soyDeLaClase())
                    .append(") || Libros Prestados: ")
                    .append(unSocio.getPrestamos().size())
                    .append("\n"); // Salto de línea entre socios

            contador++;
        }
        return lista.toString();
    }

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