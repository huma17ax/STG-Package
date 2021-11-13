package outerPackage;

import java.awt.*;

import gameFrame.*;
import gameFrame.Window;

public class Enemy extends GameObject{

	//ステータス
	private final int hp_max = 1000;
	private int hp;

	//射撃用クラス
	private ETower tower;

	public Enemy(Vector2<Double> pos) {
		super("enemy",pos);
		size=new Vector2<Double>(64.0,64.0);
		speed=0;
		hp = hp_max;
		LoadImage("image/enemy.png");
		tower = new Etower_type1();
	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Update() {
		super.Update();
		tower.Update();

		if (hp<=0) Die();

	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Draw(Graphics g) {
		super.Draw(g);
		Show(g);

		//画面上部に体力ゲージを表示する
		Graphics2D g2d = (Graphics2D)g;

		g2d.setColor(Color.RED);
		g2d.fillRect(0, 0, Window.WIDTH*hp/hp_max,10);
	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Move(){

	}

	//GameObjectクラスのオーバーライド
	@Override
	public void OnCollision(GameObject obj) {
		super.OnCollision(obj);

		//衝突したのがプレイヤーの弾なら，処理を行う
		if (obj.getTag()=="pbullet") {
			Bullet bul = (Bullet)obj;
			hp-=bul.damage;
			obj.Die();
		}

	}

}
