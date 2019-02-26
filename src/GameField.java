public class GameField {

		private GameTile[][] Level;
	
	
	public GameField(int width, int height, String level){
		 Level = new GameTile[width][height];
		 
		 for(int y=0;y<height;y++){
			 for(int x=0;x<width;x++){
				 switch (level.charAt(x+(y*width))){
				 case '#':
						Level[x][y] = new WallTile();break;
				 }
			 }
		 }
		 
	}
	
}
