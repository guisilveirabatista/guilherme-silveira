package nl.guilhermesilveira.kalaha.dto;

import nl.guilhermesilveira.kalaha.model.Move;

public class MoveDto {

	private Long gameId;
	private Long userId;
	private int playerNumber;
	private int selectedPit;

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

	public Move convertToMove() {
		Move move = new Move();
		move.setGameId(this.getGameId());
		move.setUserId(this.getUserId());
		move.setSelectedPit(this.getSelectedPit());
		return move;
	}

}
