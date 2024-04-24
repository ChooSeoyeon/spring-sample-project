package hello;

import java.util.Locale;
import org.springframework.format.Printer;

public class StringPrinter implements Printer<String> {
    private StringBuilder buffer = new StringBuilder();

    @Override
    public String print(String object, Locale locale) {
        buffer.append(object);
        System.out.println(object);
        return object;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
