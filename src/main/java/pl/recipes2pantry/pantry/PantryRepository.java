package pl.recipes2pantry.pantry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PantryRepository extends JpaRepository<Pantry, Long> {
    @Query("SELECT p FROM Pantry p LEFT JOIN FETCH p.stockSet WHERE p.id=:id")
    Optional<Pantry> findByIdWithStocks(@Param("id") Long id);
}
