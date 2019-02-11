import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainPanel extends JPanel {
	
	public MainPanel() {
		setLayout(new BorderLayout());
      int x,y,bombs;
      x = 0; y =0; bombs = 0;
      while(true){
         try{
            x = Integer.parseInt(JOptionPane.showInputDialog("size:"));
            y = x;
            bombs = (int) (x*y*0.4);
            //bombs = Integer.parseInt(JOptionPane.showInputDialog("Bombs"));
         } catch(NumberFormatException ex) {
            x= 0;y=0;bombs=0;
         }
         if(x > 2 && x > 2 && bombs < x*y-9){
            break;
         }
         System.out.println("Bad Parameters");
      }
		GridPanel panel = new GridPanel(x,y,bombs);
		add(panel,BorderLayout.CENTER);
		JToggleButton toggle = new JToggleButton("Mode Select");
		toggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.changeMode();
			}
		});
		add(toggle, BorderLayout.SOUTH);
	}
	
}
