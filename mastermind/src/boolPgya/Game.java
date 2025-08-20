package boolPgya;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Game {

	static Logic gameLogic;
	static Graphic graphic;

	public Game(Circle[][] reactionBord,JButton[][] guessBord, int attempts, int guesses) {

		this.gameLogic = new Logic(guessBord, reactionBord,attempts,guesses);

		this.graphic = new Graphic(guessBord, reactionBord);// ליצור מחלקה גרפית ולשלוח אליה את המחלקה הלוגית
	}


	public static void start() {

		JFrame startFrame = new JFrame();
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setLayout(null);
		startFrame.setSize(500,500);
		startFrame.setResizable(false);// פותח את החלון במרכז המסך במחשב


		//  פאנל התחלה
		// שורה ראשונה- כותרת, שורה שניה- כפתור להוראות משחק, שורה שלישית- פאנל בחירה, שורה רביעית - כפתור התחלה
		JPanel  mainPanel  = new JPanel(new GridBagLayout()); // ארבע שורות ובלי עמודות
		mainPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // התחשבות בעברית כדי שלא אצטרך להגדיר את זה עבור כל רכיב בנפרד
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setBounds(30, 15, 430, 430);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		// כותרת המשחק
		JLabel title = new JLabel("❀ ברוכים הבאים למשחק בול פגיעה! ❀", SwingConstants.CENTER);

		Font currentFont = title.getFont();
		title.setFont(currentFont.deriveFont(16f));
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(title,gbc );// נכנס לשורה הראשונה

		// כפתור הוראות
		JButton Instructions = new JButton("איך משחקים?");
		Instructions.setFont(new Font("Arial", Font.PLAIN, 20));
		Instructions.setBackground(Color.GRAY);
		Instructions.setPreferredSize(new Dimension(300, 60));
		Instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openInstructions(mainPanel);
			}
		});
		gbc.gridy=1;
		gbc.gridwidth=2;
		gbc.fill = GridBagConstraints.NONE;
		mainPanel.add(Instructions,gbc);// נכנס לשורה שניה



		// נסיונות לבחירה
		SpinnerNumberModel modelAttempts = new SpinnerNumberModel(7, 7, 11, 1);// ערך התחלתי 7 ערך מינימלי 7 ערך מקסימלי 12
		JSpinner spinnerAttempts = new JSpinner(modelAttempts);


		// מספר ניחושים לבחירה
		SpinnerNumberModel modelGuesses = new SpinnerNumberModel(4, 3 , 5, 1); // ערך התחלתי 4 ערך מינימלי 3 ערך מקסימלי 5
		JSpinner spinnerGuesses = new JSpinner(modelGuesses);

		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridwidth=1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(new JLabel("הזן מספר נסיונות"),gbc);
		gbc.gridx=1;	
		mainPanel.add(new JLabel("הזן מספר צבעים לניחוש"),gbc);	 
		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.NONE;
		mainPanel.add(spinnerAttempts,gbc);// כפתור מספר נסיונות
		gbc.gridx = 1;
		mainPanel.add(spinnerGuesses,gbc);// כפתור מספר צבעים לניחוש


		// כפתור ללחיצה להתחלה	
		JButton startButton = new JButton("בוא נתחיל!");
		// מה קורה כשלוחצים על כפתור ההתחלה 
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// יש למשתמש אפשרות להחליט כמה נסיונות יהיו לו וכמה צבעים יהיו לו לנחש
				int Attempts = (int) spinnerAttempts.getValue();
				int Guesses = (int) spinnerGuesses.getValue();

				startFrame.dispose();
				//gameLogic.setAttemptsAndGuesses(Attempts, Guesses);

				JButton[][] guessBord = new JButton[Attempts][Guesses];// יצירת המערך ידנית במחלקה המקשרת

				for(int i=0;i<guessBord.length;i++) {
					for(int j=0;j<guessBord[0].length;j++) {
						guessBord[i][j]=new JButton();
						guessBord[i][j].setEnabled(false);
						if(i==Attempts-1)
							guessBord[i][j].setEnabled(true);

					}
				}

				Circle[][] reaction = CreateReactionBordPanel(Guesses, Attempts, 400 + (55 * (Attempts - 7)));
				//------------------------------------------------------------------
				new Game (reaction,guessBord,Attempts,Guesses );      // התחלת המשחק
				//------------------------------------------------------------------

				gameLogic.generateRandomColors(Guesses);
				graphic.setColorArry(gameLogic.getRandomColors());

				graphic.initializeCheckButton(55 * (Attempts - 7));

				graphic.getCheckButton().addActionListener(eCheck -> { // מה שקורהכשלוחצים על כפתור בדיקה
					gameLogic.checkAndResponse(gameLogic.activeRow(), graphic.getRandomColorsList()); // מעביר 
					SwingUtilities.invokeLater(() -> {
					JPanel panel = graphic.getReactionBordPanel();
						
					    if(panel!= null) {

							panel.getParent().revalidate();
							panel.revalidate();
							panel.repaint();
						}
					    else 
					    	System.out.println("panel didnt make update");
				        
				    });
				});
					
					
					
					
					
					
					
					
					//					SwingUtilities.invokeLater(new Runnable() {
