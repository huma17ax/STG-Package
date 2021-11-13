package gameFrame;

public class Vector2<T> {

	//XYをそれぞれ指定するコンストラクタ
	public Vector2(T x,T y){
		this.x = x;
		this.y = y;
	}
	
	//すでにあるVector2と同じXYを持つVector2を生成
	public Vector2(Vector2<T> vec) {
		this.x = vec.x;
		this.y = vec.y;
	}

	public T x;
	public T y;

}
