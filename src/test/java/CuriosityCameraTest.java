import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static java.util.function.Function.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.Matchers.equalTo;


public class CuriosityCameraTest {

    public static final String API_KEY = "2wyfEESO8DP2s5tqjsDMBbORVCOy1X4Fv4FKb4XC";
    public static final String BASE_REQ_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";

    public static final int SOL = 1000;
    public static final String EARTH_DATE = "2015-5-30";
    public static final int PHOTOS_CNT = 10;

    private final static Logger log = Logger.getLogger(CuriosityCameraTest.class);

    private Response responseEarthDate;
    private Response responseSol;

    @BeforeClass
    public void setUp() {
        log.info("Start CuriosityCameraTest");
        RestAssured.baseURI = BASE_REQ_URL;

        responseEarthDate = get("?earth_date=" + EARTH_DATE + "&api_key=" + API_KEY);
        responseSol = get("?sol=" + SOL + "&api_key=" + API_KEY);
    }

    @Test
    public void comparePhotos_Sol_EarthDate() {
        for (int i = 0; i < PHOTOS_CNT; i++) {
            responseEarthDate.then()
                    .assertThat().body("photos[" + i + "]", equalTo(
                    responseSol.path("photos[" + i + "]")
                    ));
            log.info("Image link for EARTH_DATE endpoint: " + responseEarthDate.path("photos[" + i + "].img_src"));
            log.info("Image link for SOL endpoint: " + responseSol.path("photos[" + i + "].img_src"));
        }


    }

    @Test
    public void comparePhotosByEachCamera(){
        List<String> cameraIds = responseSol.jsonPath().getList("photos.camera.id");
        Map<String, Long> countPhotosByCamera = cameraIds.stream().collect(groupingBy(
                identity(), counting()
        ));
        log.info("Count of photos by each camera: " + countPhotosByCamera);
        List<Long> countOfPhotos = new ArrayList<Long>(countPhotosByCamera.values());
        Long maxPhotos = countOfPhotos.stream().max(Long::compare).get();
        Long minPhotos = countOfPhotos.stream().min(Long::compare).get();
        log.info("Max photos count: " + maxPhotos);
        log.info("Min photos count: " + minPhotos);
        Assert.assertTrue(maxPhotos / minPhotos < 10, "Some of cameras has more photos than other one on more than 10 times!");
    }

    @AfterClass
    public void cleanUp() {
        log.info("End CuriosityCameraTest");
    }
}
