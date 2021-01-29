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

//	private String pitsState;

	private List<PitDto> pitsState;

	public GameDto(Game game) {
		this.id = game.getId();
		this.userId = game.getUser().getId();
		this.player1Points = game.getPlayer1Points();
		this.player2Points = game.getPlayer2Points();
		this.gameStatus = game.getGameStatus().toString();
		this.turnNumber = game.getTurnNumber();
		this.pitsState = PitDto.convertPitListToDto(game.getPitsState());
//		this.pitsState = game.getPitsState().toString();
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

	public List<PitDto> getPitsState() {
		return pitsState;
	}

	public void setPitsState(List<PitDto> pitsState) {
		this.pitsState = pitsState;
	}

//	public String getPitsState() {
//		return pitsState;
//	}
//
//	public void setPitsState(String pitsState) {
//		this.pitsState = pitsState;
//	}

}
