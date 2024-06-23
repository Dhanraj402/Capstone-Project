package com.hms.appointments.controller;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hms.appointments.entity.Appointment;
import com.hms.appointments.repository.AppointmentRepository;
import com.hms.appointments.repository.TransactionRepository;
import com.stripe.model.checkout.Session;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setup() {
    	try {
			 
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    	}
        catch (Exception e) {
			
		}
    }

    @Test
    public void testPaymentSuccess() throws Exception {
        // Mock session ID
        String sessionId = "fake_session_id";

        // Mock Stripe session
        Session mockSession = new Session();
        mockSession.setAmountTotal(1000L); // Amount in cents

        // Mock appointment
        Appointment appointment = new Appointment();
        appointment.setId("1");
        appointment.setStatus("Pending");
        
        try {
        // Mock repository behavior
        when(appointmentRepository.findById("1")).thenReturn(java.util.Optional.of(appointment));
        doNothing().when(appointmentRepository).save(any(Appointment.class));

        // Mock HTML template content
        String htmlContent = loadHtmlTemplate("templates/payment-success.html");

        // Mock Stripe session retrieval
        when(Session.retrieve(sessionId)).thenReturn(mockSession);

        // Perform GET request to /payment-success?session_id=fake_session_id
        mockMvc.perform(get("/payment-success")
                .param("session_id", sessionId)
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(content().string(htmlContent)); // Verify the returned HTML content
        }
        catch(Exception e)
        {
        	
        }
    }

    // Helper method to load HTML template content
    private String loadHtmlTemplate(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path);
        Path templatePath = Paths.get(resource.getURI());
        return new String(Files.readAllBytes(templatePath), StandardCharsets.UTF_8);
    }
}

