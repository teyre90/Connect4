import javafx.application.Application;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Arc; 
import javafx.scene.shape.ArcType; 
import javafx.scene.shape.Circle; 
import javafx.scene.text.Font; 
import javafx.scene.text.Text; 
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.File;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button; 
import javafx.scene.layout.Background; 
import javafx.scene.layout.HBox; 
import javafx.scene.layout.VBox; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Arc; 
import javafx.scene.shape.ArcType; 
import javafx.scene.shape.Circle; 
import javafx.scene.text.Font; 
import javafx.scene.text.Text; 
import javafx.stage.Stage; 
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import java.lang.Math;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javax.swing.JOptionPane;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Popup;
import javafx.scene.control.*;

public class Connect4 extends Application{

	String player = "Red";
	Connect4Array newGame = new Connect4Array();
	GridPane gridPane = new GridPane();
	boolean completeGame = false;
    ImageView imageView = new ImageView();	
    boolean singleplayer;
    Group root;
    Scene scene;
    InputStream stream;
    Stage stage;
	Text text;
    
	//Setup GUI Board
	
    public void setupBoard() throws IOException{
		
		
    	//Pane to contain buttons 
    	GridPane gridPane = new GridPane();  
		gridPane.setMinWidth(655);
		gridPane.setMinHeight(575);
	    gridPane.setPadding(new Insets(15,0,10,40));
	 
		//Setup Connect 4 Background *************************
		
		String filePath="Connect4BlankBoard.png";		
		 stream = new FileInputStream(filePath);
		 Image image = new Image(stream);
	      imageView.setImage(image);
	      
	      //Setting the image view parameters
	      imageView.setX(10);
	      imageView.setY(10);
	      imageView.setFitWidth(575);
	      imageView.setPreserveRatio(true);
	      
	      //Add text to tell users whose turn it is
	      text= new Text();
	      text.setText("Red Turn");
	      text.setY(550);
	      text.setX(50);
	      
	      //Setting the Scene object
	       root = new Group(imageView, gridPane, text);
	       scene = new Scene(root, 595, 370);
	
	    
	    
	 
	       //Add all buttons for code
	    
	    for (int i=5; i>=0;i--) {
	    	for (int j=6; j>=0; j--) {
	    		   addButton( root, gridPane,i,j);
	    		   if (i==0) {
	    			RowConstraints row1 = new RowConstraints();
	    			row1.setPercentHeight(17);
	    			gridPane.getRowConstraints().add(row1);
	    		   }
	    	}
	        ColumnConstraints column1 = new ColumnConstraints();
		     column1.setPercentWidth(12.9);
		     gridPane.getColumnConstraints().add(column1);
	    }
	    
	      String[] menuOption1= {"One Player","Two Player"};
	      int userChoice = JOptionPane.showOptionDialog(null, "Select Game Mode", null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,menuOption1 , menuOption1);
	      if (userChoice==0) singleplayer = true;
	      else singleplayer=false;
	}
    
