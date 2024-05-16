package weather.app;

import org.json.JSONObject;

//Kacper Michalik
public class WeatherData 
{
    LocationSearchResult LocationData;
    JSONObject JSON;

    public WeatherData(LocationSearchResult SetLocationData, JSONObject SetJSONData) 
    {
        LocationData = SetLocationData;
        JSON = SetJSONData;
    }
}
