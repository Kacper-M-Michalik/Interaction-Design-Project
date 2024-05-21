package weather.app;

import java.io.Serializable;

import org.json.JSONObject;

//Kacper Michalik
public class ElevationResult implements Serializable
{
    public LocationSearchResult LocationData;

    public int Size;
    public float[][] Elevations;
    public float MinElevation;
    public float MaxElevation;

    public JSONObject WeatherData;

    public ElevationResult(LocationSearchResult locationData, int size, float[][] elevations, float minElevation,
            float maxElevation) {
        LocationData = locationData;
        Size = size;
        Elevations = elevations;
        MinElevation = minElevation;
        MaxElevation = maxElevation;
    }

    public ElevationResult(float[][] elevations, float minElevation, float maxElevation) 
    {
        LocationData = new LocationSearchResult("Unknown", "Unknown", 0f, 0f);
        Size = elevations.length;
        Elevations = elevations;
        MinElevation = minElevation;
        MaxElevation = maxElevation;
    }
}
