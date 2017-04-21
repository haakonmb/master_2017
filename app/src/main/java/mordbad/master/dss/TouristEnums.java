package mordbad.master.dss;

import java.util.EnumMap;

/**
 * Created by haakon on 30/03/17.
 */

public class TouristEnums {


//    public EnumMap FORMAL;
//    {"Besøke venner", "annen ferie", "forretningsreise","på vei til annet land","skiferie"}

    public static int[] lokal_mat = {6, 8, 14, 53, 54, 71};
    public static int[] shoppe = {4, 10, 11, 14, 24, 25, 28, 31, 37, 40, 42, 45, 49, 75, 76};
    public static int[] historiske = {4, 21, 22, 23, 26, 32, 41, 48, 55, 59};
    public static int[] slappe_av = {3, 4, 8, 11, 12, 14, 20, 34, 39, 48, 62, 71, 77, 89};
    public static int[] teater = {3, 4, 57, 59};
    public static int[] kunst_museum = {4, 59};
    public static int[] moro = {2, 3, 4, 11, 12, 14, 20, 31, 48, 56, 57, 60, 62, 89, 86};
    public static int[] lokal_historie = {22, 23, 26, 32, 21, 41, 48, 50, 55, 59, 74, 78, 82, 87};
    public static int[] sightseeing = {2, 3, 4, 6, 8, 9, 11, 15, 20, 21, 22, 23, 26, 32, 41, 43, 48, 50, 55, 59, 60, 62, 64, 68, 71, 73, 74, 76, 78, 82, 87, 89};
    public static int[] lokal_kultur = {3, 4, 22, 23, 26, 32, 33, 21, 48, 55, 59, 60, 62, 71, 74, 78, 82, 86, 87, 89};
    public static int[] restaurant = {6, 8, 14, 53, 54, 71};

    public static int[][] all_maps = { lokal_mat,shoppe,historiske,slappe_av,teater,kunst_museum,moro,lokal_historie,sightseeing,lokal_kultur,restaurant};
}