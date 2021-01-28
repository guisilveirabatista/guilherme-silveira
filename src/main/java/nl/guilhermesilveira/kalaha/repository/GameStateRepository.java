package nl.guilhermesilveira.kalaha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.guilhermesilveira.kalaha.model.Game;

@Repository
public interface GameStateRepository extends JpaRepository<Game, Long> {

}