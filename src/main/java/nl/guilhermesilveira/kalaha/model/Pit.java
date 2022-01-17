package nl.guilhermesilveira.kalaha.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Pit {

	@Column(nullable = true)
	private int stones;
	@Column(nullable = true)
	private boolean isKalaha;
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private Player player;

	public Pit() {

	}

	public Pit(int stones) {
		this.stones = stones;
	}

	public Pit(int stones, boolean isKalaha, Player player) {
		this.stones = stones;
		this.isKalaha = isKalaha;
		this.player = player;
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
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

	public void empty() {
		this.stones = 0;
	}

	public int countStones() {
		return this.getStones();
	}

	@Override
	public String toString() {
		return "{'stones': " + this.stones + ",'isKalaha': " + this.isKalaha + ",'player': " + this.player + "}";
	}

	public boolean isPlayersKalaha(Player currentPlayer) {
		return this.isKalaha && this.player == currentPlayer ? true : false;
	}

	public boolean isOpponentsKalaha(Player currentPlayer) {
		return this.isKalaha && this.player != currentPlayer ? true : false;
	}

	public boolean isOpponentsPit(Player currentPlayer) {
		return this.player != currentPlayer ? true : false;
	}

}
