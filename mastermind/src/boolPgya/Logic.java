package boolPgya;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Logic {

	private int[] randomColors; // מערך צבעים שהמחשב מגריל למשתמש 
	private  JButton [][]guess;//  מערך דו ממדי שמסדר לי מקומות לניחושים העתידיים של המשתמש 
	private  Circle [][]reaction;

	private int numAttempts;
	private int numGuesses;

	public Logic(JButton[][] guessBord, Circle[][] reactionBord, int attempts, int guesses) {  

		this.guess= guessBord;
		this.reaction= reactionBord;
		this.numAttempts=attempts;
		this.numGuesses=guesses;
	}


	//----------------------------------------------------- פעולות שקשורות לבדיקת הניחושים 

	public void generateRandomColors(int numGuesses) {

		randomColors =new int[numGuesses];// מערך בגודל הבחירה של כמות הצבעים שהמשתמש רוצה לנחש במשחק 


		Queue<Integer> queue = new LinkedList<>(); //תור של מספרים
		for(int i=1;i<9;i++)
			queue.add(i);

		for(int i=0;i<numGuesses;i++) {

			int randomNum = (int)(Math.random() * 8)+1;
			while(queue.contains(randomNum)==false)// כל עוד הערך שהגרלתי לא נמצא בתור אני אצטרך שהוא ימשיך להגריל לי מספרים
				randomNum = (int)(Math.random() * 8)+1;
			randomColors[i]=randomNum;// מערך המספרים מ1-8 של מספרים שמייצגים צבעים ללא כפילויות

			while (queue.peek()!=randomNum)//מוציא את הערך שכבר השתמשנו בו כדי שלא יהיו כפילויות
				queue.add(queue.poll());
			queue.poll();
		}	
		//System.out.println(Arrays.toString(randomColors));
	}




	public void checkAndResponse (int row, List<JPanel> randomColors ) { // פעולה שמקבלת את השורה הנוכחית ומשנה את התגובה בהתאם כדי לדעת מה התשובות של הניחוש שלי 

		if (row >=0 && allSquaresAreFilled(row) ){// בשלושה מצבים הבדיקה לא תעשה: 1 כשיש ניצחון, 2 כשמספר הנסיונות נגמר, 3 כשלא כל המשבצות התמלאו בניחושים
			// יש אישור לבצע בדיקה
			Queue<Integer> queue = new LinkedList<>(); //תור של מספרים
			for(int i=0;i<numGuesses;i++)
				queue.add(i);// אני רוצה שכשיהיה לי את הלוח תוצאות לא תהיה חוקיות של סדר התוצאות כדי שלא יוכלו להבדיל ולרמות אז אני צריכה מיקומים רנדומלים לתוצאות וללא כפילויות

			for (int i=0; i<numGuesses;i++) {
				if ( returnReactionForOneButton(guess[row][i], i) == 1){// התוצאה של הניחוש הזה הוא פגיעה כלומר הוא בחר צבע נכון במקום לא נכון 

					int randomPlaceInReactionBord = (int)(Math.random() * (numGuesses + 1));
					while(queue.contains(randomPlaceInReactionBord)==false)
						randomPlaceInReactionBord = (int)(Math.random() * (numGuesses + 1));

					this.reaction[numAttempts-1-row][randomPlaceInReactionBord].setColor(Color.WHITE);

					while (queue.peek()!=randomPlaceInReactionBord)//מוציא את הערך שכבר השתמשנו בו כדי שלא יהיו כפילויות
						queue.add(queue.poll());
					queue.poll();
				}	
				else
					if ( returnReactionForOneButton(guess[row][i], i) == 2) { // הפירוש של זה זה שהוא ניחש את הצבע הנכון במקום הנכון ועל זה הוא מקבל תוצאה של צבע שחור כלומר בול פגיעה 
						int randomPlaceInReactionBord = (int)(Math.random() * (numGuesses + 1));
						while(queue.contains(randomPlaceInReactionBord)==false)
							randomPlaceInReactionBord = (int)(Math.random() * (numGuesses + 1));

						this.reaction[numAttempts-1-row][randomPlaceInReactionBord].setColor(Color.BLACK);

						while (queue.peek()!=randomPlaceInReactionBord)//מוציא את הערך שכבר השתמשנו בו כדי שלא יהיו כפילויות
							queue.add(queue.poll());
						queue.poll();
					}	
			}
			
			// אחרי עידכון פאנל התוצאות אני צריכה שהוא יסגור את האפשרות לבחירה לאותו פאנל
			for (int i=0; i<numGuesses;i++) {
				guess[row][i].setEnabled(false);
			}

			//  בדיקה אם המשתמש ניצח תזכיר לעצמי הפעולה אוטומטית סוגרת את השורה הנוכחית אם המשתמש ניצח 
			if (winning (row)) {
				// חשיפת הצבעים האמיתיים 
				for (int i=0; i< randomColors.size(); i++)
					randomColors.get(i).setVisible(true);
				// התאמת תגובה מתאימה
				for (int i=0; i<numGuesses;i++) 
					reaction[numAttempts-1-row][i].setColor(Color.BLACK);
				// הצגת חלון מתאים
				JPanel winningPanel = new JPanel(new BorderLayout());
				JLabel text =new JLabel("ברכותינוווו  ניצחת!!" , SwingConstants.CENTER);
				text.setFont(new Font("Arial", Font.BOLD, 24));
				winningPanel.add(text, BorderLayout.NORTH);
				winningPanel.add(new JLabel(new ImageIcon(getClass().getResource("/Folder/winningGifIcon.gif"))), BorderLayout.CENTER); 

				JOptionPane.showMessageDialog(
						null,
						winningPanel,
						"חלון ניצחון",
						JOptionPane.PLAIN_MESSAGE
						);

			}
			else {
				// בדיקה אם אפשר לפתוח שורה חדשה לבחירה או שהמשתמש סיים את הנסיונות שלו
				int futerAmount=row-1;
				if (futerAmount >=0) 
				{
					row--;
					System.out.println("המשתמש יכול להמשיך לשחק נשארו לו ניסיונות" + row );
					for (int i=0; i<numGuesses;i++) {
						guess[row][i].setEnabled(true);
					}
				}
				else {
					// חשיפת הצבעים האמיתיים 
					for (int i=0; i< randomColors.size(); i++)
						randomColors.get(i).setVisible(true);
					
					System.out.println("המשחק נגמר כי נגמרו לך הנסיונות לנחש ולא הצלחת לנחש");	
					
					JPanel losingPanel = new JPanel(new BorderLayout());
					JLabel text =new JLabel("אי אי אי, לא נורא חבר, ישנם צרות גדולות יותר.." , SwingConstants.CENTER);
					text.setFont(new Font("Arial", Font.BOLD, 24));
					losingPanel.add(text, BorderLayout.NORTH);
					losingPanel.add(new JLabel(new ImageIcon(getClass().getResource("/Folder/losingGifIcon.gif"))), BorderLayout.CENTER); 

					JOptionPane.showMessageDialog(
							null,
							losingPanel,
							"חלון הפסד",
							JOptionPane.PLAIN_MESSAGE
							);

				}
			}

		}
	}
	// ------------- פעולות עזר לפעולת בדיקה

	public int returnReactionForOneButton(JButton x, int place) { //הפעולה תחזיר 1 במצב של פגיעה 2 במצב של בול פגיעה ו0 במצב שלא ניחש נכון בשום מצב 
		if (ConvertColorToNumber(x) == randomColors[place])
			return 2;
		for(int i=0; i<randomColors.length ;i++)
			if ( ConvertColorToNumber(x)==randomColors[i])
				return 1;
		return 0; 
	}

	public boolean allSquaresAreFilled(int currentRow) {
		for (int i=0;i<guess[0].length; i++)
			if (guess[currentRow][i].getBackground()==Color.lightGray)
				return false; //אין אישור לבצע בדיקה על שורת ניחושים אלו מכיוון שלא כולם מלאים בבחירות
		return true;	// יש אישור לבצע בדיקה 
	}

	public boolean winning (int row) {// מחזיק האם ניחש את הכל נכון
		for(int i=0;i<guess[0].length;i++)
			if(ConvertColorToNumber(guess[row][i])!=randomColors[i]) 
				return false; // המשתמש לא ניצח עדיין

		for(int i=0;i<guess[0].length;i++)
			guess[row][i].setEnabled(false); // אם המשתמש ניצח אני צריכה לנתק את האפשרות לגשת למשבצת הנוכחית 

		return true; // המשתמש ניצח		
	}


	public  int activeRow() {
		// col= תור row= שורה

		for (int row = 0; row < guess.length; row++) 
			if (guess[row][0].isEnabled()) {
				//System.out.println("השורה הפעילה עכשיו היא שורה "+row );
				return row; // מחזיר את השורה הפעילה הנוכחית שעליה אני מבצעת את המשחק עכשיו
			}
		return -1;// אם לא נמצאת שורה כזאת זה אמור ליקרות כשהמשחק נגמר 
	} 

	public int ConvertColorToNumber (JButton x) {// מקבל כפתור ומחזיר את המספר שמייצג את הצבע שלו
		Color color=x.getBackground();
		if (color.equals(new Color(225, 70, 60)))
			return 1;
		if (color.equals(new Color(237,150,19)))
			return 2;
		if (color.equals(new Color(255,200,20)))
			return 3;
		if (color.equals(new Color(50,230,107)))
			return 4;
		if (color.equals(new Color(111,183,237)))
			return 5;
		if (color.equals(new Color(29,63,173)))
			return 6;
		if (color.equals(new Color(174,111,237)))
			return 7;

		return 8;

	} 

	// set -ו get פעולות   ------------------------------------
	public int[]  getRandomColors() {
		return this.randomColors;
	}


	public Circle [][] getReaction() {
		return this.reaction;
	}


	public JButton [][] getGuess() {
		return this.guess;
	}

	public int getAttempts() {
		return this.numAttempts;
	}

	public int getGuesses() {
		return this.numGuesses;
	}


	public void setGuessArray(JButton[][] guessBord) {
		this.guess =guessBord;

	}


	public void setReactionArray(Circle[][] reaction) {
		this.reaction =reaction;		
	}








}
