package outerPackage;

import java.awt.*;
import java.awt.event.*;

import gameFrame.*;
import gameFrame.Window;

public class Player extends GameObject{

	//内部処理用メンバ変数
	private int shotKeyCnt=0;
	private int moveMode = 0;
	private final double slowRate = 0.5;
	private double shotAngle = 0;

	//ステータス
	private int hp;
	private final int hp_max=100;

	public Player(Vector2<Double> pos) {
		super("player",pos);
		size = new Vector2<Double>(64.0,64.0);
		speed = 4;
		hp=hp_max;
		LoadImage("image/player.png");
	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Update() {
		super.Update();
		Shot();

		if (hp<=0) Die();
	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Draw(Graphics g) {
		Show(g);

		//画面下部に体力ゲージを表示する
		Graphics2D g2d = (Graphics2D)g;

		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, Window.HEIGHT-10, Window.WIDTH*hp/hp_max,Window.HEIGHT);
	}

	//GameObjectクラスのオーバーライド
	@Override
	public void OnCollision(GameObject obj) {
		super.OnCollision(obj);

		//衝突したのが敵の弾なら，処理を行う
		if (obj.getTag()=="ebullet") {
			Bullet bul = (Bullet)obj;
			hp-=bul.damage;
			obj.Die();
		}

	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Move() {

		//キー情報の取得
		int Xway=0,Yway=0;
		if (KeyInput.getEntity().isPressingKey(KeyEvent.VK_RIGHT)) {Xway++;}
		if (KeyInput.getEntity().isPressingKey(KeyEvent.VK_LEFT)) {Xway--;}
		if (KeyInput.getEntity().isPressingKey(KeyEvent.VK_UP)) {Yway--;}
		if (KeyInput.getEntity().isPressingKey(KeyEvent.VK_DOWN)) {Yway++;}

		//シフトキーを押すと移動モードを変更
		if (KeyInput.getEntity().isPressingKey(KeyEvent.VK_SHIFT)) {
			moveMode = 1;
			if (shotAngle<60) shotAngle+=2;
		} else {
			moveMode = 0;
			if (shotAngle>0) shotAngle-=2;
		}

		//入力された方向に，速さ[speed]で動く処理(斜め方向の処理を含む)
		if (Xway!=0) position.x += Xway * speed * (moveMode==0 ? 1 : slowRate) / Math.sqrt(Math.abs(Xway)+Math.abs(Yway));
		if (Yway!=0) position.y += Yway * speed * (moveMode==0 ? 1 : slowRate) / Math.sqrt(Math.abs(Xway)+Math.abs(Yway));

	}

	//射撃処理
	private void Shot() {
		if (KeyInput.getEntity().isPressingKey(KeyEvent.VK_Z)) {
			shotKeyCnt++;
		} else {
			shotKeyCnt=0;
		}

		//右側からでる弾
		if (shotKeyCnt%14==1) {
			double x = position.x + size.x *2/ 3 * Math.cos(Math.PI * 2 * shotAngle/360);
			double y = position.y - size.y *2/ 3 * Math.sin(Math.PI * 2 * shotAngle/360);
			ObjectsManager.getEntity().AppNewObject(new PBullet_type1(new Vector2<Double>(x,y)));
		}

		//左側から出る弾
		if (shotKeyCnt%14==8) {
			double x = position.x + size.x *2/ 3 * Math.cos(Math.PI * 2 * (180-shotAngle)/360);
			double y = position.y - size.y *2/ 3 * Math.sin(Math.PI * 2 * (180-shotAngle)/360);
			ObjectsManager.getEntity().AppNewObject(new PBullet_type1(new Vector2<Double>(x,y)));
		}
	}

}
