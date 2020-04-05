import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Test{
    public static void main(String[] args) throws IOException, ParseException {
        Manage manage = new Manage();
        manage.loadAll();

    }
}
