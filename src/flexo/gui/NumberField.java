package flexo.gui;

import javafx.scene.control.TextField;

/**
 * Created by kcpr on 30.09.15.
 */
public class NumberField extends TextField {

    @Override
    public void replaceText(final int start, final int end, final String text) {
        if (text.matches("[0-9]*") || (text.matches("[0-9]*\\.[0-9]*") && (!super.getText().contains(".") || super.getSelectedText().contains(".")))) {
            super.replaceText(start, end, text);
        }
    }
//
//    @Override
//    public void replaceSelection(String replacement) {
//        System.out.println("sel");
////        System.out.println(super.getText().replace(super.getSelectedText(), "").contains("."));
////        if (replacement.matches("[0-9]*") || (replacement.matches("[0-9]*\\.[0-9]*")
////                && super.getSelectedText().contains("."))) {
//            super.replaceSelection(replacement);
////        }
//    }

}
