package nl.guilhermesilveira.kalaha.dto;

import java.util.List;

import nl.guilhermesilveira.kalaha.model.Game;

public class GameDto {

	private Long id;

	private Long userId;

	private Integer player1Points;

	private Integer player2Points;

	private String gameStatus;

	private int turnNumber;

	private List<PitDto> pits;

	public GameDto(Game game) {
		this.id = game.getId();
		this.player1Points = game.getPlayer1Points();
		this.player2Points = game.getPlayer2Points();
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

	public Integer getPlayer1Points() {
		return player1Points;
	}

	public void setPlayer1Points(Integer player1Points) {
		this.player1Points = player1Points;
	}

	public Integer getPlayer2Points() {
		return player2Points;
	}

	public void setPlayer2Points(Integer player2Points) {
		this.player2Points = player2Points;
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
