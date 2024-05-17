package weather.app;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.*;
import java.time.temporal.ChronoUnit;

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

    private int GetCurrentTimeIndex()
    {
        LocalDateTime CurrentTime = LocalDateTime.now();
        String SearchString = CurrentTime.withMinute(0).truncatedTo(ChronoUnit.MINUTES).toString();
        JSONArray Times = JSON.getJSONObject("hourly").getJSONArray("time");
        for (int i = 0; i < 24; i++)
        {
            if (Times.getString(i).equals(SearchString)) return i;
        }
        
        return 0;
    }

    public float[] GetTemperatures()
    {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray Temps = JSON.getJSONObject("hourly").getJSONArray("temperature_80m");
        
        float[] Results = new float[Temps.length() - BaseIndex];
        for (int i = BaseIndex; i < Temps.length(); i++)
        {
            Results[i - BaseIndex] = Temps.getFloat(i);
        }
        return Results;
    }

    public float[] GetVisibilities()
    {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray Visibs = JSON.getJSONObject("hourly").getJSONArray("visibility");
        
        float[] Results = new float[Visibs.length() - BaseIndex];
        for (int i = BaseIndex; i < Visibs.length(); i++)
        {
            Results[i - BaseIndex] = Visibs.getFloat(i);
        }
        return Results;
    }
    
    public float[] GetFreezingHeights()
    {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray Heights = JSON.getJSONObject("hourly").getJSONArray("freezing_level_height");
        
        float[] Results = new float[Heights.length() - BaseIndex];
        for (int i = BaseIndex; i < Heights.length(); i++)
        {
            Results[i - BaseIndex] = Heights.getFloat(i);
        }
        return Results;
    }
    
    public float[] GetSnowfalls()
    {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray Snowfalls = JSON.getJSONObject("hourly").getJSONArray("snowfall");
        
        float[] Results = new float[Snowfalls.length() - BaseIndex];
        for (int i = BaseIndex; i < Snowfalls.length(); i++)
        {
            Results[i - BaseIndex] = Snowfalls.getFloat(i);
        }
        return Results;
    }
    
    public float[] GetSnowdepths()
    {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray Snowdepths = JSON.getJSONObject("hourly").getJSONArray("snow_depth");
        
        float[] Results = new float[Snowdepths.length() - BaseIndex];
        for (int i = BaseIndex; i < Snowdepths.length(); i++)
        {
            Results[i - BaseIndex] = Snowdepths.getFloat(i);
        }
        return Results;
    }

    public float[] GetPrecipitation()
    {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray Precips = JSON.getJSONObject("hourly").getJSONArray("precipitation");
        
        float[] Results = new float[Precips.length() - BaseIndex];
        for (int i = BaseIndex; i < Precips.length(); i++)
        {
            Results[i - BaseIndex] = Precips.getFloat(i);
        }
        return Results;
    }

    public float[] GetApparentTemps()
    {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray ApparentTemps = JSON.getJSONObject("hourly").getJSONArray("apparent_temperature");

        float[] Results = new float[ApparentTemps.length() - BaseIndex];
        for (int i = BaseIndex; i < ApparentTemps.length(); i++)
        {
            Results[i - BaseIndex] = ApparentTemps.getFloat(i);
        }
        return Results;
    }

    public int[] GetPrecipitationProbabilities()
    {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray Precips = JSON.getJSONObject("hourly").getJSONArray("precipitation_probability");
        
        int[] Results = new int[Precips.length() - BaseIndex];
        for (int i = BaseIndex; i < Precips.length(); i++)
        {
            Results[i - BaseIndex] = Precips.getInt(i);
        }
        return Results;
    }

}
