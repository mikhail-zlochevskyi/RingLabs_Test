import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

/**
 * Created by mikhail_z on 8/29/17.
 */
public class CuriosityCameraTest {

    public static final String API_KEY = "2wyfEESO8DP2s5tqjsDMBbORVCOy1X4Fv4FKb4XC";
    public static final String BASE_REQ_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";

    public static final int SOL = 1000;
    public static final String EARTH_DATE = "2015-5-30";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_REQ_URL;
    }

    @Test
    public void compareImages_Sol_EarthDate(){
        System.out.println(given().when()
                .get("?sol=" + SOL + "&api_key=" + API_KEY).then()
                .assertThat().body("photos[0].id", equalTo(102693)));

        given().when().get("?sol=" + SOL + "&api_key=" + API_KEY).then().statusCode(200);
    }

    @AfterClass
    public void cleanUp(){

    }
}
