package flexo.gui;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * Created by kcpr on 30.09.15.
 */
public class NumberField extends TextField { // [TODO] Check if there are more characters entered than 'double' can handle

    public NumberField() {
        setTextFormatter(new TextFormatter<>(change -> {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ROOT);
            String controlNewText = change.getControlNewText();
            ParsePosition parsePosition = new ParsePosition(0);
            numberFormat.parse(controlNewText, parsePosition);
            if (change.isAdded() && parsePosition.getIndex() != controlNewText.length()) {
                return null;
            }
            return change;
        }));
    }

    public double getNumber() {
        String text = getText();
        if (text.length() == 0) {
            return 0;
        }
        return Double.parseDouble(text);
    }

}