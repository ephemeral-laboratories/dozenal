package garden.ephemeral.dozenal;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocaleIntegrationTest {

    @Test
    public void testNormalLocale() {
        assertEquals("42", NumberFormat.getIntegerInstance().format(42));
    }

    @Test
    public void testDozenalLocale() throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("java",
                "-Djava.locale.providers=SPI,JRE",
                "-Duser.language=en",
                "-Duser.country=US",
                "-Duser.variant=DOZ",
                "-Xbootclasspath/a:" + System.getProperty("java.class.path"),
                LocaleIntegrationTest.class.getName());
        builder.redirectErrorStream(true);
        Process process = builder.start();
        try (InputStream stream = process.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            assertEquals("36", reader.readLine());
        }
        assertEquals(0, process.waitFor());
    }

    public static void main(String[] args) {
        System.out.println(NumberFormat.getIntegerInstance().format(42));
    }
}
