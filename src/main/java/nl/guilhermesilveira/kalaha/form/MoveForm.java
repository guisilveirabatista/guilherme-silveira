package nl.guilhermesilveira.kalaha.form;

public class MoveForm {

	private Long gameId;
	private Long userId;
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

	public int getSelectedPit() {
		return selectedPit;
	}

	public void setSelectedPit(int selectedPit) {
		this.selectedPit = selectedPit;
	}

}
