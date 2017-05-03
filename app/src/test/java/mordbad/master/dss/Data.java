package mordbad.master.dss;

//import android.location.Location;

import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by haakon on 25/04/17.
 */

class Data {


    static  HashMap<String, Double[]> lookup_probability;
    static    Double[] priors_from_data = new Double[11];
    static int[] stats = {
            //purpose of trip
            2,
            //country of origin
            1,
            //gender
            1,
            //age-bracket
            3,
            //education-level
            3,
            //status
            1};
    static mockLocation location = new mockLocation("");

//    Location location = new Location("");
//    location.setLatitude();


    public static         String[] apriori = {
            "0.480848369737259",
            "0.364988920544476",
            "0.522317188983856",
            "0.652421652421652",
            "0.0177271288382399",
            "0.255777144666034",
            "0.503640392529281",
            "0.187084520417854",
            "0.336182336182336",
            "0.211142766698322",
            "0.476100031655587"
    };

public static    String[] evidence =       {
            "1,1,0.485175202156334,0.407008086253369,0.37466307277628,0.61455525606469,0.0350404312668464,0.226415094339623,0.482479784366577,0.140161725067385,0.223719676549865,0.159029649595687,0.528301886792453",
            "1,2,0.482884195193008,0.357611070648216,0.542971595047342,0.659504734158776,0.0156591405680991,0.259286234522942,0.508011653313911,0.193008011653314,0.350691915513474,0.21849963583394,0.471595047341588",
            "2,1,0.386026817219478,0.369795342272407,0.31545518701482,0.663373323923783,0.0190543401552576,0.153140437544107,0.493295695130558,0.118560338743825,0.145377558221595,0.1030345800988,0.496118560338744",
            "2,2,0.475460122699386,0.289366053169734,0.656441717791411,0.68200408997955,0.0153374233128834,0.285276073619632,0.531697341513292,0.202453987730061,0.448875255623722,0.242331288343558,0.402862985685072",
            "2,3,0.772357723577236,0.491869918699187,0.808943089430894,0.650406504065041,0.032520325203252,0.532520325203252,0.556910569105691,0.365853658536585,0.524390243902439,0.51219512195122,0.678861788617886",
            "2,4,0.605839416058394,0.474452554744526,0.671532846715328,0.45985401459854,1,0.204379562043796,0.277372262773723,0.18978102189781,0.518248175182482,0.27007299270073,0.35036496350365",
            "2,5,0.617250673854447,0.417789757412399,0.716981132075472,0.611859838274933,0.0161725067385445,0.409703504043127,0.525606469002695,0.288409703504043,0.574123989218329,0.326145552560647,0.509433962264151",
            "3,1,0.498039215686275,0.394771241830065,0.526143790849673,0.665359477124183,0.0183006535947712,0.281699346405229,0.539869281045752,0.196078431372549,0.352941176470588,0.218300653594771,0.511764705882353",
            "3,2,0.471333333333333,0.336666666666667,0.518,0.648,0.018,0.237333333333333,0.471333333333333,0.178666666666667,0.322666666666667,0.204,0.454666666666667",
            "4,1,0.538690476190476,0.413690476190476,0.544642857142857,0.666666666666667,0.00892857142857143,0.264880952380952,0.699404761904762,0.181547619047619,0.43452380952381,0.261904761904762,0.553571428571429",
            "4,2,0.477083333333333,0.388541666666667,0.49375,0.691666666666667,0.021875,0.222916666666667,0.597916666666667,0.185416666666667,0.352083333333333,0.201041666666667,0.5",
            "4,3,0.501419110690634,0.377483443708609,0.533585619678335,0.674550614947966,0.0151371807000946,0.271523178807947,0.480605487228004,0.191106906338694,0.308420056764428,0.192999053926206,0.494796594134342",
            "4,4,0.438672438672439,0.298701298701299,0.53968253968254,0.582972582972583,0.0230880230880231,0.284271284271284,0.331890331890332,0.181818181818182,0.313131313131313,0.229437229437229,0.404040404040404",
            "5,1,0.33457249070632,0.368029739776952,0.479553903345725,0.572490706319703,0.0111524163568773,0.130111524163569,0.386617100371747,0.111524163568773,0.267657992565056,0.118959107806691,0.349442379182156",
            "5,2,0.435820895522388,0.359203980099502,0.455721393034826,0.681592039800995,0.0199004975124378,0.212935323383085,0.512437810945274,0.158208955223881,0.251741293532338,0.166169154228856,0.486567164179104",
            "5,3,0.51593137254902,0.397058823529412,0.517156862745098,0.655637254901961,0.0208333333333333,0.278186274509804,0.550245098039216,0.186274509803922,0.338235294117647,0.202205882352941,0.526960784313726",
            "5,4,0.559947299077734,0.351778656126482,0.624505928853755,0.679841897233202,0.0131752305665349,0.326745718050066,0.52437417654809,0.245059288537549,0.462450592885375,0.300395256916996,0.47562582345191",
            "5,5,0.581818181818182,0.357575757575758,0.63030303030303,0.606060606060606,0.0303030303030303,0.375757575757576,0.442424242424242,0.278787878787879,0.466666666666667,0.290909090909091,0.539393939393939",
            "5,6,0.321428571428571,0.392857142857143,0.428571428571429,0.571428571428571,0.0357142857142857,0.142857142857143,0.392857142857143,0.107142857142857,0.142857142857143,0.214285714285714,0.285714285714286",
            "6,1,0.486913849509269,0.384405670665213,0.516357688113413,0.685932388222465,0.0185387131952017,0.239912758996728,0.534351145038168,0.1886586695747,0.330970556161396,0.186477644492912,0.503271537622683",
            "6,2,0.521505376344086,0.341397849462366,0.510752688172043,0.658602150537634,0.021505376344086,0.276881720430108,0.513440860215054,0.193548387096774,0.330645161290323,0.231182795698925,0.508064516129032",
            "6,3,0.437022900763359,0.312977099236641,0.557251908396947,0.557251908396947,0.0229007633587786,0.295801526717557,0.349236641221374,0.185114503816794,0.330152671755725,0.248091603053435,0.395038167938931",
            "6,4,0.5,0.442307692307692,0.544871794871795,0.705128205128205,0.0128205128205128,0.294871794871795,0.711538461538462,0.198717948717949,0.467948717948718,0.314102564102564,0.525641025641026",
            "6,5,0.525,0.3375,0.53125,0.61875,1,0.25625,0.51875,0.175,0.33125,0.2375,0.4125",
            "6,6,0.625,0.25,0.625,0.625,1,0.375,0.375,0.25,0.25,0.25,0.625"};


