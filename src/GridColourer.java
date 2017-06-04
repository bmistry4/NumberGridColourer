import java.awt.Color;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;

public class GridColourer implements Runnable{
	private HashMap<Integer, JButton> cellMap;
	private Set<Integer> outputs;
	private Color col;
	
	public GridColourer(HashMap<Integer, JButton> cellMap, Set<Integer> outputs, Color col) {
		this.cellMap = cellMap;
		this.outputs = outputs;
		this.col = col;
	}

	@Override
	public void run() {
		for(int num : outputs){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cellMap.get(num).setBackground(col);
		}
		
	}

}
