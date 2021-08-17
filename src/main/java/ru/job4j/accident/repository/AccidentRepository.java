package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.Accident;

import java.util.Optional;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    @EntityGraph(attributePaths = {"type", "rules"})
    @Query("SELECT a FROM Accident a ORDER BY a.id")
    Iterable<Accident> findAll();

    @EntityGraph(attributePaths = {"type", "rules"})
    Optional<Accident> findById(int id);
}
