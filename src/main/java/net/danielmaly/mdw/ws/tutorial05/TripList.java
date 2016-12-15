package net.danielmaly.mdw.ws.tutorial05;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class TripList {
    private List<Trip> trips;

    @XmlElementWrapper(name="trips")
    @XmlElement(name="trip")
    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
