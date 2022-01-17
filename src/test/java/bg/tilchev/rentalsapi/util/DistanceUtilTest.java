package bg.tilchev.rentalsapi.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DistanceUtilTest {
    private final static double BASE_LAT = 33.64;
    private final static double BASE_LON = -117.93;
    private final static double CLOSE_LAT = 32.83;
    private final static double CLOSE_LON = -117.28;
    private final static double DISTANT_LAT = 54.72;
    private final static double DISTANT_LON = -2.88;

    @Test
    public void givenPointsAreCloseWhenArePointsWithing100MilesThenTrue() {
        Assertions.assertTrue(DistanceUtil.arePointsWithing100Miles(BASE_LAT, BASE_LON, CLOSE_LAT, CLOSE_LON));
    }

    @Test
    public void givenPointsAreDistantWhenArePointsWithing100MilesThenFalse() {
        Assertions.assertFalse(DistanceUtil.arePointsWithing100Miles(BASE_LAT, BASE_LON, DISTANT_LAT, DISTANT_LON));
    }
}
