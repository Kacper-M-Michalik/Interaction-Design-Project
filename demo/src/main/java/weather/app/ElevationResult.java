package weather.app;

//Kacper Michalik
public class ElevationResult {
    public float[][] Elevations;
    public float MinElevation;
    public float MaxElevation;
    public ElevationResult(float[][] elevations, float minElevation, float maxElevation) {
        Elevations = elevations;
        MinElevation = minElevation;
        MaxElevation = maxElevation;
    }
}
