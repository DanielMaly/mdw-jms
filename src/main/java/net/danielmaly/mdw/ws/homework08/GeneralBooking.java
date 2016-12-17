package net.danielmaly.mdw.ws.homework08;

import net.danielmaly.mdw.ws.homework07.Airline;
import net.danielmaly.mdw.ws.homework07.FlightBookingCreateData;
import net.danielmaly.mdw.ws.homework07.FlightBookingResponse;
import net.danielmaly.mdw.ws.homework07.IAirline;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Date;

@WebService
public class GeneralBooking {

    public BookingCreateResponse makeBooking(@WebParam(name="type") @XmlElement(required = true) String type,
                            @WebParam(name="name") @XmlElement(required = true) String name,
                            @WebParam(name="origin") String origin,
                            @WebParam(name="destination") @XmlElement(required = true) String destination,
                            @WebParam(name="departureDate") Date departureDate) {

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

        FlightBookingCreateData createData = new FlightBookingCreateData();
        createData.setDepartureCode(origin);
        createData.setArrivalCode(destination);
        createData.setPassengerName(name);
        createData.setDepartureDate(departureDate);

        try {
            URL url = new URL("http://localhost:7001/jms-ws-1.0/AirlineService?wsdl");

            QName qname = new QName("http://homework07.ws.mdw.danielmaly.net/", "AirlineService");
            Service service = Service.create(url, qname);
            IAirline airline = service.getPort(IAirline.class);

            FlightBookingResponse airlineResponse = airline.addBooking(createData);

            if(airlineResponse.getMessage().equals("OK")) {
                response.setFlightBooking(airlineResponse.getFlightBooking());
            }
            else {
                response.setMessage(airlineResponse.getMessage());
            }

            return response;
        }
        catch (Exception ex) {
            response.setMessage(ex.getMessage());
            return response;
        }
    }

    private BookingCreateResponse createTripBooking(String name, String destination) {
        BookingCreateResponse response = new BookingCreateResponse();

        return response;
    }
}
