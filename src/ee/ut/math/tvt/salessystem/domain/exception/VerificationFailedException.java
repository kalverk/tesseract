package ee.ut.math.tvt.salessystem.domain.exception;

/**
 * Thrown when domain controller's verification fails.
 */
public class VerificationFailedException extends Exception {

	private static final long serialVersionUID = 1L;


	public VerificationFailedException() {
		super();
	}

	public VerificationFailedException(final String message) {
		super(message);
	}
}
