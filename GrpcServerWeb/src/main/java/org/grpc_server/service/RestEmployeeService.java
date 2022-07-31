package org.grpc_server.service;

import org.grpc_server.model.dto.EmployeeDto;
import org.grpc_server.service.adapter.DtoEmployeeServiceAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergey Kastalski
 */
@Service
public class RestEmployeeService implements EmployeeService<EmployeeDto> {

	private final EmployeeService<EmployeeDto> employeeAdapter;

	public RestEmployeeService(final DtoEmployeeServiceAdapter employeeAdapter) {
		this.employeeAdapter = employeeAdapter;
	}

	@Override
	public EmployeeDto getEmployee(final long employeeId) {
		return employeeAdapter.getEmployee(employeeId);
	}

	@Override
	public List<EmployeeDto> getEmployees() {
		return employeeAdapter.getEmployees();
	}

	@Override
	public EmployeeDto createEmployee(final EmployeeDto employee) {
		return employeeAdapter.createEmployee(employee);
	}

	@Override
	public boolean deleteEmployee(final long employeeId) {
		return employeeAdapter.deleteEmployee(employeeId);
	}

}
