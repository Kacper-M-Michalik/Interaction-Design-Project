package weather.app;

import java.util.*;

public class UserProfile {
    private static final int maxFavourites = 4, maxRecents = 4;
    private static final List<String>  favourites = new ArrayList<>();
    private static final Deque<String> recents = new LinkedList<>();
    private static String currentLocation = "Current Location";


    public static void setCurrentLocation(LocationSearchResult place){
        setCurrentLocation(place.toString());
    }

    public static void setCurrentLocation(String placeName){
        currentLocation = placeName;
        updateRecents();
    }

    public static LocationSearchResult getCurrentLatLong(){
        return new LocationSearchResult("Current", "Location", 52.2109f, 0.0917f);
    }

    public static String getCurrentLocation(){
        return currentLocation;
    }

    public static boolean isFavourite(){
        return favourites.contains(currentLocation);
    }

    public static boolean addToFavourites(){
        if (favourites.size() >= maxFavourites  || currentLocation.equalsIgnoreCase("current location")){
            return false;
        }
        if (!favourites.contains(currentLocation)) {
            favourites.add(currentLocation);
        }
        recents.removeIf(favourites::contains);
        return true;
    }

    public static boolean removeFromFavourites(String placeName) {
        boolean success = favourites.remove(placeName);
        updateRecents();
        return success;
    }

    private static void updateRecents(){
        if (currentLocation.equalsIgnoreCase("current location")){
            return;
        }
        recents.remove(currentLocation);
        recents.addFirst(currentLocation);
        recents.removeIf(favourites::contains);
        if (recents.size() > maxRecents){
            recents.removeLast();
        }
    }

    public static List<String> getFavourites(){
        return favourites;
    }

    public static Deque<String> getRecents(){
        return recents;
    }
}
