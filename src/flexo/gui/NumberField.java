package flexo.gui;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class NumberField extends TextField { // [TODO] Check if regex works correctly

    public NumberField() {
        setTextFormatter(new TextFormatter<>(change -> {
            if (change.isAdded() && !change.getControlNewText().matches("\\-?\\d*(\\.\\d*)?")) {
                return null;
            }
            return change;
        }));
    }

    public double getNumber() {
        String text = getText();
        if (text.length() == 0 || text.equals("-")) {
            return 0;
        }
        return Double.parseDouble(text);
    }

    public void setNumber(Number number) {
        setText(String.valueOf(number));
    }

}