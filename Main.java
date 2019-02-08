import java.util.Random;

import javax.swing.JFrame;

public class Main {
	static JFrame frame;
	public static void main(String[] args) {
		frame = new JFrame();
		switch(new Random().nextInt(20)) {
		case 0: frame.setTitle("you feel like you're going to have a bad time");break;
		case 1: frame.setTitle("Unfair Minesweeper");break;
		case 2: frame.setTitle("this was your first mistake");
        case 3: frame.setTitle("AUTOBALANCING YOUR CRITICAL HITS");break;
		default: frame.setTitle("Fair and Balanced Minesweeper");break;
		}
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MainPanel());
		frame.setVisible(true);
	}

}
