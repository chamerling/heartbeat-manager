import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset("utf-8", response);
    }
    
    @Test
    public void hostTest() {
        Response addBarResponse = POST("/status/", APPLICATION_X_WWW_FORM_URLENCODED, "host.hostname=http://foo");
        assertStatus(200, addBarResponse);
        Response listBarsResponse = GET("/hosts.json");
        assertIsOk(listBarsResponse);
    }
    
}