package weather.app;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;
import java.io.*;
import org.json.*;

//Kacper Michalik
public class WeatherAndLocationManager 
{
    public static WeatherData CurrentData;
    public static ElevationResult CurrentElevationData;

    final static String CacheFileName = "CachedData.json";

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
        String APIString = String.format("https://api.open-meteo.com/v1/forecast?latitude=%.3f&longitude=%.3f&hourly=precipitation_probability,precipitation,snowfall,snow_depth,visibility,temperature_80m,freezing_level_height,apparent_temperature&current=precipitation_probability,precipitation,snowfall,snow_depth,visibility,temperature_80m,freezing_level_height,apparent_temperature", Lat, Long);
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
        LoadWeatherData(LocationResult.Lat, LocationResult.Long);
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

    public static void LoadElevationData(LocationSearchResult LocationResult)
    {
        //For Lat/Long:
        //4th decimal place unit = 11m distance

        if (CheckCachedElevationData(LocationResult)) return;

        final int SampleSize = 50;
        float[][] Elevations = new float[SampleSize][SampleSize];

        int TotalPacked = 0;
        int x = 0;
        int y = 0;        
        
        float Offset = (float)(SampleSize - 1) * 0.5f * 0.0001f;
        float Long = LocationResult.Long + y * 0.0001f - Offset;
        float Min = Float.POSITIVE_INFINITY;
        float Max = Float.NEGATIVE_INFINITY;

        NumberFormat Formatter = new DecimalFormat("0.0000");
        StringBuilder Builder = new StringBuilder();

        while (TotalPacked < SampleSize * SampleSize)
        {
            int PackedCount = 0;
            Builder = new StringBuilder();
            Builder.append("https://api.opentopodata.org/v1/eudem25m?locations=");

            int CopyX = x;
            int CopyY = y;

            while (PackedCount < 100 && TotalPacked < SampleSize * SampleSize)
            {
                float Lat = LocationResult.Lat + x * 0.0001f - Offset;
                Builder.append(Formatter.format(Lat));
                Builder.append(",");
                Builder.append(Formatter.format(Long));         
                Builder.append("|");
                
                TotalPacked++;
                PackedCount++;                    
                x++;
                if (x >= SampleSize)
                {
                    x = 0;
                    y++;                    
                    Long = LocationResult.Long + y * 0.0001f - Offset;
                }
            }

            Builder.deleteCharAt(Builder.length() - 1);
            Builder.append("&interpolation=cubic");
            
            boolean Exit = false;
            JSONArray WebData = null;
            while (!Exit)
            {
                try 
                {
                    WebData = new JSONObject(GetWebDataUTF8(Builder.toString())).getJSONArray("results");
                    Exit = true;
                } 
                catch (Exception e) 
                {            
                    try 
                    {                    
                        TimeUnit.MILLISECONDS.sleep(400);
                    } catch (Exception e2) 
                    {              
                        System.out.println("CRITICAL FAIL - ELEVATION DOWNLOAD");      
                        System.out.print(e);
                        System.out.print(e2);
                        
                        CurrentElevationData = new ElevationResult(new float[0][0], 0, 0);
                        return;
                    }    
                }
            }

            for (int i = 0; i < PackedCount; i++)
            {
                final float Val = WebData.getJSONObject(i).getFloat("elevation");
                Elevations[CopyY][CopyX] = Val;
                if (Val > Max) Max = Val;
                if (Val < Min) Min = Val;

                CopyX++;
                if (CopyX >= SampleSize)
                {
                    CopyX = 0;
                    CopyY++;                    
                }
            }
        }

        CurrentElevationData = new ElevationResult(Elevations, Min, Max);
        WriteElevationDataToCache();
    }

    public static boolean CheckCachedElevationData(LocationSearchResult LocationResult)
    {
        try {
            File FileHandle = new File(CacheFileName);
            
            if (!FileHandle.exists())
            {
                FileHandle.createNewFile();
                return false;
            }

            try  
            {                    
                FileInputStream FIN = new FileInputStream(CacheFileName);
                ObjectInputStream OIS = new ObjectInputStream(FIN);
                CurrentElevationData = (ElevationResult)OIS.readObject();
                OIS.close();
                
                //Add check for lat/long match in future

                return true;
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return false;
    }

    private static void WriteElevationDataToCache()
    {
        try 
        {
            //Writer OutputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CacheFileName), "UTF-8"));
            //OutputWriter.write("\"1\" : \"టుటోరియల్స్ పాయింట్ కి స్వాగతిం\"");            
            //OutputWriter.close();

            FileOutputStream FOS = new FileOutputStream(CacheFileName);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.writeObject(CurrentElevationData);
            OOS.close();

        } 
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}

