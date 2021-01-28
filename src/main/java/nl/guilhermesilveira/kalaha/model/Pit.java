package nl.guilhermesilveira.kalaha.model;

import javax.persistence.Embeddable;

@Embeddable
public class Pit {

	private int stones;
	private boolean isKalaha;
	private int player;

	public Pit() {

	}

	public Pit(int stones) {
		this.stones = stones;
	}

	public int getStones() {
		return stones;
	}

	public void setStones(int stones) {
		this.stones = stones;
	}

	public boolean isKalaha() {
		return isKalaha;
	}

	public void setIsKalaha(boolean isKalaha) {
		this.isKalaha = isKalaha;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int grabAllStones() {
		int stonesTemp = this.stones;
		this.stones = 0;
		return stonesTemp;
	}

	public void add(int stones) {
		this.stones += stones;
	}

	public int countStones() {
		return this.getStones();
	}

	@Override
	public String toString() {
		return "{'stones': " + this.stones + ",'isKalaha': " + this.isKalaha + ",'player': " + this.player + "}";
	}

	public boolean isPlayersKalaha(int currentPlayer) {
		return this.isKalaha && this.player == currentPlayer ? true : false;
	}

	public boolean isOpponentsKalaha(int currentPlayer) {
		return this.isKalaha && this.player != currentPlayer ? true : false;
	}

}
