package org.grpc_server.service;

import org.grpc_server.proto.EmployeeProfile;
import org.grpc_server.service.adapter.GrpcEmployeeServiceAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergey Kastalski
 */
@Service
public class GrpcEmployeeService implements EmployeeService<EmployeeProfile> {

	private final EmployeeService<EmployeeProfile> employeeAdapter;

	public GrpcEmployeeService(final GrpcEmployeeServiceAdapter employeeAdapter) {
		this.employeeAdapter = employeeAdapter;
	}

	@Override
	public EmployeeProfile getEmployee(final long employeeId) {
		return employeeAdapter.getEmployee(employeeId);
	}

	@Override
	public List<EmployeeProfile> getEmployees() {
		return employeeAdapter.getEmployees();
	}

	@Override
	public EmployeeProfile createEmployee(final EmployeeProfile employee) {
		return employeeAdapter.createEmployee(employee);
	}

	@Override
	public boolean deleteEmployee(final long employeeId) {
		return employeeAdapter.deleteEmployee(employeeId);
	}

}
