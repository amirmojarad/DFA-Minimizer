import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.StringTokenizer;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer strToken = new StringTokenizer(reader.readLine(), " ");
        System.out.println(strToken.nextToken());
        System.out.println(strToken.nextToken());
        System.out.println(strToken.nextToken());
    }
}
