public class GameMain {
   
	public static void main(String args[]){
		
	Position position1 = new Position(5,6);
	Position position2 = new Position(5,6);
	WallTile wand = new WallTile(position1);
	
	System.out.println(wand);	
	System.out.println(position1.equals(position2));
	position1.setX(4);
	System.out.println(position1.equals(position2));
	}
	
}
