
public class World {
		public GameField field;
		public Player player;
				
		public World() {
			field = new GameField(7,8,"###   ### # ####### ### ### ####  #### ####### #### ####");
			player = new Player();
			player.position.x=1;
			player.position.y=1;
			
		}
			public String toString() {
				String s = field.toString();
				s = player.draw(s,field.width);
				
				String result = "\\ ";
				for( int column = 0; column < field.width; ++column) {
					result += (column % 10);
				}
				result += "\n";
				
				for( int row = 0; row < s.length()/field.width; ++row) {
					result += row%10 + " " + s.substring( row*field.width, (row+1)*field.width) + "\n";
				}
				return result;
			}
			public void keyPressed(String s) {
				if( s.isEmpty() ) {
					return;
				}
				
					switch( s.charAt(0) ) {
					case 'w':
						player.moveUp();
						break;
					case 's':
						player.moveDown();
						break;
					case 'a': 
						player.moveLeft();
						break;
					case 'd':
						player.moveRight();
						break;
					default:
						System.out.println("Key not found");
						break;
				}
					
					
			}
	boolean checkCollision(){
		
	//	player.position.equals(entsprechendes Walltile)
		
		
	}
 }
