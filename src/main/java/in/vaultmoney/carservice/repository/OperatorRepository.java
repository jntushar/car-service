package in.vaultmoney.carservice.repository;

import in.vaultmoney.carservice.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer> {
}
