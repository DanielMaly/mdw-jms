package net.danielmaly.mdw.ws.homework07;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService
public interface IAirline {
    public FlightBookingResponse addBooking(@WebParam(name="booking") @XmlElement(required = true) FlightBookingCreateData data);
    public AirlineServiceResponse removeBooking(@WebParam(name="bookingId") @XmlElement(required = true) Integer bookingId);
    public AirlineServiceResponse updateBooking(@WebParam(name="updateFields") @XmlElement(required = true) FlightBookingUpdateData updateData);
    public FlightBookingsListResponse getBookingsList();
}
