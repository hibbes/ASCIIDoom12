public class EmptyTile extends GameTile{

	public EmptyTile(Position position) {
		super(position);
		}
	
	public EmptyTile() {
		super();
		}

	public String toString(){
		return " ";
	}
}