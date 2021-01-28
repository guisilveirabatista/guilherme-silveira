package nl.guilhermesilveira.kalaha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.guilhermesilveira.kalaha.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
