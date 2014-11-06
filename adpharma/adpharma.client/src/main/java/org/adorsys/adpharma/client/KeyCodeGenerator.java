package org.adorsys.adpharma.client;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class KeyCodeGenerator {
	
	public static final String CTRL_A="Ctrl+A";
	
	public static final String CTRL_B="Ctrl+B";
	
	public static final String CTRL_C="Ctrl+C";
	
	public static final String CTRL_D="Ctrl+D";
	
	public static final String CTRL_E="Ctrl+E";
	
	public static final String CTRL_F="Ctrl+F";
	
	public static final String CTRL_O="Ctrl+O";
	
	
	public static KeyCombination getKeyCodeCombination(String keyCodeCombination) {
	    KeyCombination combination;
		if(keyCodeCombination.equals(CTRL_A)) {
			combination= new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN);
			return combination;
		}else if (keyCodeCombination.equals(CTRL_B)) {
			combination= new KeyCodeCombination(KeyCode.B, KeyCombination.SHORTCUT_DOWN);
			return combination;
		}else if (keyCodeCombination.equals(CTRL_C)) {
			combination= new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN);
			return combination;
		}else if (keyCodeCombination.equals(CTRL_D)) {
			combination= new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN);
			return combination;
		}else if (keyCodeCombination.equals(CTRL_E)) {
			combination= new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
			return combination;
		}else if (keyCodeCombination.equals(CTRL_F)) {
			combination= new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN);
			return combination;
		}
		return null;
	}
	

}
