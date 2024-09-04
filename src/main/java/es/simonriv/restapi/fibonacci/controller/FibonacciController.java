package es.simonriv.restapi.fibonacci.controller;

import es.simonriv.restapi.fibonacci.model.FibonacciSeries;
import es.simonriv.restapi.fibonacci.service.FibonacciService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fibonacci")
public class FibonacciController {
    @Autowired
    private FibonacciService fibonacciService;

    @PostMapping("/generate")
    @Operation(summary = "Genera una serie de Fibonacci basada en la cadena de tiempo proporcionada",
            description = "Genera una serie de Fibonacci usando la cadena de tiempo y envía un correo electrónico con los detalles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie de Fibonacci generada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta debido a una cadena de tiempo no válida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al enviar el correo electrónico")
    })
    public ResponseEntity<List<Integer>> generateFibonacci(@RequestBody String time, @RequestParam String email) throws MessagingException {
        List<Integer> series = fibonacciService.generateFibonacciSeries(time);

        String subject = "Prueba Técnica – " + email;
        String content = "Hora de ejecución: " + time + "\nSerie: " + series.toString();
        fibonacciService.sendEmail(subject, content);

        return ResponseEntity.ok(series);
    }

    @GetMapping("/all")
    @Operation(summary = "Obtiene todas las series de Fibonacci almacenadas",
            description = "Devuelve una lista de todas las series de Fibonacci almacenadas en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Lista de series de Fibonacci recuperada exitosamente")
    public ResponseEntity<List<FibonacciSeries>> getAllSeries() {
        return ResponseEntity.ok(fibonacciService.getAllSeries());
    }

}
