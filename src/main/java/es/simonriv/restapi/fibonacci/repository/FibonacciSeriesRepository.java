package es.simonriv.restapi.fibonacci.repository;

import es.simonriv.restapi.fibonacci.model.FibonacciSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FibonacciSeriesRepository extends JpaRepository<FibonacciSeries, Long> {
}
