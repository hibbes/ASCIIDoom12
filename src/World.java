
public class World {
		public GameField field;
		public Player player;
				
		public World() {
			field = new GameField(3, 5, "#### ## ## ####");
			player = new Player();
			player.position.x=1;
			player.position.y=1;
 }
}