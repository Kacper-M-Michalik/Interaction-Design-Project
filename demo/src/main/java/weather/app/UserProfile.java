package weather.app;

import java.util.*;

public class UserProfile {
    private static final int maxFavourites = 4, maxRecents = 4;
    private static final List<String>  favourites= new ArrayList<>();
    private static final Queue<String> recents = new LinkedList<>();
    private static String currentLocation = "";


    public static void setCurrentLocation(LocationSearchResult place){
        setCurrentLocation(place.toString());
    }

    public static void setCurrentLocation(String placeName){
        currentLocation = placeName;
    }

    public static String getCurrentLocation(){
        return currentLocation;
    }

    public static boolean isFavourite(){
        return favourites.contains(currentLocation);
    }

    public static boolean addToFavourites(String placeName){
        if (favourites.size() > maxFavourites){
            return false;
        }
        if (!favourites.contains(placeName)) {
            favourites.add(placeName);
        }
        recents.removeIf(favourites::contains);
        return true;
    }

    public static boolean removeFromFavourites(String placeName) {
        return favourites.remove(placeName);
    }

    public static void updateRecents(String placeName){
        recents.remove(placeName);
        recents.add(placeName);
        recents.removeIf(favourites::contains);
        if (recents.size() > maxRecents){
            recents.remove();
        }
    }

    public static List<String> getFavourites(){
        return favourites;
    }


    public static Queue<String> getRecents(){
        return recents;
    }



}
