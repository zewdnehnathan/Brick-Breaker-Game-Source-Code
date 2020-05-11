import javax.swing.*;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame obj = new JFrame();
		GamePlay gameplay = new GamePlay();
		obj.setBounds(10,10,700,600);
		obj.setTitle("Java Game Assignment");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
	}

}
