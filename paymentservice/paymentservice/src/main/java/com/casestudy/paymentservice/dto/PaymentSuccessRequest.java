package com.casestudy.paymentservice.dto;

public class PaymentSuccessRequest {
    private String bookingId;
    private String userEmail;
    // getters and setters


    public String getBookingId() {
        return bookingId;
    }

    public PaymentSuccessRequest(String bookingId, String userEmail) {
        this.bookingId = bookingId;
        this.userEmail = userEmail;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
