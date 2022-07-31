package org.grpc_server.repository;

import org.grpc_server.model.dbo.EmployeeDbo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Kastalski
 */
@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeDbo, Long> {

}
