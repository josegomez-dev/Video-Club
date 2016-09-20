package InterfazUsuario;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ValidacionForm {

	public static boolean campoDeTextoNoVacio(TextField pcampo){
		boolean r = false;
		if(pcampo.getText() != null && !pcampo.getText().isEmpty()){
			r = true;
		}
		return r;
	}

	public static boolean campoDeTextoNoVacio(TextField pcampo, Label plabel, String pmensaje){
		boolean r = true;
		String c = null;
		if(!campoDeTextoNoVacio(pcampo)){
			r = false;
			c = pmensaje;
		}
		plabel.setText(c);
		return r;
	}

	public static boolean areaDeTextoNoVacio(TextArea pcampo){
		boolean r = false;
		if(pcampo.getText() != null && !pcampo.getText().isEmpty()){
			r = true;
		}
		return r;
	}

	public static boolean areaDeTextoNoVacio(TextArea pcampo, Label plabel, String pmensaje){
		boolean r = true;
		String c = null;
		if(!areaDeTextoNoVacio(pcampo)){
			r = false;
			c = pmensaje;
		}
		plabel.setText(c);
		return r;
	}

	public static boolean campoTipoFecha(TextField pcampo){
		boolean r = false;
		if(pcampo.getText().matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((18|19|20)\\d\\d)")){
			r = true;
		}
		return r;
	}

	public static boolean campoTipoFecha(TextField pcampo, Label plabel, String pmensaje){
		boolean r = true;
		String c = null;
		if(!campoTipoFecha(pcampo)){
			r = false;
			c = pmensaje;
		}
		plabel.setText(c);
		return r;
	}

	public static boolean campoTipoNumero(TextField pcampo){
		boolean r = false;
		if(pcampo.getText().matches("([0-9]+(\\.[0-9]+)?)+")){
			r = true;
		}
		return r;
	}

	public static boolean campoTipoNumero(TextField pcampo, Label plabel, String pmensaje){
		boolean r = true;
		String c = null;
		if(!campoTipoNumero(pcampo)){
			r = false;
			c = pmensaje;
		}
		plabel.setText(c);
		return r;
	}

	public static boolean campoComboBox(ComboBox<String> pcampo){
		boolean r = false;
		if(pcampo.getValue() != null){
			r = true;
		}
		return r;
	}

	public static boolean campoComboBox(ComboBox<String> pcampo, Label plabel, String pmensaje){
		boolean r = true;
		String c = null;
		if(!campoComboBox(pcampo)){
			r = false;
			c = pmensaje;
		}
		plabel.setText(c);
		return r;
	}
}
