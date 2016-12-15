package net.danielmaly.mdw.ws.homework07;


import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashMap;

@WebService
public class Airline {

    private HashMap<Integer, FlightBooking> bookings;
    private int idSeq = 1;

    public Airline() {
        bookings = new HashMap<>();
    }

    public AirlineServiceResponse addBooking(@WebParam(name="booking") @XmlElement(required = true) FlightBookingCreateData data) {
        FlightBooking booking = new FlightBooking();
        booking.setId(idSeq++);

        booking.setArrivalCode(data.getArrivalCode());
        booking.setDepartureCode(data.getDepartureCode());
        booking.setArrivalDate(data.getArrivalDate());
        booking.setDepartureDate(data.getDepartureDate());
        booking.setPassengerName(data.getPassengerName());

        bookings.put(booking.getId(), booking);
        return new AirlineServiceResponse("OK", booking.getId());
    }

    public AirlineServiceResponse removeBooking(@WebParam(name="bookingId") @XmlElement(required = true) Integer bookingId) {
        bookings.remove(bookingId);
        return new AirlineServiceResponse("OK", bookingId);
    }

    public AirlineServiceResponse updateBooking(@WebParam(name="updateFields") @XmlElement(required = true) FlightBookingUpdateData updateData) {
        FlightBooking booking = bookings.get(updateData.getId());

        if(updateData.getArrivalCode() != null) {
            booking.setArrivalCode(updateData.getArrivalCode());
        }
        if(updateData.getDepartureCode() != null) {
            booking.setDepartureCode(updateData.getDepartureCode());
        }
        if(updateData.getPassengerName() != null) {
            booking.setPassengerName(updateData.getPassengerName());
        }
        if(updateData.getDepartureDate() != null) {
            booking.setDepartureDate(updateData.getDepartureDate());
        }
        if(updateData.getArrivalDate() != null) {
            booking.setArrivalDate(updateData.getArrivalDate());
        }

        return new AirlineServiceResponse("OK", booking.getId());
    }

    public FlightBookingsListResponse getBookingsList() {
        FlightBookingsListResponse response = new FlightBookingsListResponse();
        response.setMessage("OK");
        response.getBookings().addAll(bookings.values());
        return response;
    }
}
