package boolPgya;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Graphic extends JPanel {
	private JPanel reactionBordPanel;
	
	private List<JPanel> randomColorsPanels;
	
	private int [] randomNums;
	private JButton [][] guessBord;
	private Circle [][] reactionBord;
	private JButton selectedButton = null;
	private JButton checkButton;


	public Graphic(JButton [][] guessBord,Circle[][] reactionBord) {
		this.guessBord = guessBord;
		this.reactionBord= reactionBord;
		
		 this.reactionBordPanel = new JPanel() {
				protected void paintComponent(Graphics g) {

					super.paintComponent(g);		            

					if (reactionBord != null) {
						for (int i = 0; i < reactionBord.length; i++) {
							for (int j = 0; j < reactionBord[i].length; j++) {
								if (reactionBord[i][j] != null) {
									reactionBord[i][j].draw(g);
								}
							}
						}
					} else {
						System.out.println("reactionBord הוא null");
					}
				}
			};

			this.randomColorsPanels = new ArrayList<>(); 
			this.reactionBordPanel.setBounds(10, 10, 60, 400 + (55 * (reactionBord.length - 7)));

			this.reactionBordPanel.setBackground(Color.GRAY);
			this.reactionBordPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));		   

			
	}


	public void display() {
		int x= numOfGuessesr();
		int y = numOfAttempts (); // בגלל שהערך המינימלי הוא 7 ואי אפשר לרדת ממנו אז אני רק אוסיף בחישוב של מספר הנסיונות שהמשתמש בחר פחות 7 כפול 55 עבור כל פאנל שארצה להאריך 

		// חלון גודל 530*600	 
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(530+x, 600+y);
		frame.setLayout(null);// ביטול מנהל פריסה כדי למקם ידנית את הרכיבים 
		// frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		// הגדרת גודל החלון והפיכתו לנראה הם שני שלבים שאני כותבת בסוף כל הדבר הזה		 
		// יצירת פאנל ראשי 
		JPanel mainPanel = new JPanel();//פאנל ראשי
		mainPanel.setBounds(30,50,365+x,500+y);
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setLayout(null);

		JPanel guessBordPanel = new JPanel();//פאנל ניחושים
		guessBordPanel.setBounds(80,10,230+x,400+y);
		guessBordPanel.setBackground(Color.GRAY);
		guessBordPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		guessBordPanel.setLayout( new FlowLayout(FlowLayout.CENTER, 10, 15));// כל משבצת 40 ורווחים של 


		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //וזה לא נתון שמתקבל עדיין בבנאי x, y איתחול של הפאנל הזה בפעולה אחרת ולא בבנאי של המחלקה כי הוא דורש את ערך הגובה והרוחב 
		JPanel  randomColors = new JPanel();// רצף רנדומלי של צבעים
	    randomColors.setBounds(80,430+y,230+x,60);
	    randomColors.setBackground(Color.lightGray);
	    randomColors.setLayout(null);


		JPanel panelcolors = new JPanel();// פאנל צבעים
		panelcolors.setBounds(410+x,50+y,80,500);
		panelcolors.setBackground(Color.GRAY);
		panelcolors.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 11));



		//--------------------------------------------

		//צבעים

		JButton red= new JButton();// אדום
		red.setBackground(new Color(225,70,60));
		red.setPreferredSize(new Dimension(50, 50)); 
		red.addActionListener(e -> {
			if (selectedButton != null && colorAlreadyguessed(new Color(225,70,60))) {
				selectedButton.setBackground(red.getBackground()); // משנה את צבע הכפתור שנבחר לצבע של הפאנל האדום
			}
		});

		JButton orange= new JButton();// כתום
		orange.setBackground(new Color(237,150,19));
		orange.setPreferredSize(new Dimension(50, 50));
		orange.addActionListener(e -> {
			if (selectedButton != null && colorAlreadyguessed(new Color(237,150,19)))  {
				selectedButton.setBackground(orange.getBackground()); // משנה את צבע הכפתור שנבחר לצבע של הפאנל האדום
			}
		});

		JButton yellow= new JButton();// צהוב
		yellow.setBackground(new Color(255,200,20));
		yellow.setPreferredSize(new Dimension(50, 50)); 
		yellow.addActionListener(e -> {
			if (selectedButton != null && colorAlreadyguessed(new Color(255,200,20)))  {
				selectedButton.setBackground(yellow.getBackground()); // משנה את צבע הכפתור שנבחר לצבע של הפאנל האדום
			}
		});

		JButton green= new JButton();// ירוק
		green.setBackground(new Color(50,230,107));
		green.setPreferredSize(new Dimension(50, 50)); 
		green.addActionListener(e -> {
			if (selectedButton != null && colorAlreadyguessed(new Color(50,230,107)))  {
				selectedButton.setBackground(green.getBackground()); // משנה את צבע הכפתור שנבחר לצבע של הפאנל האדום
			}
		});

		JButton blue= new JButton();// כחול
		blue.setBackground(new Color(111,183,237));
		blue.setPreferredSize(new Dimension(50, 50));
		blue.addActionListener(e -> {
			if (selectedButton != null && colorAlreadyguessed(new Color(111,183,237)))  {
				selectedButton.setBackground(blue.getBackground()); // משנה את צבע הכפתור שנבחר לצבע של הפאנל האדום
			}
		});

		JButton darkblue= new JButton();// כחול כהה
		darkblue.setBackground(new Color(29,63,173));
		darkblue.setPreferredSize(new Dimension(50, 50));
		darkblue.addActionListener(e -> {
			if (selectedButton != null && colorAlreadyguessed(new Color(29,63,173)))  {
				selectedButton.setBackground(darkblue.getBackground()); // משנה את צבע הכפתור שנבחר לצבע של הפאנל האדום
			}
		});

		JButton purple= new JButton();// סגול
		purple.setBackground(new Color(174,111,237));
		purple.setPreferredSize(new Dimension(50, 50)); 
		purple.addActionListener(e -> {
			if (selectedButton != null && colorAlreadyguessed(new Color(174,111,237)))  {
				selectedButton.setBackground(purple.getBackground()); // משנה את צבע הכפתור שנבחר לצבע של הפאנל האדום
			}
		});

		JButton pink= new JButton();// ורוד
		pink.setBackground(new Color(245,132,168));
		pink.setPreferredSize(new Dimension(50, 50)); 
		pink.addActionListener(e -> {
			if (selectedButton != null && colorAlreadyguessed(new Color(245,132,168)))  {
				selectedButton.setBackground(pink.getBackground()); // משנה את צבע הכפתור שנבחר לצבע של הפאנל האדום
			}
		});


		panelcolors.add(red);
		panelcolors.add(orange);
		panelcolors.add(yellow);
		panelcolors.add(green);
		panelcolors.add(blue);
		panelcolors.add(darkblue);
		panelcolors.add(purple);
		panelcolors.add(pink);

		//-----------------------------------------------
		//צבעים רנדומלים 
		// אם המינימום שלי הוא שלושה כפתורים אז מה שבטוח שיהיו לי שלושה כפתורים ואז אם יהיו לי עוד זה תלוי בכמות הניחושים שהמשתמש בחר 
		// בנוסף אני לא רוצה שהוא יצור לי עד שני כפתורי סתם אז הוספתי משפטי תנאי 
		JPanel random1 = new JPanel();
		random1.setBounds(10, 7, 45, 45);
		randomColor(random1, randomNums[0]);
		random1.setVisible(false);
		this.randomColorsPanels.add(random1);

		JPanel random2 = new JPanel();
		random2.setBounds(65, 7, 45, 45);
		randomColor(random2, randomNums[1] );
		random2.setVisible(false);
		this.randomColorsPanels.add(random2);

		JPanel random3 = new JPanel();
		random3.setBounds(120, 7, 45, 45);
		randomColor(random3, randomNums[2] );
		random3.setVisible(false);
		this.randomColorsPanels.add(random3);

		if (guessBord[0].length==4) {
			JPanel random4 = new JPanel();
			random4.setBounds(175, 7, 45, 45);
			randomColor(random4, randomNums[3]);
			random4.setVisible(false);
			randomColors.add(random4);
			this.randomColorsPanels.add(random4);

		}
		else
			if (guessBord[0].length==5)
			{
				JPanel random4 = new JPanel();
				random4.setBounds(175, 7, 45, 45);
				randomColor(random4, randomNums[3]);
				random4.setVisible(false);
				randomColors.add(random4);
				this.randomColorsPanels.add(random4);


				JPanel random5 = new JPanel();
				random5.setBounds(230, 7, 45, 45);
				randomColor(random5, randomNums[4]);
				random5.setVisible(false);
				randomColors.add(random5);
				this.randomColorsPanels.add(random5);

			}
		// כפתורים שבכל מקרה יתווספו
		randomColors.add(random1);
		randomColors.add(random2);
		randomColors.add(random3);


		randomColors.setVisible(true);	 // חשובבב בשביל להסתיר את הצבעים - כל הפואנטה של המשחק

		// כפתור הפח
		ImageIcon trashIcon = new ImageIcon(getClass().getResource("/Folder/trash_icon.png"));
		Image image = trashIcon.getImage();
		Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JButton clear = new JButton(scaledIcon);
		clear.setBounds(310+x, 430+y, 60, 60); 
		clear.setBorder(BorderFactory.createEmptyBorder()); // הסרת מסגרת
		clear.setContentAreaFilled(false); // הסרת רקע הכפתור
		clear.addActionListener(e -> {
			for(int i=0; i<  guessBord[0].length;i++) { 
				guessBord[activeRow()][i].setBackground(Color.lightGray);
			}
		});

		CreateGuessBordPanel(guessBordPanel);
		mainPanel.add(guessBordPanel);
		mainPanel.add(reactionBordPanel);

		mainPanel.revalidate();
		mainPanel.repaint();
		mainPanel.add(randomColors);
		mainPanel.add(checkButton);

		mainPanel.add(clear);


		frame.add(panelcolors);
		frame.add(mainPanel);
		frame.setVisible(true);
		frame.repaint();
	}


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//כי אם אני אאתחל את הכפתור במחלקה הבונה הוא יציב את המיקום של הכפתור במקום לא נכון והמיקום שלו תלוי בשני הערכים שהמשתמש יכניס 
	public void initializeCheckButton(int y) {
		checkButton = new JButton("✔");
		checkButton.setFont(new Font("✔", Font.PLAIN, 40));
		checkButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		checkButton.setBackground(Color.GRAY);
		checkButton.setBounds(10, 430 + y, 60, 60);
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        	    	                                          
	public void CreateGuessBordPanel(JPanel Panel) {// הפעולה מקבלת את הפאנל הראשי ומוסיפה לו את המשבצות שעליהן מנחשים 

		for (int i = 0; i < guessBord.length; i++) 
			for (int j = 0; j < guessBord[0].length; j++) {
				guessBord[i][j].setPreferredSize(new Dimension(40,40));
				guessBord[i][j].setBackground(Color.lightGray);
				guessBord[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				guessBord[i][j].addActionListener(e -> {
					selectedButton = (JButton) e.getSource();
				});	    			 

				Panel.add(guessBord[i][j]);// נכנס לשורה הראשונה
			}	
	}



	public  int activeRow() {
		// col= תור row= שורה
		for (int row = 0; row < guessBord.length; row++) 
			for (int col = 0; col < guessBord[0].length; col++) 
				if (guessBord[row][col].isEnabled()) 
					return row; // מחזיר את השורה הפעילה הנוכחית שעליה אני מבצעת את המשחק עכשיו		            
		return -1;// אם לא נמצאת שורה כזאת זה אמור ליקרות כשהמשחק נגמר 
	}

	public boolean colorAlreadyguessed (Color color) { // אני רוצה לוודא שאי אפשר לנחש צבע פעמיים אז הפעולה הולכת לשורה הפעילה הנוכחית ובודקת איזה כפתורים נלחצו באיזה צבע
		int currentRow=activeRow();
		for(int j =0; j< guessBord[0].length ; j++ )	
			if(guessBord [currentRow][j].getBackground().equals(color))
				return false;
		return true;

	}


	public void randomColor(JPanel x, int num) {// האלגוריתם מקבל מספר שמייצג לי צבע ואת הכפתור שאני רוצה לצבוע את הרקע שלו ומשנה לי אותו 

		switch(num) {
		case 1:
			x.setBackground(new Color(225,70,60));
			break;
		case 2:
			x.setBackground(new Color(237,150,19));	
			break;
		case 3:
			x.setBackground(new Color(255,200,20));	
			break;
		case 4:	
			x.setBackground(new Color(50,230,107));	
			break;
		case 5:	
			x.setBackground(new Color(111,183,237));
			break;
		case 6:	
			x.setBackground(new Color(29,63,173));
			break;
		case 7:	
			x.setBackground(new Color(174,111,237));
			break;
		default:
			x.setBackground(new Color(245,132,168));
		} 
	}


	public int numOfGuessesr () {
		int x=0;
		switch(guessBord[0].length) {
		case 3:
			x=-55;
			break;
		case 5:
			x=55;
			break;
		}
		return x;
	}


	public int numOfAttempts () {
		int y = 55 * (guessBord.length - 7);;
		return y;
	}








	// set -ו get פעולות   ------------------------------------
	// set -ו get פעולות   ------------------------------------
	public void setColorArry(int[] randomColors) {
		this.randomNums = randomColors; // מעדכן את המערך randomNums עם הצבעים
	}

	public void setguessArry(JButton[][] guess) {
		guessBord=guess;
	}

	public void setReaction(Circle[][] reaction) {
		this.reactionBord = reaction;
	}

	public JButton getCheckButton() {
		return this.checkButton;
	}

	public Circle[][] getReactionBord() {
		return this.reactionBord;
	}


	public JPanel getReactionBordPanel() {
		return this.reactionBordPanel;
	}

	public List<JPanel> getRandomColorsList() {
		return this.randomColorsPanels;
	}
		
}




