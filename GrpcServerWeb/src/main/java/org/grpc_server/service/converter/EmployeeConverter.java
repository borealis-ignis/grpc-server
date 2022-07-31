package org.grpc_server.service.converter;

/**
 * @author Sergey Kastalski
 */
public interface EmployeeConverter<T, D> {

	D convertDto(T dto);

	T convertDbo(D dbo);

}
