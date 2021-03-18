

import code.Data;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DataTest {
    private Data dataTxt;
    private Data dataJSON;
    @Before
    public void setUp(){
        this.dataTxt = new Data("test.txt");
        this.dataJSON = new Data("test.json");
    }
    @Test
    public void testTextFileRead(){
        assertEquals("This is a test file to test the reading of Data Class.\n", this.dataTxt.getBoard());
    }
    @Test
    public void testJSONFileRead(){
        System.out.println(this.dataJSON.getJsonData());
        assertEquals("{\"Test\":\"test\"}", this.dataJSON.getJsonData().toString());
    }
}
