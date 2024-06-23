package com.hms.appointments.service;



import com.hms.appointments.dto.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    private MockedStatic<Stripe> mockedStripe;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(paymentService, "stripeApiKey", "test_api_key");
        ReflectionTestUtils.setField(paymentService, "successUrl", "https://example.com/success");
        ReflectionTestUtils.setField(paymentService, "cancelUrl", "https://example.com/cancel");

      
    }

    @Test
    public void testCreateCheckoutSession_Success() throws StripeException {
        // Mock payment request
        PaymentRequest paymentRequest = new PaymentRequest(5000, "Test Payment");
        paymentRequest.setAppointmentId("12345");

        // Mock Stripe session creation
        Session mockSession = Mockito.mock(Session.class);
        when(mockSession.getId()).thenReturn("sess_123");
        when(mockSession.getUrl()).thenReturn("https://example.com/checkout/sess_123");

        try (MockedStatic<Session> mockedSession = mockStatic(Session.class)) {
            mockedSession.when(() -> Session.create(any(SessionCreateParams.class))).thenReturn(mockSession);

            // Call service method
            Session createdSession = paymentService.createCheckoutSession(paymentRequest);

            // Assertions
            assertNotNull(createdSession);
            assertEquals("sess_123", createdSession.getId());
            assertEquals("https://example.com/checkout/sess_123", createdSession.getUrl());
        }
    }

  
    @Test
    public void testGetCheckoutUrl() {
        // Mock Stripe session
        Session mockSession = Mockito.mock(Session.class);
        when(mockSession.getId()).thenReturn("sess_123");
        when(mockSession.getUrl()).thenReturn("https://example.com/checkout/sess_123");

        // Call service method
        String checkoutUrl = paymentService.getCheckoutUrl(mockSession);

        // Assertions
        assertNotNull(checkoutUrl);
        assertEquals("https://example.com/checkout/sess_123", checkoutUrl);
    }
}

