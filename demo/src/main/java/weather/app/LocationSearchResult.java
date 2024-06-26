package weather.app;

import java.io.Serializable;

//Kacper Michalik
public class LocationSearchResult implements Serializable
{
    public String Location;
    public String Country;
    public float Lat;
    public float Long;

    public LocationSearchResult(String SetLocation, String SetCountry, float SetLat, float SetLong)
    {
        Location = SetLocation;
        Country = SetCountry;
        Lat = SetLat;
        Long = SetLong;
    }

    public String toString()
    {
        return Location + " " + Country;
    }
}
