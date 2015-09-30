package flexo.gui;

import javafx.scene.control.TextField;

/**
 * Created by kcpr on 30.09.15.
 */
public class NumberField extends TextField {

    @Override
    public void replaceText(final int start, final int end, final String text) {
        System.out.println(text);

        if (text.matches("[0-9]?") || (text.matches("\\.") && !super.getText().contains("."))) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        System.out.println("selection");
        if (replacement.matches("[0-9]?") || replacement.matches("\\.") && !super.getText().contains(".")) {
            super.replaceSelection(replacement);
        }
    }


}
