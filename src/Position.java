public class Position {

public int x;
public int y;

public Position(int x, int y){
	this.x = x;
	this.y = y;
}

public int getX(){return x;}

public int getY(){return y;}

public void setX(int x){this.x = x;}

public void setY(int y){this.y = y;}

public boolean equals(Position that){
	if(this.x == that.x && this.y == that.y){return true;}
	return false;
	}

public int toIndex(int iWidth) {
	return x + y*iWidth;
}
}
