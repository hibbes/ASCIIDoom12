public class GameTile {

	public Position position;

	public GameTile(Position position){
		this.position = position;
	}
	public GameTile(){
		this(new Position(0,0));
	}
	
	public String draw(String s, int worldLength) {
		int index = position.toIndex( worldLength );
		char[] chars = s.toCharArray();
		chars[index] = this.toString().charAt(0);
		return new String(chars);
	}
}
