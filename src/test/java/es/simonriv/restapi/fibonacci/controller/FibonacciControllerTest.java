package es.simonriv.restapi.fibonacci.controller;

import es.simonriv.restapi.fibonacci.model.FibonacciSeries;
import es.simonriv.restapi.fibonacci.service.FibonacciService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FibonacciController.class)
public class FibonacciControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FibonacciService fibonacciService;

    @InjectMocks
    private FibonacciController fibonacciController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fibonacciController).build();
    }

    @Test
    public void testGenerateFibonacci() throws Exception {
        String time = "10:05:10";
        String email = "test@example.com";
        List<Integer> series = List.of(233, 144, 89, 55, 34, 21, 13, 8, 5, 3, 2, 1);

        when(fibonacciService.generateFibonacciSeries(time)).thenReturn(series);

        mockMvc.perform(post("/api/fibonacci/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + time + "\"")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(233))
                .andExpect(jsonPath("$[1]").value(144));
    }

    @Test
    public void testGetAllSeries() throws Exception {
        List<FibonacciSeries> allSeries = Collections.singletonList(new FibonacciSeries());

        when(fibonacciService.getAllSeries()).thenReturn(allSeries);

        mockMvc.perform(get("/api/fibonacci/all"))
                .andExpect(status().isOk());
    }
}
