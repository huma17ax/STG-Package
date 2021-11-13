package outerPackage;

import gameFrame.*;

public class Main {

	public static void main(String[] args) {
		//ウィンドウの生成
		Window win = new Window();

		//Playerの登録
		ObjectsManager.getEntity().AppNewObject(new Player(new Vector2<Double>((double)Window.WIDTH/2,(double)Window.HEIGHT/5*4)));

		//Enemyの登録
		ObjectsManager.getEntity().AppNewObject(new Enemy(new Vector2<Double>((double)Window.WIDTH/2,(double)Window.HEIGHT/5)));
	}

	Main(){}


}
