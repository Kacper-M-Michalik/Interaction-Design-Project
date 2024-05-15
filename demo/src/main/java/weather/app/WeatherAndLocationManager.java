package weather.app;

import java.net.*;


import java.io.*;

//Kacper Michalik
public class WeatherAndLocationManager 
{
    static String WeatherData = "";

    static String GetWebData(String DataURL) throws Exception
    {
        InputStream Stream;
        
        URL RequestURL = new URL(DataURL);
        Stream = RequestURL.openStream();
        
        StringBuffer Buffer = new StringBuffer();

        int ptr = Stream.read();
        while (ptr != -1) 
        {
            Buffer.append((char)ptr);            
            ptr = Stream.read();            
        }
        
        String Data = Buffer.toString();
        System.out.print(Data);
        return Data;
    }

    public static void LoadWeatherData(float Lat, float Long)
    {              
        String APIString = String.format("https://api.open-meteo.com/v1/forecast?latitude=%.3f&longitude=%.3f&hourly=temperature_2m,relative_humidity_2m", Lat, Long);
        try 
        {
            WeatherData = GetWebData(APIString);
        } 
        catch (Exception e) 
        {            
            System.out.print(e);
        }
    }

    public static LocationResult GetCoordsFromLocation(String Location)
    {
        String APIKey = "ab24e7163e9faf456a82dff51533b614";
        int Limit = 5;

        String[] Locations = Location.split(" ");
        String APIString;

        if (Locations.length == 0) return new LocationResult(false, 0, 0);
        
        if (Locations.length == 1) 
        {
            APIString = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%d&appid=%s", Locations[0], Limit, APIKey);
        }
        else
        {
            APIString = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s&limit=%d&appid=%s", Locations[0], Locations[Locations.length - 1], Limit, APIKey);
        }

        String LocationData;
        try 
        {            
            LocationData = GetWebData(APIString);
        } 
        catch (Exception e) 
        {
            System.out.print(e);
            return new LocationResult(false, 0, 0);
        }

        //Translate location data
        //JSONObject oobj = new JSONObject(LocationData);
        
        System.out.print(LocationData);
        return new LocationResult(true, 0, 0);
    }

}

