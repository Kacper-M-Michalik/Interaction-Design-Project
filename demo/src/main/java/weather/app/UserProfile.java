package weather.app;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
public class UserProfile {
    private static List<LocationSearchResult> favourites = new ArrayList<>();
    private static Queue<LocationSearchResult> recents = new ArrayDeque<>();
    private static int maxFavourites = 4;
    private static int maxRecents = 4;

    public static void addToFavourites(LocationSearchResult placeName) throws ExceededMaxSizeException{
        if (favourites.size() == 5){
            throw new ExceededMaxSizeException("Attempted to insert more favourites than the limit" + maxFavourites);
        }
        favourites.add(placeName);
    }

    public static List<LocationSearchResult> getFavourites(){
        return favourites;
    }

    public static List<String> getFavouritesString(){
        List<String> temp = new ArrayList<>();
        for (LocationSearchResult l: favourites){
            temp.add(l.toString());
        }
        return temp;
    }

    public static Queue<LocationSearchResult> getRecents(){
        return recents;
    }

    public static List<String> getRecentsString(){
        List<String> temp = new ArrayList<>();
        for (LocationSearchResult l: recents){
            temp.add(l.toString());
        }
        return temp;
    }


}
