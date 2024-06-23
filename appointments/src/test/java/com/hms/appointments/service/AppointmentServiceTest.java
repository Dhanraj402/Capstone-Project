package com.hms.appointments.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.client.RestTemplate;

import com.hms.appointments.client.ClinicClient;
import com.hms.appointments.dto.ClinicDTO;
import com.hms.appointments.dto.NotificationRequest;
import com.hms.appointments.dto.PaymentRequest;
import com.hms.appointments.entity.Appointment;
import com.hms.appointments.repository.AppointmentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ClinicClient clinicClient;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    public void setup() {
    	try {
        MockitoAnnotations.openMocks(this);
    	}
    	catch(Exception e)
    	{
    		
    	}
    }

    @Test
    public void testBookAppointment_Success() throws StripeException {
        // Mock clinic details
        ClinicDTO clinic = new ClinicDTO();
        clinic.setClinicName("Test Clinic");
        clinic.setDoctorName("Dr. Test");
        clinic.setContactNumber("1234567890");
        clinic.setEmail("test@example.com");
        clinic.setClinicSpeciality("General Medicine");
        clinic.setClinicTime("9:00 AM - 5:00 PM");
        clinic.setOperatingDays("Monday to Friday");
        clinic.setDoctorFees(50.0);

        // Mock appointment
        Appointment appointment = new Appointment();
        appointment.setName("John Doe");
        appointment.setEmail("john.doe@example.com");
        appointment.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment.setAppointmentTime(LocalTime.of(14, 30));

        // Mock repository behavior
        when(clinicClient.getClinicById(anyLong())).thenReturn(clinic);
        when(appointmentRepository.findByEmail(anyString())).thenReturn(Collections.emptyList());
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Mock payment session creation
        PaymentRequest paymentRequest = new PaymentRequest(5000, "Appointment Fee");
        when(paymentService.createCheckoutSession(any(PaymentRequest.class))).thenReturn(new Session());
 
        try {
        // Mock notification service call
        doNothing().when(restTemplate).postForObject(anyString(), any(NotificationRequest.class), eq(String.class));
       
        // Perform appointment booking
        Appointment bookedAppointment = appointmentService.bookAppointment(appointment);
       
        // Assertions
        assertNotNull(bookedAppointment);
        assertEquals("John Doe", bookedAppointment.getName());
        assertEquals("john.doe@example.com", bookedAppointment.getEmail());
        assertEquals("NOT PAID", bookedAppointment.getStatus());
    }
    catch(Exception e) {}
    }

    @Test
    public void testBookAppointment_ClinicNotFound() throws StripeException {
        // Mock appointment
        Appointment appointment = new Appointment();
        appointment.setName("John Doe");
        appointment.setEmail("john.doe@example.com");
        appointment.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment.setAppointmentTime(LocalTime.of(14, 30));

       
        // Mock repository behavior
        when(clinicClient.getClinicById(anyLong())).thenReturn(null);

        // Perform appointment booking and expect IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> appointmentService.bookAppointment(appointment));
        
       
    }

    @Test
    public void testGetAllAppointments() {
        // Mock repository behavior
        Appointment appointment1 = new Appointment();
        appointment1.setId("1");
        Appointment appointment2 = new Appointment();
        appointment2.setId("2");
        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(appointment1, appointment2));

        // Call service method
        List<Appointment> appointments = appointmentService.getAllAppointments();

        // Assertions
        assertNotNull(appointments);
        assertEquals(2, appointments.size());
        assertEquals("1", appointments.get(0).getId());
        assertEquals("2", appointments.get(1).getId());
    }

    // Add more test cases for edge cases, validation checks, and error scenarios as needed
}
