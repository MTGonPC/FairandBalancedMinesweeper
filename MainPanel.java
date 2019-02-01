import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class MainPanel extends JPanel {
	
	public MainPanel() {
		setLayout(new BorderLayout());
		GridPanel panel = new GridPanel(10,10,3);
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
