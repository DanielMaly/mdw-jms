package net.danielmaly.mdw.ws.homework07;

public class AirlineServiceResponse {
    private String message;
    private int bookingId;

    public AirlineServiceResponse() {

    }

    public AirlineServiceResponse(String message, int bookingId) {
        this.message = message;
        this.bookingId = bookingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
