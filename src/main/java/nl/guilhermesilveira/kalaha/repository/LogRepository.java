package nl.guilhermesilveira.kalaha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.guilhermesilveira.kalaha.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {


}
