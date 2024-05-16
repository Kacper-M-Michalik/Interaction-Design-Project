package weather.app;

//Kacper Michalik
public class LocationSearchResult 
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

    @Override
    public String toString(){
        return Location + " " + Country;
    }
}
