package weather.app;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.*;
import java.time.temporal.ChronoUnit;

//Kacper Michalik + Joshua Chen
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
        ZonedDateTime CurrentTime = ZonedDateTime.now(ZoneId.of("GMT"));
        String SearchString = CurrentTime.withMinute(0).truncatedTo(ChronoUnit.MINUTES).toString();
        SearchString = SearchString.substring(0, SearchString.length() - 6);
        JSONArray Times = JSON.getJSONObject("hourly").getJSONArray("time");
        for (int i = 0; i < 24; i++)
        {
            if (Times.getString(i).equals(SearchString)) return i;
        }
        return 0;
    }

    public float[] GetFloatData(String Var) {
        int BaseIndex = GetCurrentTimeIndex();
        JSONArray Data = JSON.getJSONObject("hourly").getJSONArray(Var);

        float[] Results = new float[Data.length() - BaseIndex];
        for (int i = BaseIndex; i < Data.length(); i++)
        {
            Results[i - BaseIndex] = Data.getFloat(i);
        }
        return Results;
    }

    public float[] GetFloatData(String Var, int Offset) {
        int BaseIndex = GetCurrentTimeIndex() + Offset;
        JSONArray Data = JSON.getJSONObject("hourly").getJSONArray(Var);

        float[] Results = new float[Data.length() - BaseIndex];
        for (int i = BaseIndex; i < Data.length(); i++)
        {
            Results[i - BaseIndex] = Data.getFloat(i);
        }
        return Results;
    }

    public float[] GetTemperatures() {
        return GetFloatData("temperature_80m");
    }

    public float[] GetVisibilities() {
        return GetFloatData("visibility");
    }

    public float[] GetFreezingHeights() {
        return GetFloatData("freezing_level_height");
    }

    public float[] GetSnowfalls() {
        return GetFloatData("snowfall", 1);
    }

    public float[] GetSnowDepths() {
        return GetFloatData("snow_depth");
    }

    public float[] GetPrecipitations() {
        return GetFloatData("precipitation", 1);
    }

    public float[] GetApparentTemps() {
        return GetFloatData("apparent_temperature");
    }

    public int[] GetPrecipitationProbabilities()
    {
        int BaseIndex = GetCurrentTimeIndex() + 1;
        JSONArray Precips = JSON.getJSONObject("hourly").getJSONArray("precipitation_probability");
        
        int[] Results = new int[Precips.length() - BaseIndex];
        for (int i = BaseIndex; i < Precips.length(); i++)
        {
            Results[i - BaseIndex] = Precips.getInt(i);
        }
        return Results;
    }

    float GetCurrentTemperature() {
        return JSON.getJSONObject("current").getFloat("temperature_80m");
    }

    float GetCurrentVisibility() {
        return JSON.getJSONObject("current").getFloat("visibility");
    }

    float GetCurrentFreezingHeight() {
        return JSON.getJSONObject("current").getFloat("freezing_level_height");
    }

    float GetCurrentSnowfall() {
        return JSON.getJSONObject("current").getFloat("snowfall");
    }

    float GetCurrentSnowDepth() {
        return JSON.getJSONObject("current").getFloat("snow_depth");
    }

    float GetCurrentPrecipitation() {
        return JSON.getJSONObject("current").getFloat("precipitation");
    }

    float GetCurrentApparentTemp() {
        return JSON.getJSONObject("current").getFloat("apparent_temperature");
    }

    int GetCurrentPrecipitationProb() {
        return JSON.getJSONObject("current").getInt("precipitation_probability");
    }
}
