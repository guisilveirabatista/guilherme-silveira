package nl.guilhermesilveira.kalaha.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	@OneToOne
	@JoinColumn
	private User user;

	@Enumerated(EnumType.STRING)
	private GameStatus gameStatus;

	@Column
	private Integer player1Points;

	@Column
	private Integer player2Points;

	@Column
	private String lastSelectedPit;

	@Column
	private Integer turnNumber;

	@Column
	private Integer boardSize;

	@Column
	private Integer intialStones;

//	@Lob
	@ElementCollection
	private List<Pit> pitsState;

	@Column
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
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

	public String getLastSelectedPit() {
		return lastSelectedPit;
	}

	public void setLastSelectedPit(String lastSelectedPit) {
		this.lastSelectedPit = lastSelectedPit;
	}

	public Integer getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(Integer turnNumber) {
		this.turnNumber = turnNumber;
	}

	public Integer getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(Integer boardSize) {
		this.boardSize = boardSize;
	}

	public Integer getIntialStones() {
		return intialStones;
	}

	public void setIntialStones(Integer intialStones) {
		this.intialStones = intialStones;
	}

	public List<Pit> getPitsState() {
		return pitsState;
	}

	public void setPitsState(List<Pit> pitsState) {
		this.pitsState = pitsState;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
