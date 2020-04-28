
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class InsertCandidate extends Frame 
{
	Button insertCandidateButton;
	TextField cidText, useridText, aadharnoText, symbolText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	public InsertCandidate() 
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
	}

	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","swetha","vasavi");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	public void buildGUI() 
	{		
		insertCandidateButton = new Button("Insert Candidate");
		insertCandidateButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{				  
				  String query= "INSERT INTO candidates VALUES(" + cidText.getText() + ", " + useridText.getText() + "," + aadharnoText.getText() + "," + "'" + symbolText.getText() + "')";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  displaySQLErrors(insertException);
				}
			}
		});

	
		cidText = new TextField(15);
		useridText = new TextField(15);
		aadharnoText = new TextField(15);
		symbolText = new TextField(15);

		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Candidate ID:"));
		first.add(cidText);
		first.add(new Label("User id:"));
		first.add(useridText);
		first.add(new Label("Aadhar No.:"));
		first.add(aadharnoText);
		first.add(new Label("Symbol:"));
		first.add(symbolText);
		first.setBounds(125,90,200,100);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(insertCandidateButton);
        second.setBounds(125,220,150,100);         
		
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(125,320,300,200);
		
		setLayout(null);

		add(first);
		add(second);
		add(third);
	    
		setTitle("New candidate Creation");
		setSize(500, 600);
		setVisible(true);
	}

	

	private void displaySQLErrors(SQLException e) 
	{
		errorText.append("\nSQLException: " + e.getMessage() + "\n");
		errorText.append("SQLState:     " + e.getSQLState() + "\n");
		errorText.append("VendorError:  " + e.getErrorCode() + "\n");
	}

	

	public static void main(String[] args) 
	{
		InsertCandidate cand = new InsertCandidate();

		cand.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		cand.buildGUI();
	}
}