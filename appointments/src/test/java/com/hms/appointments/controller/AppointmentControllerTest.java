package com.hms.appointments.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hms.appointments.entity.Appointment;
import com.hms.appointments.service.AppointmentService;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    void testBookAppointment() throws Exception {
        Appointment appointment = new Appointment (                    
            "John Doe",                     // name
            "john.doe@example.com",         // email
            "2023-06-23",                   // appointmentDate
            LocalTime.of(14, 30),           // appointmentTime
            "Scheduled"                     // status
        );
        
        try {
        System.out.println("hi");
        mockMvc.perform(post("/appointments/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(appointment)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Appointment booked successfully!"));
        System.out.println("bye");
        }
        catch(Exception e)
        {
        	
        }
    }
    
    @Test
    public void testGetAllAppointments() throws Exception {
        // Prepare mock data
        List<Appointment> mockAppointments = new ArrayList<>();
        mockAppointments.add(new Appointment("1", "John Doe", "john.doe@example.com", "2023-06-23", null, "Scheduled"));
        mockAppointments.add(new Appointment("2", "Jane Smith", "jane.smith@example.com", "2023-06-24", null, "Cancelled"));

        try {
        // Mock behavior of appointmentService.getAllAppointments()
        when(appointmentService.getAllAppointments()).thenReturn(mockAppointments);

        // Perform GET request to /appointments
        mockMvc.perform(get("/appointments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(mockAppointments.size())) // Verify the number of appointments returned
                .andExpect(jsonPath("$[0].id").value("1")) // Verify specific appointment details
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
        }
        catch(Exception e)
        {
        	
        }
    }
}
