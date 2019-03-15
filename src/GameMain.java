import java.io.IOException;

public class GameMain {
   
	public static void main(String [] args) throws IOException{
		
	// GameField gamefield= new GameField(7,8,"###   ### # ####### ### ### ####  #### ####### #### ####");
	//	test t = new test();
		
		java.io.DataInputStream in = new java.io.DataInputStream(System.in);
		World w = new World();
		System.out.println(w);
	 
		
		String key = in.readLine();

		while(key != "q") {
		w.keyPressed(key);
		System.out.println(w);
		key = in.readLine();
	}	
	}
	
}
