import java.io.IOException;
import java.util.Scanner;

public class GameMain {
   
	public static void main(String [] args) throws IOException{
		
	// GameField gamefield= new GameField(7,8,"###   ### # ####### ### ### ####  #### ####### #### ####");
	//	test t = new test();
		
		Scanner scanner = new Scanner(System.in);
		World w = new World();
		System.out.println(w);
	 
		
		String key = scanner.nextLine();

		while(!"q".equals(key)) {
		w.keyPressed(key);
		System.out.println(w);
		if (scanner.hasNextLine()) {
		    key = scanner.nextLine();
		} else {
		    break;
		}
	}	
	}
	
}
