package nl.guilhermesilveira.kalaha.model;

import java.util.Date;

public class Move {

	private Long gameId;
	private Long userId;
	private int playerNumber;
	private int selectedPit;
	private Date date;

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getSelectedPit() {
		return selectedPit;
	}

	public void setSelectedPit(int selectedPit) {
		this.selectedPit = selectedPit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
