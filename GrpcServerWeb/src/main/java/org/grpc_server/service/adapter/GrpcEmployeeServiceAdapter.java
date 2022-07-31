package org.grpc_server.service.adapter;

import org.grpc_server.proto.EmployeeProfile;
import org.grpc_server.repository.EmployeeRepository;
import org.grpc_server.service.AbstractEmployeeService;
import org.grpc_server.service.converter.GrpcEmployeeConverter;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Kastalski
 */
@Service
public class GrpcEmployeeServiceAdapter extends AbstractEmployeeService<EmployeeProfile> {

	public GrpcEmployeeServiceAdapter(final EmployeeRepository employeeRepository, final GrpcEmployeeConverter converter) {
		super(employeeRepository, converter);
	}

}
