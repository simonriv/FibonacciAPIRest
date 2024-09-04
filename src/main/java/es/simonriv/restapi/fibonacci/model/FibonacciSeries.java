package es.simonriv.restapi.fibonacci.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Schema(description = "Modelo para la serie de Fibonacci")
public class FibonacciSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la serie de Fibonacci", example = "1")
    private long id;

    @Schema(description = "Hora de ejecución de la serie de Fibonacci en formato 'HH:MM:SS'", example = "12:34:56")
    private String executionTime;

    @Schema(description = "Serie de Fibonacci generada como una cadena separada por comas", example = "0,1,1,2,3,5,8,13")
    private String series;

    // Constructor
    public FibonacciSeries() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }
}
