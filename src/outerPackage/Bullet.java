package outerPackage;

import gameFrame.*;

public abstract class Bullet extends GameObject{

	//ステータス
	int damage;
	int angle;

	public Bullet(String tag,Vector2<Double> pos) {
		super(tag,pos);
		damage=0;
		angle=0;
	}

}
