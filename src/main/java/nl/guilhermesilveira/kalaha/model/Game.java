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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	@Column
	private Integer boardSize;

	@Column
	private Integer intialStones;

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
	private String currentPit;

	@Column
	private Integer turnNumber;

	@ElementCollection
	private List<Pit> pits;

	@Column
	@Temporal(TemporalType.DATE)
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCurrentPit() {
		return currentPit;
	}

	public void setCurrentPit(String currentPit) {
		this.currentPit = currentPit;
	}

	public Integer getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(Integer turnNumber) {
		this.turnNumber = turnNumber;
	}

	public List<Pit> getPits() {
		return pits;
	}

	public void setPits(List<Pit> pits) {
		this.pits = pits;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
