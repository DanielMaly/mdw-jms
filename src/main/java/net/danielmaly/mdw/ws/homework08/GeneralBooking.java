package net.danielmaly.mdw.ws.homework08;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@WebService
public class GeneralBooking {

    public BookingCreateResponse makeBooking(@WebParam @XmlElement(required = true) String type,
                            @WebParam @XmlElement(required = true) String name,
                            @WebParam String origin,
                            @WebParam @XmlElement(required = true) String destination,
                            @WebParam Date departureDate) {

        if(type.equals("flight")) {
            return createFlightBooking(name, origin, destination, departureDate);
        }
        else {
            return createTripBooking(name, destination);
        }

    }

    private BookingCreateResponse createFlightBooking(String name, String origin, String destination, Date departureDate) {
        BookingCreateResponse response = new BookingCreateResponse();
        if(origin == null || departureDate == null) {
            response.setMessage("Missing required fields");
            return response;
        }

        return response;
    }

    private BookingCreateResponse createTripBooking(String name, String destination) {
        BookingCreateResponse response = new BookingCreateResponse();

        return response;
    }
}
