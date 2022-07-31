package org.grpc_server.service;

import java.util.List;

/**
 * @author Sergey Kastalski
 */
public interface EmployeeService<E> {

	E getEmployee(long employeeId);

	List<E> getEmployees();

	E createEmployee(E employee);

	boolean deleteEmployee(long employeeId);

}
