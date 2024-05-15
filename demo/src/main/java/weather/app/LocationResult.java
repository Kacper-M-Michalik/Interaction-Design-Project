package weather.app;

//Kacper Michalik
public class LocationResult 
{
    public boolean Success;
    public float Lat;
    public float Long;

    public LocationResult(boolean SetSuccess, float SetLat, float SetLong)
    {
        Success = SetSuccess;
        Lat = SetLat;
        Long = SetLong;
    }
}
