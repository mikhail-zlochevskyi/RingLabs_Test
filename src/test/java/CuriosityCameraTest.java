import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CuriosityCameraTest {

    public static final String API_KEY = "2wyfEESO8DP2s5tqjsDMBbORVCOy1X4Fv4FKb4XC";
    public static final String BASE_REQ_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";

    public static final int SOL = 1000;
    public static final String EARTH_DATE = "2015-5-30";

    public static final int PHOTOS_CNT = 10;


    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_REQ_URL;
    }

    @Test
    public void comparePhotos_Sol_EarthDate() {
        for (int i = 0; i < PHOTOS_CNT; i++) {
            given().when()
                    .get("?earth_date=" + EARTH_DATE + "&api_key=" + API_KEY).then()
                    .assertThat().body("photos[" + i + "]", equalTo(
                            get("?sol=" + SOL + "&api_key=" + API_KEY).path("photos[" + i + "]"))
                    );

        }
    }

    @AfterClass
    public void cleanUp() {

    }
}
