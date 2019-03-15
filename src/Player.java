class Player extends GameTile {
		
	public String toString() {
			return "@";
		}
		public void moveLeft() {
			position.x--;
		}
		public void moveRight() {
			position.x++;
		}
		public void moveUp() {
			position.y--;
		}
		public void moveDown() {
			position.y++;
		}
	}
