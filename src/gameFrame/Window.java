package gameFrame;

import java.awt.*;
import javax.swing.*;

public class Window extends JPanel implements Runnable{

	//ウインドウサイズ
	public static final int WIDTH = 930;
	public static final int HEIGHT = 720;

	//メインスレッド
	private Thread thread;

	public Window() {
		makeWindow();

		thread = new Thread(this);
		thread.start();
	}

	//ウィンドウの生成
	private void makeWindow() {
		JFrame frame = new JFrame();
		frame.setTitle("Shooting");

		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(new Color(0,0,0));

		Container contentPane = frame.getContentPane();
		contentPane.add(this);

		frame.addKeyListener(KeyInput.getEntity());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	//座標が画面内かどうかを判定
	public static boolean Contain(final Vector2<Double> pos) {
		if (pos.x >= 0 && pos.y >= 0 && pos.x <= WIDTH && pos.y <= HEIGHT ) {
			return true;
		} else {
			return false;
		}
	}

	//JPanelの描画関数
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		ObjectsManager.getEntity().Draw(g);
	}

	//Runnableのメインループ
	@Override
	public void run() {
		while(true) {
			ObjectsManager.getEntity().Update();
			CollideManager.getEntity().MainJudge();

			repaint();

			try {
				Thread.sleep(5);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