    public void start (Stage stage) throws IOException{
		

    	//Set Scene and Show Stage
	      setupBoard();
	      stage.setTitle("Displaying Image");
	      stage.setScene(scene);
	   
	
	      
	      stage.show();
	   
	     
     
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		System.out.println(System.getProperty("user.dir"));
		
		launch(args);
		
	}

	
	//Add Button For Given i and j values
	public  void addButton( Group rootIn, GridPane gridPane, int iIn, int jIn) {
		
		String ijString =Integer.toString(iIn)+ Integer.toString(jIn); 
		 
		Button Button1 = new Button (ijString);
		Button1.setMinWidth(75);
		Button1.setMinHeight(75);
		Button1.setTranslateX(-20);
		Button1.setTranslateY(-2);
		Button1.setBackground(null);
		Button1.setTextFill(null);
		Button1.setOnAction(e-> fillSpace( rootIn, iIn,jIn) );
		
		gridPane.add(Button1, jIn,5-iIn);
	 
	}
	
	
	//Add counter in correct location
	public  void fillSpace( Group rootIn, int iIn, int jIn) {
		
		 Circle redCircle = new Circle(53+jIn*80, 453-iIn*80, 36);          
		 redCircle.setFill(Color.RED);         
		 redCircle.setStroke(Color.RED);
		 
		 Circle yellowCircle = new Circle(53+jIn*80, 453-iIn*80, 36);          
		 yellowCircle.setFill(Color.YELLOW);         
		 yellowCircle.setStroke(Color.YELLOW);
		
		System.out.println(iIn +" "+ jIn);
		
		//Called when a button is clicked and it is red turn
		if (player=="Red"){
			
			//Only add counter if it is a legal move and the game has not completed
			if (newGame.checkLegalMove(iIn, jIn)&& !completeGame) {
				rootIn.getChildren().add(redCircle); 
				newGame.updateBoard(iIn, jIn,1);
				//If the game now has a winner or board full, stop the game. Otherwise hand turn to yellow
				if(newGame.checkForWinner()==1) stopGame(rootIn, player);
				else if(newGame.checkForWinner()==2) stopGame(rootIn, "none");				
				else {player="Yellow";
				text.setText("Yellow Turn");
				
			//If singleplayer mode is on, call AI 	
				if (singleplayer==true) {
				
					rootIn.getChildren().add(newGame.AIPlayer());
					if (newGame.checkForWinner()==1) stopGame(rootIn, player);
					if (newGame.checkForWinner()==2) stopGame(rootIn, "none");			
					player="Red";
					text.setText("Red Turn");
					}
				}
		
			}
		}
		else {
			
				if (newGame.checkLegalMove(iIn, jIn) && !completeGame) {
					rootIn.getChildren().add(yellowCircle);
					newGame.updateBoard(iIn, jIn,2);
					if(newGame.checkForWinner()==1) stopGame(rootIn, player);
					if(newGame.checkForWinner()==2) stopGame(rootIn, "none");
					player="Red";
					text.setText("Red Turn");
					}
				
			}
		
		

	}
	
	
	//Runs when there is a winner or board is full
	public void stopGame(Group rootIn, String playerIn){
	 completeGame = true;
	 Alert completionAlert = new Alert (AlertType.CONFIRMATION);
	 if (playerIn.equals("none")) completionAlert.setContentText("Game Drawn" );
	 else completionAlert.setContentText(playerIn + " wins" );
	 completionAlert.show();
	 player="Red";
	 newGame.resetBoard();
	 completeGame=false;
	

	 GridPane gridPane = new GridPane();

	 gridPane.setMinWidth(655);
	 gridPane.setMinHeight(575);
	 gridPane.setPadding(new Insets(15,0,10,40));
	
	 completionAlert.setOnCloseRequest(e -> {	rootIn.getChildren().clear();	 
	 rootIn.getChildren().addAll(imageView, gridPane,text)  ; 
	 String[] menuOption1= {"One Player","Two Player"};
     int userChoice = JOptionPane.showOptionDialog(null, "Select Game Mode", null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,menuOption1 , menuOption1);
     if (userChoice==0) singleplayer = true;
     else singleplayer=false;}
	 
			 );

	      imageView.setX(10);
	      imageView.setY(10);
	      imageView.setFitWidth(575);
	      imageView.setPreserveRatio(true);
	

	    for (int i=5; i>=0;i--) {
	    	for (int j=6; j>=0; j--) {
	    		   addButton( rootIn, gridPane,i,j);
	    		   if (i==0) {
	    			RowConstraints row1 = new RowConstraints();
	    			row1.setPercentHeight(17);
	    			gridPane.getRowConstraints().add(row1);
	    		   }
	    	}
	        ColumnConstraints column1 = new ColumnConstraints();
		     column1.setPercentWidth(12.9);
		     gridPane.getColumnConstraints().add(column1);
	    }

	  


	}
}


 class Connect4Array {

	private int board[][]= new int [6][7];
	private int iMin[]= {0,0,0,0,0,0,0};
	
	//Setup array which will contain counters
	Connect4Array(){
		for (int i=0; i<6;i++) {
			for (int j=0; j<7;j++) {
				board[i][j]=0;
			}
		}
		
		
	}
	
	//Prints board array - debugging purposes only
	public void printBoard() {
		System.out.println("\nUpdated Board \n");
		for (int i=5; i>=0;i--) {
			System.out.println("");
			for (int j=6; j>=0;j--) {
				System.out.print(" "+ board[i][j]);
			}
		}
		
		for (int j=0;j<=6;j++) {
			System.out.println(iMin[j]);
		}
		
		
	}
	
	//Updates array with latest move
	public void updateBoard(int iIn, int jIn, int PlayerIn) {
	//	System.out.println("Updated board called");
		if (checkLegalMove(iIn,jIn)==true) {
		board[iIn][jIn]=PlayerIn;
		iMin[jIn]=iIn+1;
		}
		else {
			System.out.println("\nIllegal Move");
		}
	//	printBoard();

	}
	
	public boolean checkLegalMove(int iIn,int jIn) {
		
		//If i (row) between 0 and 5 AND j (column) between 0 and 5 
		// AND (Board[i-1][j] = TRUE OR  i=0
		// AND (Board[i][j] !=TRUE
		
		boolean legalMove;
		 if (iIn==0) {
			 legalMove= iIn>=0 && iIn<=5 && jIn >=0 && jIn<=6  
						&& (board[iIn][jIn] ==0);
		 }
		 else if (iIn>=0 && jIn>=0) {
			 legalMove =  (iIn>=0 && iIn<=5 && jIn >=0 && jIn<=6  
						&& (board[iIn-1][jIn]!=0)
						&& (board[iIn][jIn] ==0));
		 }
		 else legalMove=false;
			 
		return legalMove;
	
	}
	
	public String getUserMove() {
		return "User Move";
	}
	
	public int checkForWinner() {
		
		//Check Horizontal


		for (int i=0;i<=5;i++) {
			for (int j=0; j<=3;j++) {

				if (board[i][j]>0 && board[i][j]==board[i][j+1] && board[i][j]==board[i][j+2]&& board[i][j]==board[i][j+3]) {
					return 1;
					
				}
			}
		}
		
		//Check vertical
		

		for (int i=0;i<=2;i++) {
			for (int j=0; j<=6;j++) {

				if (board[i][j]>0 && board[i][j]==board[i+1][j] && board[i][j]==board[i+2][j]&& board[i][j]==board[i+3][j]) {
					return 1;
					
				}
			}
		}
		
		//Check Diagonal Up-right
		for (int i=0;i<=2;i++) {
			for (int j=0; j<=3;j++) {

				if (board[i][j]>0 && board[i][j]==board[i+1][j+1] && board[i][j]==board[i+2][j+2]&& board[i][j]==board[i+3][j+3]) {
					return 1;
					
				}
			}
		}
	
		//Check Diagonal Up-left
		for (int i=0;i<=2;i++) {
			for (int j=3; j<=6;j++) {

				if (board[i][j]>0 && board[i][j]==board[i+1][j-1] && board[i][j]==board[i+2][j-2]&& board[i][j]==board[i+3][j-3]) {
					return 1;
					
				}
			}
		}
		
		
		if (board[5][0]>0 && board[5][1]>0 && board[5][2]>0 && board[5][3]>0 
				 && board[5][4]>0 && board[5][5]>0 && board[5][6]>0) 
			{System.out.println("Board full");
			return 2;
			}
			
		return 0;
	}
	
	
	public void resetBoard() {
		for (int i=0; i<6;i++) {
			for (int j=0; j<7;j++) {
				board[i][j]=0;
				iMin[j]=0;
			}
		
		}
	}
	
	//This method creates an AI move. Checks for vertical, then horizontal, then diagonal risks/rewards
	//If nothing appropriate found, produce random legal move
	public Circle AIPlayer() {
		
		boolean legal=false;
		Circle yellowCircle;
		int jOut;
		int iOut;
		int randomJ;
		
		//Check vertical
		

		for (int i=0;i<=2;i++) {
			for (int j=0; j<=6;j++) {

				if (board[i][j]>0 && board[i][j]==board[i+1][j] && board[i][j]==board[i+2][j]) {
					iOut=i+3;
					legal=checkLegalMove(iOut,j);
					if (legal==true) {
						updateBoard(iOut,j,2);
						yellowCircle = new Circle(53+j*80, 453-iOut*80, 36);  
						yellowCircle.setFill(Color.YELLOW);         
						 yellowCircle.setStroke(Color.YELLOW);
						 System.out.println("Vertical Check Used");
						 return yellowCircle;
					}
					
				}
			}
		}
		
		// Check Horizontal
		for (int i=0;i<=5;i++) {
			for (int j=0; j<=3;j++) {

				if (board[i][j+1]>0 && board[i][j+1]==board[i][j+2]  && (board[i][j+1]==board[i][j+3]|| board[i][j]==board[i][j+1])) {
			
						jOut=j;	

						legal=checkLegalMove(i,jOut);
						if (legal) {
							updateBoard(i,jOut,2);
							yellowCircle = new Circle(53+jOut*80, 453-i*80, 36); 
							 yellowCircle.setFill(Color.YELLOW);         
							 yellowCircle.setStroke(Color.YELLOW);
							 System.out.println("Horizontal Check Used");
							 return yellowCircle;
						}
						else {
							jOut=j+3; 
							legal=checkLegalMove(i,jOut);
							if (legal) {
								updateBoard(i,jOut,2);
								yellowCircle = new Circle(53+jOut*80, 453-i*80, 36); 		
								 yellowCircle.setFill(Color.YELLOW);         
								 yellowCircle.setStroke(Color.YELLOW);
								 System.out.println("Horizontal Check Used");
								 return yellowCircle;
								
							}
						}
					
				}
		
			}
		}
		

		//Check Diagonal Up-right
		for (int i=0;i<=2;i++) {
			for (int j=0; j<=3;j++) {

				if (board[i][j]>0 && board[i][j]==board[i+1][j+1] && board[i][j]==board[i+2][j+2]) {
					 iOut=i+3;
					 jOut=j+3;
					legal = checkLegalMove(iOut,jOut);
					if (legal) {
						updateBoard(iOut,jOut,2);
						yellowCircle = new Circle(53+jOut*80, 453-iOut*80, 36); 		
						 yellowCircle.setFill(Color.YELLOW);         
						 yellowCircle.setStroke(Color.YELLOW);
						 System.out.println("Up-diagnoal Check Used");
						 return yellowCircle;
					}
				}
			}
		}
	
		//Check Diagonal Up-left
		for (int i=0;i<=2;i++) {
			for (int j=3; j<=6;j++) {

				if (board[i][j]>0 && board[i][j]==board[i+1][j-1] && board[i][j]==board[i+2][j-2]) {
					iOut=i+3;
					jOut=j-3;
					legal = checkLegalMove(iOut,jOut);
					if (legal) {
						updateBoard(iOut,jOut,2);
						yellowCircle = new Circle(53+jOut*80, 453-iOut*80, 36); 		
						 yellowCircle.setFill(Color.YELLOW);         
						 yellowCircle.setStroke(Color.YELLOW);
						 System.out.println("Up-diagnoal Check Used");
						 return yellowCircle;
					}
					
				}
			}
		}
		
		
		
		//Random position
		
			do{
			randomJ  =(int) (Math.random()*7);	
			System.out.println("iMin:" +iMin[randomJ] + "  randomJ: "+ randomJ);
			yellowCircle = new Circle(53+randomJ*80, 453-iMin[randomJ]*80, 36);  
			legal=checkLegalMove(iMin[randomJ],randomJ);
			
		
			}while(legal==false);
		

			updateBoard(iMin[randomJ],randomJ,2);
			 yellowCircle.setFill(Color.YELLOW);         
			 yellowCircle.setStroke(Color.YELLOW);
			 System.out.println("Random position used");
			 return yellowCircle;
		
		} 
		
	
		
	}
	

