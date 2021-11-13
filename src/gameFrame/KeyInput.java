package gameFrame;

import java.awt.event.*;
import java.util.*;

//Singleton

public class KeyInput implements KeyListener{

	//シングルトン実装用インスタンス
	private static KeyInput keyInput = new KeyInput();

	private KeyInput() {
	}

	//インスタンス取得
	public static KeyInput getEntity() {
		return keyInput;
	}

	//キー情報の連想配列
	Map<Integer,Boolean> keyStates = new HashMap<Integer,Boolean>();

	@Override
	//キー押下時
	public void keyPressed(KeyEvent e) {
		keyStates.put(e.getKeyCode(),true);
	}

	@Override
	//キー離した時
	public void keyReleased(KeyEvent e) {
		keyStates.put(e.getKeyCode(),false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	//キー情報を返す
	public boolean isPressingKey(int keyCode) {
		if (keyStates.containsKey(keyCode)) {
			return keyStates.get(keyCode);
		} else {
			return false;
		}
	}

}
