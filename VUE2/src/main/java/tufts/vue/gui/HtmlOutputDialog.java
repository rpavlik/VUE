package tufts.vue.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import tufts.vue.VUE;
import tufts.vue.VueResources;

public class HtmlOutputDialog extends JDialog implements KeyListener, ActionListener {
/*
	htmloutput.title=HTML Output Settings
htmloutput.pixel.title=Pixel Dimensions
htmloutput.ok=OK
htmloutput.cancel=Cancel
htmloutput.width=Width
htmloutput.heigh=Height
htmloutput.pixels=Pixels
 */
	private static final String TITLE = VueResources.getString("htmloutput.title");
	private final JLabel heightPixels = new JLabel(VueResources.getString("htmloutput.pixels"));
	private final JLabel widthPixels = new JLabel(VueResources.getString("htmloutput.pixels"));
	private final JButton okButton = new JButton(VueResources.getString("htmloutput.ok"));
	private final JButton cancelButton = new JButton(VueResources.getString("htmloutput.cancel"));
	private final JLabel widthLabel = new JLabel(VueResources.getString("htmloutput.width"));
	private final JLabel heightLabel = new JLabel(VueResources.getString("htmloutput.height"));
	private final JLabel formatLabel = new JLabel(VueResources.getString("htmloutput.format"));
	private final JComboBox formatBox = new JComboBox();
	private final JTextField widthField = new JTextField();
	private final JTextField heightField = new JTextField();
	private final double widthRatio;
	private final double heightRatio;
	private final double width;
	private final double height;
	private int returnVal = -1;

	public HtmlOutputDialog()
	{
		 super(VUE.getDialogParentAsFrame(),TITLE,true);
		 buildUI();
		 setModal(true);
         setResizable(false);  
         setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
         width = VUE.getActiveMap().getPaintBounds().getWidth();
         height = VUE.getActiveMap().getPaintBounds().getHeight();
         widthField.setText((Integer.valueOf((int) width)).toString());
         heightField.setText((Integer.valueOf((int) height)).toString());
         widthRatio = width / height;
         heightRatio = height / width;
        // System.out.println("w;"+width + " h;" + height + " widthRatio;" + widthRatio+" heightRatio;"+heightRatio);
         addListeners();
         Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
         Dimension labelSize = getPreferredSize();
         setLocation(screenSize.width/2 - (labelSize.width/2),
                       screenSize.height/2 - (labelSize.height/2));


	}
	

	private void addListeners()
	{
		widthField.addKeyListener(this);
		heightField.addKeyListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}
	
	private void buildUI()
	{
		 JPanel panel = new JPanel(new MigLayout("fill","[80][80][80]","[20]"));
		 JLabel dimLabel = new JLabel(VueResources.getString("htmloutput.dimensions"));
		 panel.add(dimLabel,"gaptop 3");
		 panel.add(new JSeparator(),       "growx, wrap, gaptop 3");
		 panel.add(widthLabel,"gap 10");
		 panel.add(widthField,"growx");
		 panel.add(widthPixels,"gap 10, wrap");
		 panel.add(heightLabel,"gap 10");
		 panel.add(heightField,"growx");		 
		 panel.add(heightPixels,"gap 10, wrap");
		 panel.add(formatLabel,"gap 10");
		 formatBox.addItem("PNG");		 
		 formatBox.addItem("JPEG");
		 panel.add(formatBox,"gap 10, wrap");
		 
		
		 JPanel p = new JPanel(new MigLayout(""));
		 p.add(cancelButton, "sg, tag cancel");
		 p.add(okButton, "sg, tag ok");
		 
		 panel.add(p,"gapbottom 1, span 3, align right");
		 this.getContentPane().add(panel);
		 this.pack();

	}
	
	public String getFormat()
	{
		return formatBox.getSelectedItem().toString().toLowerCase();
	}
	
	public double getScale()
	{
		String wText = widthField.getText();
		Double w = new Double(wText);
		
		double ratio = w /width;
		
		return ratio;
	}
	
	public static void main(String[] args)
	{
		HtmlOutputDialog tml = new HtmlOutputDialog();
		tml.setVisible(true);
	}

	public void keyPressed(KeyEvent e) {
		
		
	
		
	}

	public void keyReleased(KeyEvent e) {
		if (e.getSource().equals(widthField))
		{
			String s = widthField.getText();
			Integer i = null;
			
			try
			{
				i = Integer.valueOf(s);
			}
			catch(Exception e2)
			{
				i = (int) width;
			}
			//System.out.println(i.intValue());
			int newHeight = (int)(i * heightRatio);
			heightField.setText((Integer.valueOf(newHeight)).toString());
		}
		else if (e.getSource().equals(heightField))
		{
			String s = heightField.getText();
			Integer i = null;
			
			try
			{
				i = Integer.valueOf(s);
			}
			catch(Exception e2)
			{
				i = (int) height;
			}
			//System.out.println(i.intValue());
			int newWidth = (int)(i * widthRatio);
			widthField.setText((Integer.valueOf(newWidth)).toString());
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public int getReturnVal()
	{
		return returnVal;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(okButton))
			returnVal = 1;
		
		setVisible(false);
		dispose();
		
	}
}
