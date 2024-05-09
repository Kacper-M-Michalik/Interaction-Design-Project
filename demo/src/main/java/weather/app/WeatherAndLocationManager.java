package weather.app;

import java.net.*;
import java.io.*;

//Kacper Michalik
public class WeatherAndLocationManager 
{
    static String WeatherData = "";

    public static void LoadWeatherData(float Lat, float Long) throws Exception
    {      
        URL RequestURL;
        InputStream Stream;
        
        String APIString = String.format("https://api.open-meteo.com/v1/forecast?latitude=%.3f&longitude=%.3f&hourly=temperature_2m,relative_humidity_2m", Lat, Long);
        RequestURL = new URL(APIString);
        Stream = RequestURL.openStream();
        
        StringBuffer Buffer = new StringBuffer();

        int ptr = Stream.read();
        while (ptr != -1) 
        {
            Buffer.append((char)ptr);            
            ptr = Stream.read();            
        }
        
        WeatherData = Buffer.toString();
        System.out.print(WeatherData);
    }

    public static LocationResult GetCoordsFromLocation(String Location)
    {
        return null;
    }

}

