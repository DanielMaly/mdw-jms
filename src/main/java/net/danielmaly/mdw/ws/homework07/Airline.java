package net.danielmaly.mdw.ws.homework07;


import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import java.time.Instant;
import java.util.*;

@WebService(endpointInterface = "net.danielmaly.mdw.ws.homework07.IAirline")
public class Airline implements IAirline {

    private HashMap<Integer, FlightBooking> bookings;
    private List<Flight> flights;
    private int idSeq = 1;

    public Airline() {
        bookings = new HashMap<>();
        flights = new ArrayList<>();
        initFlights();
    }

    private void initFlights() {
        Flight flight1 = new Flight();
        flight1.setDepartureCode("PRG");
        flight1.setArrivalCode("CDG");
        flight1.setDepartureDate(new GregorianCalendar(2016, 11, 27, 18, 15).getTime());
        flight1.setArrivalDate(new GregorianCalendar(2016, 11, 27, 21, 0).getTime());
        flight1.setId(1);
        flights.add(flight1);
    }

    public FlightBookingResponse addBooking(@WebParam(name="booking") @XmlElement(required = true) FlightBookingCreateData data) {
        // Find flight
        Optional<Flight> flight = flights.stream().filter(f ->
                f.getDepartureDate().equals(data.getDepartureDate()) &&
                        f.getDepartureCode().equals(data.getDepartureCode()) &&
                        f.getArrivalCode().equals(data.getArrivalCode()))
                .findFirst();

        if(flight.isPresent()) {
            FlightBooking booking = new FlightBooking();
            booking.setId(idSeq++);
            booking.setPassengerName(data.getPassengerName());
            booking.setFlight(flight.get());
            bookings.put(booking.getId(), booking);
            FlightBookingResponse response = new FlightBookingResponse();
            response.setMessage("OK");
            response.setFlightBooking(booking);
            return response;
        }
        else {
            FlightBookingResponse response = new FlightBookingResponse();
            response.setMessage("No flight available");
            return response;
        }


    }

    public AirlineServiceResponse removeBooking(@WebParam(name="bookingId") @XmlElement(required = true) Integer bookingId) {
        if(bookings.containsKey(bookingId)) {
            bookings.remove(bookingId);
            AirlineServiceResponse response = new AirlineServiceResponse();
            response.setMessage("OK");
            return response;
        }
        else {
            AirlineServiceResponse response = new AirlineServiceResponse();
            response.setMessage("This booking doesn't exist");
            return response;
        }
    }

    public AirlineServiceResponse updateBooking(@WebParam(name="updateFields") @XmlElement(required = true) FlightBookingUpdateData updateData) {

        if(!bookings.containsKey(updateData.getId())) {
            AirlineServiceResponse response = new AirlineServiceResponse();
            response.setMessage("This booking doesn't exist");
            return response;
        }

        FlightBooking booking = bookings.get(updateData.getId());


        if(updateData.getPassengerName() != null) {
            booking.setPassengerName(updateData.getPassengerName());
        }

        if(updateData.getFlightId() != null) {
            Optional<Flight> flight = flights.stream().filter(f -> f.getId() == updateData.getFlightId()).findFirst();
            if(flight.isPresent()) {
                booking.setFlight(flight.get());
            }
            else {
                AirlineServiceResponse response = new AirlineServiceResponse();
                response.setMessage("Requested flight does not exist");
                return response;
            }
        }


        AirlineServiceResponse response = new AirlineServiceResponse();
        response.setMessage("OK");
        return response;
    }

    public FlightBookingsListResponse getBookingsList() {
        FlightBookingsListResponse response = new FlightBookingsListResponse();
        response.setMessage("OK");
        response.getBookings().addAll(bookings.values());
        return response;
    }
}
