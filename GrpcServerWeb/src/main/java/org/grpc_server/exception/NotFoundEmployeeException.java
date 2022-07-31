package org.grpc_server.exception;

/**
 * @author Sergey Kastalski
 */
public class NotFoundEmployeeException extends RuntimeException {

	public NotFoundEmployeeException(final long id) {
		super("Employee is not found by id=" + id);
	}

}
