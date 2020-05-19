package apgutil;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {
    @Test
    public void UtilStringTest1() {
        Assert.assertEquals("2020.05.09", UtilString.getDoubleString("2020.05.09"));
        Assert.assertEquals("20200509", UtilString.getIntString("2020.05.09"));
        Assert.assertEquals("123456789", UtilString.getIntString("123,456,789원"));
    }

    //@Test
    public void utilFileTest1() {
        File file = new File("c:\\temp\\parent\\appagatest.txt");
        try {
            Assert.assertEquals(file, UtilFile.downloadFileFromUrl("https://m.naver.com", file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}