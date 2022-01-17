package nl.guilhermesilveira.kalaha.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
	private Player playerLeft;

	@Enumerated(EnumType.STRING)
	private Player playerRight;

	@Enumerated(EnumType.STRING)
	private Player currentPlayer;

	@Enumerated(EnumType.STRING)
	private GameStatus gameStatus;

	@Column
	private Integer playerLeftPoints;

	@Column
	private Integer playerRightPoints;

	@Embedded
	private Pit currentPit;

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

	public Player getPlayerLeft() {
		return playerLeft;
	}

	public void setPlayerLeft(Player playerLeft) {
		this.playerLeft = playerLeft;
	}

	public Player getPlayerRight() {
		return playerRight;
	}

	public void setPlayerRight(Player playerRight) {
		this.playerRight = playerRight;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
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

	public Pit getCurrentPit() {
		return currentPit;
	}

	public void setCurrentPit(Pit currentPit) {
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
