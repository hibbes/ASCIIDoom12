public class GameTile {

	private Position position;

	public GameTile(Position position){
		this.position = position;
	}
	public GameTile(){
		this(new Position(0,0));
	}
}
