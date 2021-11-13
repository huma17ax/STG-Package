package outerPackage;

import java.awt.Graphics;

import gameFrame.*;

public class PBullet_type1 extends Bullet{

	public PBullet_type1(Vector2<Double> pos) {
		super("pbullet",pos);
		size=new Vector2<Double>(8.0,8.0);
		speed=8;
		damage=20;
		angle=270;
		LoadImage("image/bullet.png");
	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Update() {
		super.Update();

		//画面外に出たら死ぬ
		if (Window.Contain(position)==false) Die();
	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Draw(Graphics g) {
		Show(g);
	}

	//GameObjectクラスのオーバーライド
	@Override
	public void Move() {
		position.x += speed * Math.cos(Math.PI * 2 * angle/360);
		position.y += speed * Math.sin(Math.PI * 2 * angle/360);
	}

}
