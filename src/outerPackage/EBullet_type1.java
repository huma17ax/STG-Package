package outerPackage;

import java.awt.Graphics;

import gameFrame.*;

public class EBullet_type1 extends Bullet{

	public EBullet_type1(Vector2<Double> pos,int angle) {
		super("ebullet", pos);
		this.size=new Vector2<Double>(8.0,8.0);
		this.speed=3;
		this.angle=angle;
		this.damage=10;
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
