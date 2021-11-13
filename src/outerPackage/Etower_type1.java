package outerPackage;

import java.util.List;

import gameFrame.*;

public class Etower_type1 extends ETower{

	//内部処理用メンバ変数
	private int shotCnt=0;
	private int wayNum=7;

	Etower_type1(){
		super();
	}

	//ETowerクラスのオーバーライド
	@Override
	public void Update(){
		List<GameObject> ene = ObjectsManager.getEntity().getObjectsList("enemy");

		//Enemyと位置を一致させる
		position.x = ene.get(0).position.x;
		position.y = ene.get(0).position.y;

		Shot();
	}

	//射撃処理
	private void Shot() {
		shotCnt++;
		if (shotCnt%10==1) {
			for (int i=0;i<wayNum;i++) {
				ObjectsManager.getEntity().AppNewObject(new EBullet_type1(new Vector2<Double>(position),shotCnt + i*360/wayNum));
			}
		}
	}

}
