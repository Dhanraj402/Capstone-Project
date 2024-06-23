package com.hms.appointments.repository;

import com.hms.appointments.entity.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void testFindByEmail() {
        // Setup test data
        Appointment appointment1 = new Appointment();
        appointment1.setId("1");
        appointment1.setName("John Doe");
        appointment1.setEmail("john.doe@example.com");
        appointment1.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment1.setAppointmentTime(LocalTime.of(10, 0));
        appointment1.setStatus("NOT PAID");

        Appointment appointment2 = new Appointment();
        appointment2.setId("2");
        appointment2.setName("Jane Smith");
        appointment2.setEmail("jane.smith@example.com");
        appointment2.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment2.setAppointmentTime(LocalTime.of(11, 0));
        appointment2.setStatus("NOT PAID");

        Appointment appointment3 = new Appointment();
        appointment3.setId("3");
        appointment3.setName("John Doe");
        appointment3.setEmail("john.doe@example.com");
        appointment3.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment3.setAppointmentTime(LocalTime.of(12, 0));
        appointment3.setStatus("NOT PAID");

        // Save test data to the repository
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        // Execute the repository method
        List<Appointment> appointmentsByEmail = appointmentRepository.findByEmail("john.doe@example.com");

        // Assertions
        assertNotNull(appointmentsByEmail);
        assertEquals(2, appointmentsByEmail.size());
        assertTrue(appointmentsByEmail.stream().anyMatch(app -> app.getId().equals("1")));
        assertTrue(appointmentsByEmail.stream().anyMatch(app -> app.getId().equals("3")));
    }
}
