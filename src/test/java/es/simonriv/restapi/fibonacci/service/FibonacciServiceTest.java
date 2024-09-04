package es.simonriv.restapi.fibonacci.service;

import es.simonriv.restapi.fibonacci.repository.FibonacciSeriesRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FibonacciServiceTest {

    @Mock
    private FibonacciSeriesRepository repository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private FibonacciService fibonacciService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateFibonacciSeries() {
        String timeString = "10:05:10"; // Ejemplo de tiempo
        List<Integer> result = fibonacciService.generateFibonacciSeries(timeString);

        assertNotNull(result);
        assertEquals(12, result.size()); // La serie debería tener 12 elementos
        assertEquals(List.of(233, 144, 89, 55, 34, 21, 13, 8, 5, 3, 2, 1), result); // Resultado esperado
    }

    @Test
    public void testSendEmail() throws MessagingException {
        // Configura Mockito para no hacer nada cuando se llame al método send()
        doNothing().when(mailSender).send(any(MimeMessage.class));

        String subject = "Test Subject";
        String content = "Test Content";

        // Llama al método que se está probando
        fibonacciService.sendEmail(subject, content);

        // Crea un capturador para MimeMessage
        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);

        // Verifica que el método send() fue llamado exactamente una vez con un MimeMessage
        verify(mailSender, times(1)).send(mimeMessageCaptor.capture());

        // Obtén el MimeMessage capturado
        MimeMessage mimeMessage = mimeMessageCaptor.getValue();

        // Maneja la IOException que puede lanzar getContent()
        try {
            assertNotNull(mimeMessage);
            assertEquals(subject, mimeMessage.getSubject());
            assertEquals(content, mimeMessage.getContent());
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving content from MimeMessage", e);
        }
    }
}
