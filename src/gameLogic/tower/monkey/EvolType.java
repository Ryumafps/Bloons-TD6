package gameLogic.tower.monkey;

/**
 * Every evolution type possible
 **/ 
public enum EvolType{
	Range,
	Attack,
	ShotSpeed,
	Proje,
	;

public String toString() {
	switch (this) {
		case Range:
		return "\u001B[3m" + "\u001B[32m" + "Range" + "\u001B[0m";
		case Attack:
		return "\u001B[4m" + "\u001B[31m" + "Attack" + "\u001B[0m";
		case ShotSpeed:
		return "\u001B[2m" + "\u001B[35m" + "ShotSpeed" + "\u001B[0m";
		case Proje:
		return "\u001B[33m" + "Projectile" + "\u001B[0m";
		default:
		return null;
	}
}
}
