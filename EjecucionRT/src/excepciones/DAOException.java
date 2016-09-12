package excepciones;



/**
 * A customized Exception class that redirects how the ClassNotFoundException,
 * SQLException and Exception objects are handled in the application.
 *
 */
@SuppressWarnings("serial")
public class DAOException extends RuntimeException {

	/**
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

}
