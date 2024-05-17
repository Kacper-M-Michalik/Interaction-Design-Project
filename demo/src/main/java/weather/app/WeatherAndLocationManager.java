package weather.app;

import java.net.*;
import java.nio.charset.StandardCharsets;

import java.io.*;
import org.json.*;

//Kacper Michalik
public class WeatherAndLocationManager 
{
    public static WeatherData CurrentData;

    private static String GetWebDataUTF8(String DataURL) throws Exception
    {
        InputStream Stream;
        
        URL RequestURL = new URL(DataURL);
        Stream = RequestURL.openStream();

        ByteArrayOutputStream OutputBuffer = new ByteArrayOutputStream();
        byte[] TempBuffer = new byte[1024];
        
        int Size = Stream.read(TempBuffer, 0, 1024);
        while (Size  != -1) 
        {
            OutputBuffer.write(TempBuffer, 0, Size);
            Size = Stream.read(TempBuffer, 0, 1024);
        }

        return new String(OutputBuffer.toByteArray(), StandardCharsets.UTF_8);
    }

    public static void LoadWeatherData(float Lat, float Long)
    {
        String APIString = String.format("https://api.open-meteo.com/v1/forecast?latitude=%.3f&longitude=%.3f&hourly=precipitation_probability,precipitation,snowfall,snow_depth,visibility,temperature_80m,freezing_level_height", Lat, Long);
        try 
        {
            JSONObject WebData = new JSONObject(GetWebDataUTF8(APIString));
            CurrentData = new WeatherData(new LocationSearchResult("Unknown", "Unknown", Lat, Long), WebData);
        }
        catch (Exception e) 
        {            
            System.out.print(e);
        }
    }
    
    public static void LoadWeatherData(LocationSearchResult LocationResult)
    {
        String APIString = String.format("https://api.open-meteo.com/v1/forecast?latitude=%.3f&longitude=%.3f&hourly=precipitation_probability,precipitation,snowfall,snow_depth,visibility,temperature_80m,freezing_level_height,apparent_temperature", LocationResult.Lat, LocationResult.Long);
        try
        {
            JSONObject WebData = new JSONObject(GetWebDataUTF8(APIString));
            CurrentData = new WeatherData(LocationResult, WebData);
        } 
        catch (Exception e) 
        {            
            System.out.print(e);
        }
    }

    public static LocationSearchResult[] SearchLocations(String Location)
    {
        String APIKey = "ab24e7163e9faf456a82dff51533b614";
        int Limit = 5;

        String[] Locations = Location.split(" ");
        String APIString;

        LocationSearchResult[] Results = new LocationSearchResult[0];

        if (Locations.length == 0) return Results;
        
        if (Locations.length >= 2 && Locations[Locations.length - 1].length() == 2)
        {
            APIString = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s&limit=%d&appid=%s", Locations[0], Locations[Locations.length - 1], Limit, APIKey);
        }
        else
        {
            APIString = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%d&appid=%s", Locations[0], Limit, APIKey);
        }

        String WebData;
        try 
        {            
            WebData = GetWebDataUTF8(APIString);            
        } 
        catch (Exception e) 
        {
            System.out.print(e);
            return Results;
        }
        
        JSONArray LocationData = new JSONArray(WebData);
        Results = new LocationSearchResult[LocationData.length()];

        for (int i = 0; i < Results.length; i++)
        {            
            JSONObject Current = LocationData.getJSONObject(i);
            Results[i] = new LocationSearchResult(Current.getString("name"), Current.getString("country"), Current.getFloat("lat"), Current.getFloat("lon"));
        }

        return Results;
    }

}

