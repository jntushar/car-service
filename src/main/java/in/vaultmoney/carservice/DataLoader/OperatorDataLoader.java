package in.vaultmoney.carservice.DataLoader;

import in.vaultmoney.carservice.entity.Operator;
import in.vaultmoney.carservice.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class OperatorDataLoader implements CommandLineRunner {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public void run(String... args) throws Exception {
        operatorRepository.save(new Operator(1, "Clean&Clear", new HashSet<>()));
        operatorRepository.save(new Operator(2, "Super Car Wash", new HashSet<>()));
        operatorRepository.save(new Operator(3, "Bangalore Car Wash", new HashSet<>()));
    }
}
