package net.danielmaly.mdw.ws.homework07;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class FlightBookingsListResponse {

    private String message;

    @XmlElementWrapper
    @XmlElement(name="booking")
    private List<FlightBooking> bookings = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FlightBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<FlightBooking> bookings) {
        this.bookings = bookings;
    }
}
