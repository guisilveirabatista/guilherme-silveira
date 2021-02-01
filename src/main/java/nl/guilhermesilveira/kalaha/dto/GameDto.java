package nl.guilhermesilveira.kalaha.dto;

import java.util.List;

import nl.guilhermesilveira.kalaha.model.Game;

public class GameDto {

	private Long id;

	private Long userId;

	private Integer playerLeftPoints;

	private Integer playerRightPoints;

	private String gameStatus;

	private int turnNumber;

	private List<PitDto> pits;

	public GameDto(Game game) {
		this.id = game.getId();
		this.playerLeftPoints = game.getPlayerLeftPoints();
		this.playerRightPoints = game.getPlayerRightPoints();
		this.gameStatus = game.getGameStatus().toString();
		this.turnNumber = game.getTurnNumber();
		this.pits = PitDto.convertPitListToDto(game.getPits());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getPlayerLeftPoints() {
		return playerLeftPoints;
	}

	public void setPlayerLeftPoints(Integer playerLeftPoints) {
		this.playerLeftPoints = playerLeftPoints;
	}

	public Integer getPlayerRightPoints() {
		return playerRightPoints;
	}

	public void setPlayerRightPoints(Integer playerRightPoints) {
		this.playerRightPoints = playerRightPoints;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public List<PitDto> getPits() {
		return pits;
	}

	public void setPits(List<PitDto> pits) {
		this.pits = pits;
	}

}
