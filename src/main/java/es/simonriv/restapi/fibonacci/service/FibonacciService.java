package es.simonriv.restapi.fibonacci.service;

import es.simonriv.restapi.fibonacci.model.FibonacciSeries;
import es.simonriv.restapi.fibonacci.repository.FibonacciSeriesRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FibonacciService {

    @Autowired
    private FibonacciSeriesRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public List<Integer> generateFibonacciSeries(String timeString) {
        // Verifica que la cadena de entrada no esté vacía
        if (timeString == null || timeString.trim().isEmpty()) {
            throw new IllegalArgumentException("La cadena de tiempo no puede estar vacía.");
        }

        // Elimina comillas simples y dobles de la cadena de entrada
        timeString = timeString.replace("\"", "").replace("'", "");

        // Separa la cadena "HH:MM:SS" en partes
        String[] timeParts = timeString.split(":");

        // Verifica que haya exactamente 3 partes
        if (timeParts.length != 3) {
            throw new IllegalArgumentException("La cadena de tiempo debe estar en el formato 'HH:MM:SS'.");
        }

        int seed1, seed2, length;

        try {
            // Intenta convertir cada parte en un número entero
            String minutesString = timeParts[1].trim(); // Minutos
            String secondsString = timeParts[2].trim(); // Segundos (cantidad de números a generar)

            // Se asegura de que minutosString tenga al menos dos dígitos
            if (minutesString.length() < 2) {
                throw new IllegalArgumentException("Los minutos deben tener al menos dos dígitos.");
            }

            // Convertir el tiempo en los dígitos de las semillas y la longitud
            seed1 = Character.getNumericValue(minutesString.charAt(0));
            seed2 = Character.getNumericValue(minutesString.charAt(1));
            length = Integer.parseInt(secondsString); // La longitud de la serie será igual a los segundos
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cada parte de la cadena de tiempo debe ser un número entero válido.", e);
        }

        // Valida que length no sea 0 o negativo para evitar errores
        if (length <= 0) {
            throw new IllegalArgumentException("La longitud de la serie debe ser un número positivo.");
        }

        // Lógica para generar la serie de Fibonacci
        List<Integer> fibonacciSeries = new ArrayList<>();
        fibonacciSeries.add(seed1);
        fibonacciSeries.add(seed2);

        // Genera la serie de Fibonacci hacia adelante
        for (int i = 2; i < length + 2; i++) {
            int nextNumber = fibonacciSeries.get(i - 1) + fibonacciSeries.get(i - 2);
            fibonacciSeries.add(nextNumber);
        }

        // Recorta la serie para incluir solo `length` números adicionales, sin contar las semillas
        List<Integer> resultSeries = new ArrayList<>(fibonacciSeries);
        resultSeries = resultSeries.subList(0, length + 2);

        // Ordena la serie en orden descendente
        Collections.reverse(resultSeries);

        // Convertir la lista de enteros a una cadena separada por comas
        String seriesAsString = resultSeries.stream().map(String::valueOf).collect(Collectors.joining(","));

        // Guardar en la base de datos
        FibonacciSeries fibonacciSeriesEntity = new FibonacciSeries();
        fibonacciSeriesEntity.setExecutionTime(timeString);
        fibonacciSeriesEntity.setSeries(seriesAsString);
        repository.save(fibonacciSeriesEntity);

        return resultSeries;
    }

    public void sendEmail(String subject, String content) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("didier.correa@proteccion.com.co", "correalondon@gmail.com");
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public List<FibonacciSeries> getAllSeries() {
        return repository.findAll();
    }
}