  public static  String[] place_candidates = {
            "accounting",
            "airport",
            "amusement_park",
            "aquarium",
            "art_gallery",
            "atm",
            "bakery",
            "bank",
            "bar",
            "beauty_salon",
            "bicycle_store",
            "book_store",
            "bowling_alley",
            "bus_station",
            "cafe",
            "campground",
            "car_dealer",
            "car_rental",
            "car_repair",
            "car_wash",
            "casino",
            "cemetery",
            "church",
            "city_hall",
            "clothing_store",
            "convenience_store",
            "courthouse",
            "dentist",
            "department_store",
            "doctor",
            "electrician",
            "electronics_store",
            "embassy",
            "fire_station",
            "florist",
            "funeral_home",
            "furniture_store",
            "gas_station",
            "gym",
            "hair_care",
            "hardware_store",
            "hindu_temple",
            "home_goods_store",
            "hospital",
            "insurance_agency",
            "jewelry_store",
            "laundry",
            "lawyer",
            "library",
            "liquor_store",
            "local_government_office",
            "locksmith",
            "lodging",
            "meal_delivery",
            "meal_takeaway",
            "mosque",
            "movie_rental",
            "movie_theater",
            "moving_company",
            "museum",
            "night_club",
            "painter",
            "park",
            "parking",
            "pet_store",
            "pharmacy",
            "physiotherapist",
            "plumber",
            "police",
            "post_office",
            "real_estate_agency",
            "restaurant",
            "roofing_contractor",
            "rv_park",
            "school",
            "shoe_store",
            "shopping_mall",
            "spa",
            "stadium",
            "storage",
            "store",
            "subway_station",
            "synagogue",
            "taxi_stand",
            "train_station",
            "transit_station",
            "travel_agency",
            "university",
            "veterinary_care",
            "zoo"
    };
    public static Probabilitator probabilitator = generateProbabilitator(stats);


    private static void loadDataFromAsset() {

//        int[] stats = {1,1,2,1,1,1};

//        ActiveAndroid.beginTransaction();
        String cvsSplitBy = ",";
        lookup_probability = new HashMap<>();
        // priors_from_data = new Double[11];

        String[] apriori = Data.apriori;

        String[] evidence = Data.evidence;


        Double[] apriori_double= new Double[11]               ;

        for(int i=0; i< apriori.length; i++){
            apriori_double[i] = Double.parseDouble( apriori[i]);
//            System.out.println(apriori_double[i]);

        }

        for(String s: evidence){
            String[] data = s.split(cvsSplitBy);
            String key = data[0] + data[1];


            List<Double> tmp = new ArrayList<>();
            for(int i = 2; i < data.length; i++){
                tmp.add(Double.parseDouble(data[i]));

            }


//              Making the value into an array of the correct type and size automagically and putting it in the hashmap
            lookup_probability.put(key,tmp.toArray(new Double[tmp.size()]));
        }

        priors_from_data = apriori_double;

    }


    //if you want a probabilitator based on different demographic data, run this. Note that it changes the default Probabilitator and then returns it.
    public static Probabilitator generateProbabilitator(int[] newstats){
        stats = newstats;
        loadDataFromAsset();
        probabilitator = new Probabilitator(priors_from_data,lookup_probability,stats);
        return probabilitator;
    }

    public static class mockLocation extends Location{
        double latitude = 60.389097;
        double longitude =5.324683;

        public mockLocation(String provider) {
            super(provider);


        }

        @Override
        public double getLatitude() {
            return latitude;
        }

        @Override
        public double getLongitude(){
            return longitude;
        }
    }
}
