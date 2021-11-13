package gameFrame;

import java.awt.Graphics;
import java.util.*;

//Singleton

public class ObjectsManager {

	//シングルトン実装用インスタンス
	private static ObjectsManager objectsManager = new ObjectsManager();

	//登録待ちリスト
	private LinkedList<GameObject> resistAppList;

	//オブジェクト管理テーブル
	Map<String,LinkedList<GameObject>> objectsTable;

	private ObjectsManager() {
		objectsTable = new HashMap<String,LinkedList<GameObject>>();
		resistAppList = new LinkedList<GameObject>();
	}

	//インスタンス取得
	public static ObjectsManager getEntity() {
		return objectsManager;
	}

	//登録待ちリストからオブジェクト登録
	private synchronized void ResistNewObject() {

		while(resistAppList.size()!=0) {
			GameObject obj = resistAppList.poll();
			if (obj==null) break;

			if (objectsTable.containsKey(obj.tag) == false) {
				objectsTable.put(obj.tag, new LinkedList<GameObject>());
			}
			objectsTable.get(obj.tag).add(obj);
		}

	}

	//登録待ちリストへ追加
	public void AppNewObject(GameObject obj) {
		resistAppList.offer(obj);
	}

	//管理テーブルの更新
	synchronized void Update() {

		for (LinkedList<GameObject> list : objectsTable.values()) {

			//死んだオブジェクトの削除
			list.removeIf(obj -> obj.exist == false);

			for (GameObject obj : list) {
				//管理テーブル内のオブジェクトの更新
				obj.Update();
			}
		}

		//新オブジェクトの登録
		ResistNewObject();

	}

	//描画メソッドの呼び出し
	synchronized void Draw(Graphics g) {

		Iterator<LinkedList<GameObject>> itr1 = objectsTable.values().iterator();

		while(itr1.hasNext()) {
			LinkedList<GameObject> llist = itr1.next();
			Iterator<GameObject> itr2 = llist.iterator();

			while(itr2.hasNext()) {
				GameObject obj = itr2.next();
				//管理テーブル内のオブエジェクトの描画メソッド呼び出し
				obj.Draw(g);
			}

		}

	}

	//指定タグのオブジェクトリストを読み取り専用で返す
	public List<GameObject> getObjectsList(String tag){
		if (objectsTable.containsKey(tag)) {
			return Collections.unmodifiableList(objectsTable.get(tag));
		} else {
			return null;
		}
	}

}
