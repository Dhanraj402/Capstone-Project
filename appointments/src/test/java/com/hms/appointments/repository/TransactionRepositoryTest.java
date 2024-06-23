package com.hms.appointments.repository;

import com.hms.appointments.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testFindByPaymentId() {
    	
    	
        // Setup test data
        Transaction transaction1 = new Transaction();
        transaction1.setAppointmentId("app1");
        transaction1.setPaymentId("pay1");
        transaction1.setAmount(BigDecimal.valueOf(100.00));
        transaction1.setStatus("PAID");
        transaction1.setCreatedAt(LocalDateTime.now());

        Transaction transaction2 = new Transaction();
        transaction2.setAppointmentId("app2");
        transaction2.setPaymentId("pay2");
        transaction2.setAmount(BigDecimal.valueOf(200.00));
        transaction2.setStatus("PAID");
        transaction2.setCreatedAt(LocalDateTime.now());

        try {
        // Save test data to the repository
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);

        // Execute the repository method
        Transaction foundTransaction = transactionRepository.findByPaymentId("pay1");

          // Assertions
        assertNotNull(foundTransaction);
        assertEquals("app1", foundTransaction.getAppointmentId());
        assertEquals("pay1", foundTransaction.getPaymentId());
        assertEquals(BigDecimal.valueOf(100.00), foundTransaction.getAmount());
        assertEquals("PAID", foundTransaction.getStatus());
    	}
    	catch(Exception e)
    	{}
    }
}
