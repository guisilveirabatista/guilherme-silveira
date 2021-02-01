package nl.guilhermesilveira.kalaha.dto;

import nl.guilhermesilveira.kalaha.model.Move;

public class MoveDto {

	private Long gameId;
	private Integer selectedPit;

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public int getSelectedPit() {
		return selectedPit;
	}

	public void setSelectedPit(Integer selectedPit) {
		this.selectedPit = selectedPit;
	}

	public Move convertToMove() {
		Move move = new Move();
		move.setGameId(this.getGameId());
		move.setSelectedPit(this.getSelectedPit());
		return move;
	}

}
