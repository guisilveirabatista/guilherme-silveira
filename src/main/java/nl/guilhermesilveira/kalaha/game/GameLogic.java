package nl.guilhermesilveira.kalaha.game;

import java.util.Date;

import org.springframework.stereotype.Component;

import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.GameStatus;
import nl.guilhermesilveira.kalaha.model.Move;
import nl.guilhermesilveira.kalaha.model.Pit;
import nl.guilhermesilveira.kalaha.model.User;

//TODO IMPLEMENT EXCEPTIONS

//TODO IMPLEMENT TESTS

//TODO IMPROVE CONSTRUCTORS OF CLASSES

//TODO IMPLEMENT LOG

@Component
public class GameLogic implements IGameLogic {

	public static final int BOARD_SIZE = 14;
	public static final int INITIAL_STONES = 6;
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;

	private Board board;
	private Pit currentPit;
	private int currentPlayer;
	private int player1Points;
	private int player2Points;
	private int turnNumber;
	private GameStatus gameStatus;

	@Override
	public Game newGame(User user) {

		this.board = new Board(BOARD_SIZE, INITIAL_STONES);

		Game game = new Game();
		game.setUser(user);
		game.setPlayer1Points(0);
		game.setPlayer2Points(0);
		game.setLastSelectedPit(null);
		game.setPitsState(this.board.getPits());
		game.setGameStatus(GameStatus.Player1Turn);
		game.setTurnNumber(1);
		game.setBoardSize(BOARD_SIZE);
		game.setIntialStones(INITIAL_STONES);
		game.setCreated(new Date());

		return game;
	}

	public Game loadGame(Game game) {
		return game;
	}

	@Override
	public Game makeMove(Game game, Move move) throws GameException {

		// Load game from passed game state
		this.board = Board.loadBoard(game.getPitsState());

//		if (isGameOver()) {
//			throw new GameException("Game Over!");
//		}

		int selectedPitNumber = move.getSelectedPit();
		this.currentPit = this.board.getPits().get(selectedPitNumber);
		this.currentPlayer = getPlayerFromStatus(game.getGameStatus());
		this.player1Points = game.getPlayer1Points();
		this.player2Points = game.getPlayer2Points();
		this.turnNumber = game.getTurnNumber();
		this.gameStatus = game.getGameStatus();

		// Validates if turn is valid for the player
		isLegalMove(game);

		sowStones();

		updatePlayersScore();

		changeGameState();

		this.turnNumber++;

		game = updateGame(game);

		return game;
	}

	private Game updateGame(Game game) {

		game.setTurnNumber(this.turnNumber);

		game.setPitsState(this.board.getPits());

		game.setGameStatus(this.gameStatus);

		game.setPlayer1Points(this.player1Points);

		game.setPlayer2Points(this.player2Points);

		return game;
	}

	private void isLegalMove(Game game) throws GameException {

		if (this.currentPlayer != PLAYER1 && this.currentPlayer != PLAYER2) {
			throw new GameException("Player number invalid!");
		}

		if (this.currentPit.getPlayer() != this.currentPlayer) {
			throw new GameException(
					"This move is invalid! It's player's " + this.getPlayerFromStatus(game.getGameStatus()) + " turn.");
		}
	}

	private void sowStones() {
		int hand = this.currentPit.grabAllStones();

		while (hand > 0) {
			this.currentPit = this.board.getNextPit(this.currentPit);

			// Check if pit is opponent's Kalaha
			if (this.currentPit.isOpponentsKalaha(this.currentPlayer)) {
				continue;
			}

			this.currentPit.add(1);
			hand--;
		}

		if (canStealStones()) {
			stealStones();
		}
	}

	private boolean canStealStones() {
		if (this.currentPit.countStones() > 1 || this.currentPit.isKalaha()) {
			return false;
		}
		Pit oppositePit = this.board.getOppositePit(this.currentPit);
		if (!oppositePit.isOpponentsPit(this.currentPlayer)) {
			return false;
		}
		return true;
	}

