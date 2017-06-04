

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridGUI extends JFrame{
	private HashMap<Integer, JButton> cellMap;
	
	protected GridGUI(){
		cellMap = new HashMap<Integer, JButton>();
		init();
	}
	
	public void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(10,10));
		JButton cell;
		for(int i=0; i<100; i++){
			cell = new JButton(String.valueOf(i));
			cell.setBackground(Color.WHITE);
			cellMap.put(i, cell);
			contentPane.add(cell);
		}
		
		pack();
		setVisible(true);
	}
	
	public HashMap<Integer, JButton> getCellMap(){
		return cellMap;
	}
	
}
