package org.grpc_server.service;

import org.grpc_server.exception.NotFoundEmployeeException;
import org.grpc_server.model.dbo.EmployeeDbo;
import org.grpc_server.repository.EmployeeRepository;
import org.grpc_server.service.converter.EmployeeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kastalski
 */
public class AbstractEmployeeService<E> implements EmployeeService<E> {

	private final EmployeeRepository employeeRepository;

	private final EmployeeConverter<E, EmployeeDbo> converter;

	public AbstractEmployeeService(final EmployeeRepository employeeRepository, final EmployeeConverter<E, EmployeeDbo> converter) {
		this.employeeRepository = employeeRepository;
		this.converter = converter;
	}

	@Override
	public E getEmployee(final long employeeId) {
		final EmployeeDbo dbo = employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundEmployeeException(employeeId));
		return converter.convertDbo(dbo);
	}

	@Override
	public List<E> getEmployees() {
		final Iterable<EmployeeDbo> dbos = employeeRepository.findAll();
		final List<E> result = new ArrayList<>();
		dbos.forEach(dbo -> result.add(converter.convertDbo(dbo)));
		return result;
	}

	@Override
	public E createEmployee(final E employee) {
		final EmployeeDbo dbo = employeeRepository.save(converter.convertDto(employee));
		return converter.convertDbo(dbo);
	}

	@Override
	public boolean deleteEmployee(final long employeeId) {
		if (!employeeRepository.existsById(employeeId)) {
			return false;
		}
		employeeRepository.deleteById(employeeId);
		return true;
	}

}
