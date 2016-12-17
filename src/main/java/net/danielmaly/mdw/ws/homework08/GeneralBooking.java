package net.danielmaly.mdw.ws.homework08;

import net.danielmaly.mdw.ws.homework07.FlightBookingCreateData;
import net.danielmaly.mdw.ws.homework07.FlightBookingResponse;
import net.danielmaly.mdw.ws.homework07.IAirline;
import net.danielmaly.mdw.ws.tutorial05.IAgency;
import net.danielmaly.mdw.ws.tutorial05.Trip;
import net.danielmaly.mdw.ws.tutorial05.TripBooking;
import net.danielmaly.mdw.ws.tutorial05.TripList;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

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
            URL url = new URL("http://localhost:7001/jms-1.0-SNAPSHOT/AirlineService?wsdl");

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

        try {
            URL url = new URL("http://localhost:7001/jms-1.0-SNAPSHOT/AgencyService?wsdl");

            QName qname = new QName("http://tutorial05.ws.mdw.danielmaly.net/", "AgencyService");
            Service service = Service.create(url, qname);
            IAgency agency = service.getPort(IAgency.class);

            TripList tripList = agency.listTrips();
            Optional<Trip> trip = tripList.getTrips().stream().filter(f -> f.getLocation().equals(destination)).findAny();

            if(trip.isPresent()) {
                Integer tripId = trip.get().getId();
                TripBooking tripBooking = new TripBooking();
                tripBooking.setTripId(tripId);
                tripBooking.setName(name);
                agency.addBooking(tripBooking);
                response.setTripBooking(tripBooking);
                response.setMessage("OK");
            }
            else {
                response.setMessage("No trip available");
            }

            return response;
        }
        catch (Exception ex) {
            response.setMessage(ex.getMessage());
            return response;
        }

    }
}
