package gameFrame;

import java.util.ArrayList;
import java.util.List;

public class LinerQuadTree<E>{

	//線形4分木の本体
	private List<ArrayList<E>> nodes;

	//4分木の階層数
	private int level;

	public LinerQuadTree(int lv){
		level=lv;
		nodes = new ArrayList<ArrayList<E>>(Level2Num(level));
		for (int i=0;i<Level2Num(level);i++) {
			nodes.add(new ArrayList<E>());
		}
	}

	//階層"lv"の先頭が線形4分木の何番目の要素かを返す
	public final int Level2Num(int lv) {
		return (int)((Math.pow(4, lv)-1)/3);
	}

	//階層"lv"とその中での番号"num"を受け取り，その位置に"obj"を追加する
	public final void addElm(int lv,int num,E obj) {
		ArrayList<E> list = nodes.get(Level2Num(lv)+num);
		list.add(obj);
	}

	//階層"lv"とその中での番号"num"を受け取り，その位置のリストを返す
	public final ArrayList<E> getNode(int lv,int num) {
		return nodes.get(Level2Num(lv)+num);
	}

	//線形4分木の要素の全削除
	public final void Clear() {
		for (ArrayList<E> list : nodes) {
			list.clear();
		}
	}

}
