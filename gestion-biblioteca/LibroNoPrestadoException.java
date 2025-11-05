/**
 * Excepción personalizada utilizada para señalar que se intentó realizar
 * una operación (como una devolución o consulta) sobre un libro
 * que no se encuentra actualmente en estado de préstamo (está en la
 * biblioteca).
 *
 * @author Jensen, Lucas
 * @author Romero Lencinas, Santiago
 * @author Larrazet, Tomás
 * @author Lencinas, Mauricio
 * @author Kruchowski, Juan Ignacio
 * @version 1.0 (Inicial)
 */
public class LibroNoPrestadoException extends Exception {

    /**
     * Constructor que acepta un mensaje detallado sobre el error.
     *
     * @param message El mensaje de error descriptivo.
     */
    public LibroNoPrestadoException(String message) {
        super(message);
    }
}