package flexo.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Created by kcpr on 30.09.15.
 */
public class NumberField extends TextField {
//    private static final Logger logger = LoggerFactory.getLogger(DoubleTextField.class);

    public NumberField() { // [TODO] This code is rather temporary - needs to be reviewed and should be changed
        super();

        addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!isValid(getText())) {
                    event.consume();
                }
            }
        });

        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String oldValue, String newValue) {
                if(!isValid(newValue)) {
                    setText(oldValue);
                }
            }
        });
    }

    private boolean isValid(final String value) {
        if (value.length() == 0 || value.equals("-")) {
            return true;
        }

        if (value.indexOf(".") != value.lastIndexOf(".")) { //StringUtils.countMatches(value, ".") > 1) {
            return false;
        } if (value.endsWith(".")) {
            return true;
        }

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public double getNumber() {
        try {
            return Double.parseDouble(getText());
        } catch (NumberFormatException e) {
//            logger.error("Error parsing double (" + getText() +") from field.", e);
            return 0;
        }
    }

//    @Override
//    public void replaceText(final int start, final int end, final String text) {
//        if (text.matches("[0-9]*") || (text.matches("\\-?[0-9]*\\.[0-9]*")
//                && (!super.getText().contains(".") || super.getSelectedText().contains("."))
//                && (!super.getText().contains("-") || super.getSelectedText().contains("-")))) {
//            super.replaceText(start, end, text);
//        }
//    }
//
//
//    public double getNumber(){
//        return Double.parseDouble(getText());
//    }
////
////    @Override
////    public void replaceSelection(String replacement) {
////        System.out.println("sel");
//////        System.out.println(super.getText().replace(super.getSelectedText(), "").contains("."));
//////        if (replacement.matches("[0-9]*") || (replacement.matches("[0-9]*\\.[0-9]*")
//////                && super.getSelectedText().contains("."))) {
////            super.replaceSelection(replacement);
//////        }
////    }

}