//						@Override
//						public void run() {
//							graphic.getReactionBordPanel().repaint();				           
//							graphic.revalidate();  // עדכון הממשק
//							graphic.getReactionBordPanel().revalidate();  // לאשר מחדש את המצב של הפאנל
//						}
//					});
				

				graphic.display();
			}
		});     


		gbc.gridy = 8;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(startButton,gbc); // נכנס לשורה רביעית

		startFrame.add(mainPanel);
		startFrame.setLocationRelativeTo(null);
		startFrame.setVisible(true);	 

	}
	//-----------------------------------------------------------

	public static Circle[][] CreateReactionBordPanel ( int numGuesses,int numAttempts,int reactionBordLenght) {
		//reactionBordLenght-8*15
		//-----------------------   =   המרחב לציור בכל שורה
		//      numAttempts  

		Circle[][] reactionBord= new Circle[numAttempts][numGuesses];

		int rowHeight = (reactionBordLenght - 30 - (numAttempts - 1) * 20) / numAttempts; // 20px רווח
		int circleSize = 10;

		for (int i = 0; i < numAttempts; i++) {
			for (int j = 0; j < numGuesses; j++) {
				reactionBord[i][j] = new Circle(circleSize, Color.GRAY);
			}
		}

		for (int i = 0; i < numAttempts; i++) {
			int y = reactionBordLenght - 30 - (i * (rowHeight + 20)); // 20px רווח בין קבוצות

			switch (numGuesses) {
			case 3:

				reactionBord[i][0].setPosition(10, y-10);
				reactionBord[i][1].setPosition(25, y-10);
				reactionBord[i][2].setPosition(40, y-10);
				break;

			case 4:
				reactionBord[i][0].setPosition(15, y);
				reactionBord[i][1].setPosition(35, y);
				reactionBord[i][2].setPosition(15, y - 15);
				reactionBord[i][3].setPosition(35, y - 15);
				break;

			case 5:
				reactionBord[i][0].setPosition(15, y-4);
				reactionBord[i][1].setPosition(35, y-4);
				reactionBord[i][2].setPosition(25, y - 15);
				reactionBord[i][3].setPosition(15, y - 26);
				reactionBord[i][4].setPosition(35, y - 26);
				break;

			}
		}


		return reactionBord;  
	}



	public static void openInstructions(JPanel mainPanel) {

		JDialog dialog = new JDialog();
		dialog.setLayout(new BorderLayout());

		JLabel label = new JLabel(
				"<html><body style='text-align: right; direction: rtl;'>"
						+ "❁ברוכים הבאים למשחק בול פגיעה❁!<br><br>"
						+ "במשחק זה המחשב יגריל עבורכם מספר צבעים שונים ונסתרים שאותם תצטרכו לנחש<br><br>"
						+ "בצד שמאל יופיע  תוצאות הניחושים שלכם<br><br>"
						+ "ובצד ימין יופיעו הצבעים שהמחשב מגריל"
						+"<font color='rgb(225, 70, 60)'>⚫</font>"
						+"<font color='rgb(237,150,19)'>⚫</font>"
						+"<font color='rgb(255,200,20)'>⚫</font>"
						+"<font color='rgb(50,230,107)'>⚫</font>"
						+"<font color='rgb(111,183,237)'>⚫</font>"
						+"<font color='rgb(29,63,173)'>⚫</font>"
						+"<font color='rgb(174,111,237)'>⚫</font>"
						+"<font color='rgb(245,132,168)'>⚫</font>"
						+"<br><br>"
						+ "עבור ניחוש של צבע נכון במקום הלא נכון תקבלו כתוצאה צבע לבן<font color='white'>⚫</font><br><br>"
						+ " עבור ניחוש של צבע נכון במקום הנכון תקבלו כתוצאה צבע שחור<font color='black'>⚫</font><br><br>"
						+ "תוכלו להחליט על מספר הנסיונות שתקבלו על מנת לנחש את הצבע<br><br>"
						+ "מאחלים לכם שפע הצלחה ובריאות מקרב ה<font color='rgb(255,54,135)'>❤</font> !" 

	        	    + "</div></html>"
				);

		label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.RIGHT); 
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 30));  // שמאל, למטה 


		JButton okButton = new JButton("סבבה, הבנתי!");

		okButton.setPreferredSize(new Dimension(100,20));
		okButton.addActionListener(e -> dialog.dispose());// הכפתור גורם לחלון הקופץ להעלם ברגע שלוחצים עליו על ידי פעולה שקיימת במחלקת הדיאלוג עצמה 


		JPanel  dialogPanel  = new JPanel(new BorderLayout());
		dialogPanel.setBackground(Color.LIGHT_GRAY);

		dialogPanel.add(label, BorderLayout.CENTER);// מסדר שהטקסט יהיה במרכז הדיאלוג כלומר במרכז החלון הקופץ
		dialogPanel.add(okButton, BorderLayout.SOUTH);


		dialog.add(dialogPanel);
		dialog.pack(); 
		dialog.setMinimumSize(new Dimension(300,250)); // מונע חלון קטן מדי
		dialog.setLocationRelativeTo(mainPanel);
		dialog.setVisible(true);
	}






}
