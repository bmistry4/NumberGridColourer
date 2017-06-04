import java.awt.Color;

public class Main {
	public static void main(String[] args) {
		Color c = Color.getHSBColor(0.56f, 1.0f, 0.8f);
		
		GridGUI grid = new GridGUI();
		String s = "( 1x )";
		Formula f = new Formula('x', s);
		Formula f1 = new Formula('x', "( 1x )");
		GridColourer g = new GridColourer(grid.getCellMap(), f.getResult(), c);
//		GridColourer g1 = new GridColourer(grid.getCellMap(), f1.getResult(), Color.green);
		new Thread(g).start();
//		new Thread(g1).start();
	}
	
}
