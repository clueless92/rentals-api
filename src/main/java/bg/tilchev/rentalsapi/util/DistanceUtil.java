package bg.tilchev.rentalsapi.util;

public class DistanceUtil {

    /**
     * Calculate distance between two points in latitude and longitude. Uses Haversine method as its base.
     *
     * @param lat1 latitude of point 1
     * @param lon1 longitude of point 1
     * @param lat2 latitude of point 2
     * @param lon2 longitude of point 2
     * @returns true if the points are within 100 miles of each other
     */
    public static boolean arePointsWithing100Miles(double lat1, double lon1, double lat2, double lon2) {
        double distanceInMiles = distance(lat1, lon1, lat2, lon2, true);
        return distanceInMiles <= 100d;
    }

    /**
     * Calculate distance between two points in latitude and longitude. Uses Haversine method as its base.
     *
     * @param lat1 latitude of point 1
     * @param lon1 longitude of point 1
     * @param lat2 latitude of point 2
     * @param lon2 longitude of point 2
     * @param convertToMiles whether to convert the result to miles or keep it in meters
     * @returns Distance in Meters/miles
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2, boolean convertToMiles) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000d; // convert to meters
        if (!convertToMiles) {
            return distance;
        }
        return distance * 0.000621371192d; // convert to miles
    }
}
