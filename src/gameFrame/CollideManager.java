package gameFrame;

import java.util.*;

//Singleton

public class CollideManager {

	//シングルトン実装用インスタンス
	private static CollideManager collideManager = new CollideManager();

	//4分木空間分割の最大分割レベル
	private int levelLimit;

	//単位距離(最大分割時の1空間の大きさ)
	private Vector2<Double> UD;

	//線形4分木
	private LinerQuadTree<GameObject> LQT;

	private CollideManager() {
		levelLimit = 5;
		UD = new Vector2<Double>(
				Window.WIDTH / Math.pow(2,levelLimit-1),
				Window.HEIGHT / Math.pow(2,levelLimit-1));
		LQT = new LinerQuadTree<GameObject>(levelLimit);
	}

	//インスタンス取得
	public static CollideManager getEntity() {
		return collideManager;
	}

	//メインループから呼ばれるメソッド
	//各処理のメソッドを呼び出す
	void MainJudge() {
		LQT.Clear();
		MortonUpdate();
		CrashJudgeMain();
	}

	//全オブジェクトのモートン番号の更新
	//線形4分木への登録
	void MortonUpdate() {

		//ObjectsManagerの管理テーブル全探索
		Iterator<LinkedList<GameObject>> itr1 = ObjectsManager.getEntity().objectsTable.values().iterator();

		while(itr1.hasNext()) {
			LinkedList<GameObject> llist = itr1.next();
			Iterator<GameObject> itr2 = llist.iterator();

			while(itr2.hasNext()) {
				GameObject obj = itr2.next();

				Vector2<Double> obj_size = obj.size;

				//座標が画面外の場合は判定を行わない
				if (Window.Contain(new Vector2<Double>(obj.position.x-obj_size.x/2, obj.position.y-obj_size.y/2))==false) continue;
				if (Window.Contain(new Vector2<Double>(obj.position.x+obj_size.x/2, obj.position.y+obj_size.y/2))==false) continue;

				//オブジェクト左上のモートン番号
				int MN_LeftUp = MortonNumber(
						new Vector2<Integer>(
								(int)((obj.position.x-obj_size.x/2)/UD.x),
								(int)((obj.position.y-obj_size.y/2)/UD.y)));

				//オブジェクト右下のモートン番号
				int MN_RightDown = MortonNumber(
						new Vector2<Integer>(
								(int)((obj.position.x+obj_size.x/2)/UD.x),
								(int)((obj.position.y+obj_size.y/2)/UD.y)));

				//オブジェクトの所属階層と空間番号を決定
				int MN_xor = MN_LeftUp ^ MN_RightDown;
				int Level = levelLimit-1;
				for (int i=0;i<levelLimit-1;i++) {
					if (MN_xor == 0) break;
					Level--;
					MN_xor >>= 2;
					MN_RightDown >>= 2;
				}

				//線形4分木への追加
				LQT.addElm(Level,MN_RightDown,obj);

			}

		}

	}

	//座標からモートン番号を求める
	private int MortonNumber(Vector2<Integer> pos) {

		if (pos.x < 0) pos.x=0;
		if (pos.y < 0) pos.y=0;
		if (pos.x >= Math.pow(4, levelLimit)) pos.x = (int)Math.pow(4, levelLimit)-1;
		if (pos.y >= Math.pow(4, levelLimit)) pos.y = (int)Math.pow(4, levelLimit)-1;

		return (BitSeparate32(pos.x) | (BitSeparate32(pos.y)<<1));
	}

	//32bit整数型(int型)の下16bitを1bit飛びにする
	private int BitSeparate32(int INT) {

		INT = (INT | (INT << 8)) & 0x00ff00ff;
		INT = (INT | (INT << 4)) & 0x0f0f0f0f;
		INT = (INT | (INT << 2)) & 0x33333333;
		INT = (INT | (INT << 1)) & 0x55555555;

		return INT;
	}


	//線形四分木を元に当たり判定
	//GameObjectの当たり判定関数の呼び出し
	void CrashJudgeMain() {

		//衝突可能性リスト
		Deque<GameObject> collidableList = new ArrayDeque<GameObject>();


		int nowLevel=0;
		boolean checkFlag = true;
		int[] checkNum = new int[levelLimit];
		int[] nowList = new int[levelLimit];

		while(true) {
			if (checkNum[0]==1) break;

			if (checkFlag) {
				//現在空間内の当たり判定
				for (GameObject obj1 : LQT.getNode(nowLevel,nowList[nowLevel])) {
					for (GameObject obj2 : LQT.getNode(nowLevel,nowList[nowLevel])) {
						if (obj1!=obj2 && CrashJudgeObj2Obj(obj1,obj2) == true) {
							obj1.OnCollision(obj2);
							obj2.OnCollision(obj1);
						}
					}
				}

				//衝突可能性リストとの当たり判定
				for (GameObject obj1 : LQT.getNode(nowLevel , nowList[nowLevel])) {
					for (GameObject obj2 : collidableList) {
						if (obj1!=obj2 && CrashJudgeObj2Obj(obj1,obj2) == true) {
							obj1.OnCollision(obj2);
							obj2.OnCollision(obj1);
						}
					}
				}
			}

			//現在空間の下に未巡回の空間がない
			if (nowLevel == levelLimit-1 || checkNum[nowLevel+1]==3) {

				//下の空間の巡回カウントを0に戻す
				if (nowLevel != levelLimit-1) checkNum[nowLevel+1]=0;

				nowList[nowLevel]++;

				//同じ階層に未巡回の空間がある
				if (checkNum[nowLevel]  < 3) {
					checkNum[nowLevel]++;
					checkFlag = true;
					continue;
				}

				//上の階層に戻る
				nowLevel--;

				//上の階層のオブジェクトを衝突可能性リストから削除
				for (int i=0;i<LQT.getNode(nowLevel, nowList[nowLevel]).size();i++) {
					collidableList.pop();
				}

				checkFlag = false;
				continue;
			}

			//下に空間がある
			//現在空間のオブジェクトを衝突可能性リストへ追加
			for (GameObject obj : LQT.getNode(nowLevel , nowList[nowLevel])) {
				collidableList.push(obj);
			}

			nowLevel++;
			checkFlag = true;

		}

	}

	//衝突判定メソッド(ユーザーがオーバーライドできる用)
	protected boolean CrashJudgeObj2Obj(GameObject obj1,GameObject obj2) {
		return CrashJudgeSquare(obj1,obj2);
	}

	//矩形衝突判定メソッド
	public final boolean CrashJudgeSquare(GameObject obj1,GameObject obj2) {

		if (Math.abs(obj1.position.x-obj2.position.x)
				< (obj1.size.x + obj2.size.x)/2
			&& Math.abs(obj1.position.y-obj2.position.y)
				< (obj1.size.y + obj2.size.y)/2) {
			return true;
		}
		return false;
	}

}
