package gameFrame;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public abstract class GameObject {

	//各種ステータス
	public Vector2<Double> position,size,scale;
	public double speed;
	public BufferedImage image;

	//オブジェクトタグ
	String tag;

	//存在フラグ
	boolean exist;

	public GameObject(String tag,Vector2<Double> pos) {
		this.exist=true;
		this.tag = tag;
		this.position = pos;
		this.size = new Vector2<Double>(0.0,0.0);
		this.scale = new Vector2<Double>(1.0,1.0);
		this.speed = 0;
		this.image = null;
	}

	public GameObject(String tag,Vector2<Double> pos,String img_path){
		this.exist = true;
		this.tag= tag;
		this.position = pos;
		this.size = new Vector2<Double>(0.0,0.0);
		this.scale = new Vector2<Double>(1.0,1.0);
		this.speed = 0;

		LoadImage(img_path);
	}

	public GameObject(String tag,Vector2<Double> pos,String img_path,
			Vector2<Double> size,Vector2<Double> scale,double speed) {
		this.exist=true;
		this.tag= tag;
		this.position = pos;
		this.size = size;
		this.scale = scale;
		this.speed = speed;

		LoadImage(img_path);
	}

	//画像読み込み
	protected void LoadImage(String path) {
		try {
			image = ImageIO.read(new File(path));
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	//毎フレーム呼ばれる更新メソッド
	public void Update() {
		Move();
	}

	//移動メソッド
	public void Move() {}

	//描画メソッド
	public void Draw(Graphics g) {}

	//自オブジェクト表示
	protected void Show(Graphics g) {
		g.drawImage(
				image,
				(int)(position.x-(size.x*scale.x)/2),
				(int)(position.y-(size.x*scale.y)/2),
				(int)(position.x+(size.x*scale.x)/2),
				(int)(position.y+(size.x*scale.y)/2),
				0,0,64,64,
				null);
	}

	//オブジェクトタグ取得
	public final String getTag() {
		return tag;
	}

	//衝突すると呼ばれる衝突メソッド
	public void OnCollision(GameObject obj) {
		//objに相手オブジェクトが渡される
	}

	//オブジェクト破壊
	public final void Die() {
		exist=false;
	}
}
