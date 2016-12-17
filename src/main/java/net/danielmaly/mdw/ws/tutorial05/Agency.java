package net.danielmaly.mdw.ws.tutorial05;

import javax.jws.WebService;
import java.util.*;

@WebService
public class Agency {

    private static int idCounter = 0;

    private Map<Integer, Trip> trips = new HashMap<>();
    private List<TripBooking> tripBookingList = new ArrayList<>();

    public Agency() {
        Trip trip1 = new Trip();
        trip1.setLocation("Prague");
        trip1.setCapacity(10);
        trip1.setId(1);

        Trip trip2 = new Trip();
        trip2.setLocation("Paris");
        trip2.setCapacity(10);
        trip2.setId(2);

        trips.put(1, trip1);
        trips.put(2, trip2);
    }

    public TripList listTrips() {
        TripList tripList = new TripList();
        tripList.setTrips(new ArrayList<>(trips.values()));
        return tripList;
    }

    public TripCreateResponse addTrip(TripCreateData data) {
        Trip trip = new Trip();
        trip.setCapacity(data.getCapacity());
        trip.setLocation(data.getLocation());
        trip.setId(idCounter++);
        trips.put(trip.getId(), trip);
        TripCreateResponse resp = new TripCreateResponse();
        resp.setTripId(trip.getId());
        return resp;
    }

    public void addBooking(TripBooking tripBooking) {
        Trip trip = trips.get(tripBooking.getTripId());
        trip.getTripBookings().add(tripBooking);
        tripBookingList.add(tripBooking);
    }

}
