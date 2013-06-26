package server_battle;

public class Position {
	private final Integer x;
	private final Integer y;

	public Position (Integer X, Integer Y) {
		this.x = X;
		this.y = Y;
	}
	
    public int hashCode() {
    	int hashFirst = x != null ? x.hashCode() : 0;
    	int hashSecond = y != null ? y.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

	public boolean equals(Object other) {
		if (other instanceof Position) {
			Position otherPosition = (Position) other;
			return 
					((  this.x == otherPosition.y ||
					( this.x != null && otherPosition.x != null &&
					this.x.equals(otherPosition.x))) &&
					(	this.y == otherPosition.y ||
					( this.y != null && otherPosition.y != null &&
					this.y.equals(otherPosition.y))) );
		}

		return false;
	}

	public String toString()
	{ 
		return "(" + x + ", " + y + ")"; 
	}

	public Integer getx() {
		return x;
	}
	
	public Integer gety() {
		return y;
	}
}
