package gr.personal.story.domain;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
public class Geolocation {

    private double latitude;
    private double longitude;

    public Geolocation() {
    }

    public Geolocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Geolocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
