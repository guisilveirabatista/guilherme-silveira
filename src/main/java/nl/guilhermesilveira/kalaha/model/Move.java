package nl.guilhermesilveira.kalaha.model;

import java.util.Date;

public class Move {

	private Long gameId;
	private Integer selectedPit;
	private Date date;

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Integer getSelectedPit() {
		return selectedPit;
	}

	public void setSelectedPit(Integer selectedPit) {
		this.selectedPit = selectedPit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
