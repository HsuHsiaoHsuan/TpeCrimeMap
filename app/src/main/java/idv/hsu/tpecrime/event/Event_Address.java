package idv.hsu.tpecrime.event;

import com.google.android.gms.maps.model.LatLng;

public class Event_Address {
    private String location;
    private LatLng latLng;

    public Event_Address(String location, LatLng latLng) {
        this.location = location;
        this.latLng = latLng;
    }

    public String getLocation() {
        return location;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
