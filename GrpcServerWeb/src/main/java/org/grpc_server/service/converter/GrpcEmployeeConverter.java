package org.grpc_server.service.converter;

import org.grpc_server.model.dbo.EmployeeDbo;
import org.grpc_server.proto.EmployeeProfile;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Kastalski
 */
@Component
public class GrpcEmployeeConverter implements EmployeeConverter<EmployeeProfile, EmployeeDbo> {

	@Override
	public EmployeeDbo convertDto(final EmployeeProfile dto) {
		return new EmployeeDbo(dto.getEmployeeId(), dto.getEmployeeName());
	}

	@Override
	public EmployeeProfile convertDbo(final EmployeeDbo dbo) {
		return EmployeeProfile.newBuilder().setEmployeeId(dbo.getId()).setEmployeeName(dbo.getName()).build();
	}

}
