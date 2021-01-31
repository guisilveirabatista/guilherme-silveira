package nl.guilhermesilveira.kalaha.game;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.GameStatus;
import nl.guilhermesilveira.kalaha.model.Move;
import nl.guilhermesilveira.kalaha.model.Pit;

//TODO IMPLEMENT EXCEPTIONS

//TODO IMPLEMENT TESTS

//TODO IMPROVE CONSTRUCTORS OF CLASSES

//TODO IMPLEMENT LOG

@Component
public class GameLogic implements IGameLogic {

	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	public static final int BOARD_SIZE = 14;
	public static final int INITIAL_STONES = 6;

	private Board board;
	private Pit currentPit;
	private int turnNumber;
	private int currentPlayer;
	private int player1Points;
	private int player2Points;
	private GameStatus gameStatus;

	@Override
	public Game newGame() {

		this.board = new Board(BOARD_SIZE, INITIAL_STONES);

		Game game = new Game();
		game.setTurnNumber(1);
		game.setPlayer1Points(0);
		game.setPlayer2Points(0);
		game.setCreated(new Date());
		game.setCurrentPit(null);
		game.setBoardSize(BOARD_SIZE);
		game.setIntialStones(INITIAL_STONES);
		game.setPitsState(this.board.getPits());
		game.setGameStatus(GameStatus.Player1Turn);

		return game;
	}

	public Game loadGame(Game game) {
		// Implement additional logic if necessary
		return game;
	}

	@Override
	public Game makeMove(Game game, Move move) throws GameException {

		// Validates if game and move are set up correctly in order to process the game
		// logic
		validateGameAndMoveSetup(game, move);

		// Load game from passed game state
		this.board = Board.loadBoard(game.getPitsState());
		this.currentPit = this.board.getPits().get(move.getSelectedPit());
		this.currentPlayer = getPlayerFromStatus(game.getGameStatus());
		this.player1Points = game.getPlayer1Points();
		this.player2Points = game.getPlayer2Points();
		this.turnNumber = game.getTurnNumber();
		this.gameStatus = game.getGameStatus();

		// Validates if turn is valid for the player
		isLegalMove(game);

		sowStones();

<<<<<<< Updated upstream
		updatePlayersScore();
=======
		changeGameTurn();
		
		updatePlayersPoints();
		
		if (isGameOver()) {
			endGame();
			this.gameStatus = getGameEndResult();
		}		
>>>>>>> Stashed changes

		changeGameTurn();

		game = updateGame(game);

		return game;
	}

	private void validateGameAndMoveSetup(Game game, Move move) throws GameException {

		// Validates Game and Move
		if (game == null || move == null) {
			throw new GameException("Game error! Game not found or move not created!");
		}
		// Validates Pit List
		if (game.getPitsState() == null || game.getPitsState().size() < BOARD_SIZE) {
			throw new GameException("Pit state invalid!");
		}

		// Validate Turn
		List<String> listEnumGameStatus = Stream.of(GameStatus.values()).map(GameStatus::name)
				.collect(Collectors.toList());
		if (game.getGameStatus() == null || !listEnumGameStatus.contains(game.getGameStatus().toString())) {
			throw new GameException("Turn info invalid!");
		}

		// Validates Selected Pit
		if (move.getSelectedPit() == null || move.getSelectedPit() > game.getPitsState().size()) {
			throw new GameException("Pit invalid!");
		}
	}

	private void isLegalMove(Game game) throws GameException {

		if (this.currentPlayer != PLAYER1 && this.currentPlayer != PLAYER2) {
			throw new GameException("Player number invalid!");
		}

		if (this.currentPit.getPlayer() != this.currentPlayer) {
			throw new GameException(
					"This move is invalid! It's player's " + this.getPlayerFromStatus(game.getGameStatus()) + " turn.");
		}

		if (this.currentPit.countStones() == 0) {
			throw new GameException("This move is invalid! The pit selected is empty!");
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

		// Grab all stones from opposite pit if opposite pit is opponents pit and it's
		// not empty
		Pit oppositePit = this.board.getOppositePit(this.currentPit);
<<<<<<< Updated upstream
		if (oppositePit.countStones() == 0) {
			return;
		}
		stolenStones += this.currentPit.grabAllStones();
=======
>>>>>>> Stashed changes
		stolenStones += oppositePit.grabAllStones();

		// Sow all the stones stolen in the current player's Kalaha
		Pit currentPlayersKalaha = this.board.getPlayerKalaha(this.currentPlayer);
		currentPlayersKalaha.add(stolenStones);
	}

<<<<<<< Updated upstream
	private void updatePlayersScore() {
		this.player1Points = this.board.getPlayerKalaha(1).countStones();
		this.player2Points = this.board.getPlayerKalaha(2).countStones();
	}

=======
>>>>>>> Stashed changes
	private void changeGameTurn() {
		if (!checkIsLastPitIsPlayerSKalaha()) {
			this.gameStatus = getNextPlayer(this.currentPlayer);
		}
		this.turnNumber++;
	}

	private void updatePlayersPoints() {
		this.player1Points = this.board.getPlayerKalaha(GameLogic.PLAYER1).countStones();
		this.player2Points = this.board.getPlayerKalaha(GameLogic.PLAYER2).countStones();
	}

	private Game updateGame(Game game) {

		game.setTurnNumber(this.turnNumber);

		game.setPitsState(this.board.getPits());

		game.setGameStatus(this.gameStatus);

		game.setPlayer1Points(this.player1Points);

		game.setPlayer2Points(this.player2Points);

		return game;
	}

	private boolean checkIsLastPitIsPlayerSKalaha() {
		return this.currentPit.isPlayersKalaha(this.currentPlayer);
	}

	private boolean isGameOver() {
		if (this.gameStatus == GameStatus.Player1Wins || this.gameStatus == GameStatus.Player2Wins
				|| this.gameStatus == GameStatus.Draw) {
			return true;
		}
		if (isImpossibleToWin()) {
//			return true;
		}
		if (isOnePlayerFieldsEmpty()) {
			return true;
		}
		return false;
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
		int stonesOnFieldP1 = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER1)
				.mapToInt(Pit::getStones).sum();
		int stonesOnFieldP2 = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER2)
				.mapToInt(Pit::getStones).sum();
		if (stonesOnFieldP1 == 0) {
			return true;
		}
		if (stonesOnFieldP2 == 0) {
			return true;
		}
		return false;
	}

	public void endGame() {
		int stonesOnFieldP1 = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER1)
				.mapToInt(Pit::grabAllStones).sum();
		int stonesOnFieldP2 = this.board.getPits().stream().filter((p) -> !p.isKalaha() && p.getPlayer() == PLAYER2)
				.mapToInt(Pit::grabAllStones).sum();

		this.board.getPlayerKalaha(PLAYER1).add(stonesOnFieldP1);
		this.board.getPlayerKalaha(PLAYER2).add(stonesOnFieldP2);
		updatePlayersPoints();
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

	private int getPlayerFromStatus(GameStatus gameStatus) {
		return gameStatus == GameStatus.Player1Turn ? 1 : 2;
	}

	private GameStatus getStatusNextPlayer(int player) {
		return player == 1 ? GameStatus.Player2Turn : GameStatus.Player1Turn;
	}

}
