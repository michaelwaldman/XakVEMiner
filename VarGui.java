import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class VarGUI extends JFrame {
	private JPanel contentPane;

	public boolean ironSelected;
	public boolean copperSelected;
	public boolean tinSelected;




	
	public VarGUI(final Main ctx) {
		setTitle("Xak's VEast Miner");
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 250, 150);
        setLocationRelativeTo(null);
        
        
  
      
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRocksToMine = new JLabel("Select rocks to mine:");
		lblRocksToMine.setLocation(21, 29);
		lblRocksToMine.setSize(132, 16);
		contentPane.add(lblRocksToMine);

		JCheckBox chckbxIron = new JCheckBox("Iron");
		chckbxIron.setLocation(165, 91);
		chckbxIron.setSize(57, 23);
		chckbxIron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ironSelected = chckbxIron.isSelected();
			}
		});
		contentPane.add(chckbxIron);
		
				JCheckBox chckbxCopper = new JCheckBox("Copper");
				chckbxCopper.setLocation(165, 25);
				chckbxCopper.setSize(77, 23);
				chckbxCopper.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						copperSelected = chckbxCopper.isSelected();
					}
				});
				
						contentPane.add(chckbxCopper);

		JCheckBox chckbxTin = new JCheckBox("Tin");
		chckbxTin.setLocation(165, 60);
		chckbxTin.setSize(52, 23);
		chckbxTin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				tinSelected = chckbxTin.isSelected();

			}
		});
		contentPane.add(chckbxTin);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if (tinSelected) {
					ctx.myID.add(7485);
					ctx.myID.add(7486);
				}
				if (copperSelected) {
					ctx.myID.add(7453);
					ctx.myID.add(7484);
				}
				if (ironSelected) {
					ctx.myID.add(7488);
					ctx.myID.add(7455);
				}
				ctx.startScript = true;
				ctx.setStartScript(true);
				dispose();
				setVisible(false);
			}
		});
		btnStart.setBounds(42, 78, 63, 27);
		contentPane.add(btnStart);
	}

}
