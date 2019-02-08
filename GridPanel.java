import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class GridPanel extends JPanel{
	private boolean mode;
	private BoardHandler board;
	private int bombsLeft;
	private boolean firstClick;
   private Square[][] buttons;
	public GridPanel(int width, int height, int numOfBombs) {
		bombsLeft = numOfBombs;
		board = new BoardHandler(width, height, numOfBombs);
		setLayout(new GridLayout(width,height));
      buttons = new Square[height][width];
		for(int rows = 0; rows < height; rows++) {
			for(int cols = 0; cols < width; cols++) {
				buttons[rows][cols] = new Square(cols,rows);
            add(buttons[rows][cols]);
			}
		}
		firstClick = false;
	}
	public void changeMode() {
		mode = !mode;
	}
	private boolean correctFlag(int x, int y) {
		if(board.getHiddenBoard()[y][x] == 'X') {
			return true;
		}
		return false;
	}
	private class Square extends JToggleButton {
		private boolean flag;
		
		public Square(int x, int y) {
			super("#");
			
			flag = false;
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(mode) {
						flag = !flag;
						if(flag) {
							setText("F");
							setSelected(true);
							if(correctFlag(x,y)) {
								bombsLeft--;
							}
							if(bombsLeft == 0) {
								System.out.println("Congratulations. It didnt break!");
								System.exit(0);
							}
						} else {
							setText("#");
							setSelected(false);
							if(correctFlag(x,y)) {
								bombsLeft++;
							}
						}
					} 
					
					else if(!flag){
						if(firstClick) {
							board.click(x, y);
							setText("" +board.getPublicBoard()[y][x]);
							setEnabled(false);
						} else {
							board.firstClick(x, y);
							
							setText("" +board.getPublicBoard()[y][x]);
							board.debug(board.getHiddenBoard());;
							setEnabled(false);
							firstClick = !firstClick;
						}
                  if(board.getPublicBoard()[y][x] == ' ') {
                  for(int i = 0; i < 9; i++) {
                     try{
                      if(board.getPublicBoard()[y+BoardHandler.SURROUND[i][1]][x+BoardHandler.SURROUND[i][0]] == '#') {
                           try{
                              buttons[y+BoardHandler.SURROUND[i][1]][x+BoardHandler.SURROUND[i][0]].doClick();
                           }catch(StackOverflowError er) {
                              return;
                           }
                        }
                     }catch(ArrayIndexOutOfBoundsException ex) {}
                  }
               }

					} else {
						setSelected(true);
					}
									}
			});
		}
		
	}
}