	private void stealStones() {
		int stolenStones = 0;

		// Grab all stones from current pit
		stolenStones = this.currentPit.grabAllStones();

		// Grab all stones from opposite pit if opposite pit is opponents pit

		Pit oppositePit = this.board.getOppositePit(this.currentPit);
		stolenStones += oppositePit.grabAllStones();

		// Sow all the stones stolen in the current player's Kalaha
		Pit currentPlayersKalaha = this.board.getPlayerKalaha(this.currentPlayer);
		currentPlayersKalaha.add(stolenStones);
	}

	private void updatePlayersScore() {
		this.player1Points = this.board.getPlayerKalaha(1).countStones();
		this.player2Points = this.board.getPlayerKalaha(2).countStones();
	}

	private void changeGameState() {
		if (!checkIsLastPitIsPlayerSKalaha()) {
			this.gameStatus = getNextPlayer(this.currentPlayer);
		}
		if (isGameOver()) {
			endGame();
			this.gameStatus = getGameEndResult();
		}
	}

	private GameStatus getNextPlayer(int player) {
		return getStatusNextPlayer(player);
	}

	private GameStatus getGameEndResult() {
		if (this.player1Points > this.player2Points)
			return GameStatus.Player1Wins;
		if (this.player1Points < this.player2Points)
			return GameStatus.Player2Wins;
		else
			return GameStatus.Draw;
	}

	private boolean checkIsLastPitIsPlayerSKalaha() {
		return this.currentPit.isPlayersKalaha(this.currentPlayer);
	}

	// TODO
	private boolean isGameOver() {
		boolean gameOver = false;

		gameOver = isImpossibleToWin();

		gameOver = isOnePlayerFieldsEmpty();

		return gameOver;
	}

	private boolean isImpossibleToWin() {
		int stonesOnField = this.board.getPits().stream().filter((p) -> !p.isKalaha()).map((p) -> p.countStones())
				.reduce(0, (x, y) -> x + y);
		int kalahaPlayer1 = this.board.getPlayerKalaha(PLAYER1).countStones();
		int kalahaPlayer2 = this.board.getPlayerKalaha(PLAYER2).countStones();

		// impossible for Player 2
		if (kalahaPlayer1 > (stonesOnField + kalahaPlayer2)) {
			return true;
		}

		// impossible for Player 1
		if (kalahaPlayer2 > (stonesOnField + kalahaPlayer1)) {
			return true;
		}

		return false;
	}

	private boolean isOnePlayerFieldsEmpty() {
		boolean gameOver = false;
		int stonesOnFieldP1 = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER1)
				.mapToInt(Pit::getStones).sum();
		int stonesOnFieldP2 = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER2)
				.mapToInt(Pit::getStones).sum();
		if (stonesOnFieldP1 == 0) {
			gameOver = true;
		}
		if (stonesOnFieldP2 == 0) {
			gameOver = true;
		}
		return gameOver;
	}

	public void endGame() {
		int stonesOnFieldP1 = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER1).mapToInt(Pit::getStones).sum();
		int stonesOnFieldP2 = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER2).mapToInt(Pit::getStones).sum();
		
		this.board.getPlayerKalaha(PLAYER1).add(stonesOnFieldP1);
		this.board.getPlayerKalaha(PLAYER2).add(stonesOnFieldP2);
		
		this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER1).forEach(Pit::empty);
		this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER2).forEach(Pit::empty);
	}

//	public void grabAllStonesLeftAndPutThemInKalaha(int player) {
//		int stonesToAdd = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == player)
//				.mapToInt(p -> p.grabAllStones()).sum();
//		System.out.println(stonesToAdd);
//		this.board.getPlayerKalaha(player).add(stonesToAdd);
//	}

//	private boolean checkAllFieldsEmpty() {
//		int stonesOnField = this.board.getPits().stream().filter((p) -> !p.isKalaha()).mapToInt(Pit::getStones).sum();
//
//		if (stonesOnField == 0)
//			return true;
//
//		return false;
//	}

	private int getPlayerFromStatus(GameStatus gameStatus) {
		return gameStatus == GameStatus.Player1Turn ? 1 : 2;
	}

	private GameStatus getStatusNextPlayer(int player) {
		return player == 1 ? GameStatus.Player2Turn : GameStatus.Player1Turn;
	}

}