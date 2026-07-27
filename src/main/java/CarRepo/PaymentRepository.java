package CarRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.PaymentTable;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTable, Integer>{

}
