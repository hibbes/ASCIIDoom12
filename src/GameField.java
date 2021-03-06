public class GameField {

	public GameTile[][] Level;
	public int width;
	public int height;
	
	
	public GameField(int width, int height, String level){
		this.width=width;
		this.height=height;
		Level = new GameTile[width][height];
		 
		 for(int y=0;y<height;y++){
			 for(int x=0;x<width;x++){
				 switch (level.charAt(x+(y*width))){
				 case '#':
						Level[x][y] = new WallTile();break;
				 case ' ':
						Level[x][y] = new EmptyTile();break;
				default:
						System.out.println("Fehlerhaftes Zeichen an Position"+ x+(y*width));
					  
				 }
			 }
		 }
		 
	}
	
	public String toString() {
		String result="";
		for( int y = 0; y < height; ++y) {
			for( int x = 0; x < width; ++x) {
				result += Level[x][y].toString();
			}
		}
		
		return result;
	}
	
}
