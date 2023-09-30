package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation;
            reservation = reservationRepository2.findById(reservationId).get();

            if(amountSent < reservation.getSpot().getPricePerHour() * reservation.getNumberOfHours()){
                throw new Exception("Insufficient Amount");
            }

            PaymentMode paymentMode = null;

            if(mode.toUpperCase().equals(PaymentMode.CASH.toString())){
                paymentMode = paymentMode.CASH;
            }else if(mode.toUpperCase().equals(PaymentMode.CARD.toString())){
                paymentMode = paymentMode.CARD;
            } else if (mode.toUpperCase().equals(PaymentMode.UPI.toString())) {
                paymentMode = paymentMode.UPI;
            }else{
                throw new Exception("Payment mode not detected");
            }

            Payment payment = new Payment();
            payment.setPaymentMode(paymentMode);
            payment.setPaymentCompleted(true);
            payment.setReservation(reservation);

            reservationRepository2.save(reservation);

            return payment;
    }
}
