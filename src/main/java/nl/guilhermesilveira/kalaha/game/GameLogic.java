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
import nl.guilhermesilveira.kalaha.model.Player;

@Component
public class GameLogic {

	// These values could be dynamic in the future
	public static final int BOARD_SIZE = 14;
	public static final int INITIAL_STONES = 6;

	public static Game newGame() {

		Game game = new Game();
		game.setTurnNumber(1);
		game.setPlayerLeft(Player.Player1);
		game.setPlayerRight(Player.Player2);
		game.setCurrentPlayer(Player.Player1);
		game.setPlayerLeftPoints(0);
		game.setPlayerRightPoints(0);
		game.setCreated(new Date());
		game.setCurrentPit(null);
		game.setBoardSize(BOARD_SIZE);
		game.setIntialStones(INITIAL_STONES);
		game.setPits(BoardLogic.prepareBoard(game));
		game.setGameStatus(GameStatus.PlayerLeftTurn);

		return game;
	}

	public static Game loadGame(Game game) {
		// Implement additional logic if necessary
		return game;
	}

	public static Game makeMove(Game game, Move move) throws GameException {

		validateGameAndMoveSetup(game, move);

		changeCurrentPit(game, move);

		isLegalMove(game, move);

		sowStones(game);

		updatePlayersPoints(game);

		changeGameTurn(game);

		if (isGameOver(game)) {
			endGame(game);
		}

		return game;
	}

	private static void changeCurrentPit(Game game, Move move) {
		game.setCurrentPit(game.getPits().get(move.getSelectedPit()));
	}

	private static void validateGameAndMoveSetup(Game game, Move move) throws GameException {

		// Validates Game and Move
		validateGameAndMove(game, move);

		// Validates Pit List
		validatePitList(game.getPits(), game.getBoardSize());

		// Validate Game Status
		validateGameStatus(game.getGameStatus());

		// Validates Selected Pit
		validateSelectedPit(move.getSelectedPit(), game.getPits());
	}

	private static void validateGameAndMove(Game game, Move move) throws GameException {
		if (game == null || game.getId() == null || game.getId() == 0 || move == null || move.getGameId() == null) {
			throw new GameException("Game error! Game not created or move not created!");
		}
	}

	private static void validatePitList(List<Pit> pits, int boardSize) throws GameException {
		if (pits == null || pits.size() < boardSize) {
			throw new GameException("Pit state invalid!");
		}
	}

	private static void validateGameStatus(GameStatus gameStatus) throws GameException {
		List<String> listEnumGameStatus = Stream.of(GameStatus.values()).map(GameStatus::name)
				.collect(Collectors.toList());
		if (gameStatus == null || !listEnumGameStatus.contains(gameStatus.toString())) {
			throw new GameException("Turn info invalid!");
		}
	}

	private static void validateSelectedPit(Integer selectedPit, List<Pit> pits) throws GameException {
		if (selectedPit == null || selectedPit > pits.size()) {
			throw new GameException("Pit invalid!");
		}
	}

	private static void isLegalMove(Game game, Move move) throws GameException {

		if (game.getCurrentPlayer() != Player.Player1 && game.getCurrentPlayer() != Player.Player2) {
			throw new GameException("Player number invalid!");
		}

		if (game.getCurrentPit().getPlayer() != game.getCurrentPlayer()) {
			throw new GameException("This move is invalid! It's player's " + getTurnPlayer(game) + " turn.");
		}

		if (game.getCurrentPit().countStones() == 0) {
			throw new GameException("This move is invalid! The pit selected is empty!");
		}
	}

	private static void sowStones(Game game) {
		int hand = game.getCurrentPit().grabAllStones();

		while (hand > 0) {
			game.setCurrentPit(BoardLogic.getNextPit(game.getCurrentPit(), game.getPits()));

			// Check if pit is opponent's Kalaha
			if (game.getCurrentPit().isOpponentsKalaha(game.getCurrentPlayer())) {
				continue;
			}

			game.getCurrentPit().add(1);
			hand--;
		}

		if (canStealStones(game)) {
			stealStones(game);
		}
	}

	private static boolean canStealStones(Game game) {
		if (game.getCurrentPit().countStones() > 1 || game.getCurrentPit().isKalaha()) {
			return false;
		}
		Pit oppositePit = BoardLogic.getOppositePit(game.getCurrentPit(), game.getPits());
		if (!oppositePit.isOpponentsPit(game.getCurrentPlayer())) {
			return false;
		}
		return true;
	}

	private static void stealStones(Game game) {
		int stolenStones = 0;

		// Grab all stones from current pit
		stolenStones += game.getCurrentPit().grabAllStones();

		// Grab all stones from opposite pit if opposite pit is opponents pit and it's
		// not empty
		Pit oppositePit = BoardLogic.getOppositePit(game.getCurrentPit(), game.getPits());
		stolenStones += oppositePit.grabAllStones();

		// Sow all the stones stolen in the current player's Kalaha
		Pit currentPlayersKalaha = BoardLogic.getPlayerKalaha(game.getCurrentPlayer(), game.getPits());
		currentPlayersKalaha.add(stolenStones);
	}

	private static void changeGameTurn(Game game) {
		if (!checkIsLastPitIsPlayerSKalaha(game)) {
			game.setGameStatus(getNextTurnGameStatus(game));
			game.setCurrentPlayer(getTurnPlayer(game));
		}
		game.setTurnNumber(game.getTurnNumber() + 1);
	}

	private static void updatePlayersPoints(Game game) {
		game.setPlayerLeftPoints(BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits()).countStones());
		game.setPlayerRightPoints(BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits()).countStones());
	}

	private static boolean checkIsLastPitIsPlayerSKalaha(Game game) {
		return game.getCurrentPit().isPlayersKalaha(game.getCurrentPlayer());
	}

	private static boolean isGameOver(Game game) {
		if (game.getGameStatus() == GameStatus.PlayerLeftWins || game.getGameStatus() == GameStatus.PlayerRightWins
				|| game.getGameStatus() == GameStatus.Draw) {
			return true;
		}

		// Maybe turn this condition on if necessary
		if (isImpossibleToWin(game)) {
			// return true;
		}

		if (isOnePlayerFieldsEmpty(game)) {
			return true;
		}
		return false;
	}

	private static boolean isImpossibleToWin(Game game) {
		int allStonesOnField = BoardLogic.countAllStonesOnField(game.getPits());
		int stonesOnKalahaPlayerLeft = BoardLogic.countStonesOnPlayerKalaha(game.getPlayerLeft(), game.getPits());
		int stonesOnKalahaPlayerRight = BoardLogic.countStonesOnPlayerKalaha(game.getPlayerRight(), game.getPits());

		// impossible for Player Right
		if (stonesOnKalahaPlayerLeft > (allStonesOnField + stonesOnKalahaPlayerRight)) {
			return true;
		}

		// impossible for Player Left
		if (stonesOnKalahaPlayerRight > (allStonesOnField + stonesOnKalahaPlayerLeft)) {
			return true;
		}

		return false;
	}

	private static boolean isOnePlayerFieldsEmpty(Game game) {
		int stonesOnFieldPlayerLeft = BoardLogic.countStonesOnPlayerField(game.getPlayerLeft(), game.getPits());
		int stonesOnFieldPlayerRight = BoardLogic.countStonesOnPlayerField(game.getPlayerRight(), game.getPits());

		if (stonesOnFieldPlayerLeft == 0) {
			return true;
		}
		if (stonesOnFieldPlayerRight == 0) {
			return true;
		}
		return false;
	}

	public static void endGame(Game game) {
		int grabbedStonesFromFieldPlayerLeft = BoardLogic.grabAllStonesFromPlayerField(game.getPlayerLeft(),
				game.getPits());
		int grabbedStonesFromFieldPlayerRight = BoardLogic.grabAllStonesFromPlayerField(game.getPlayerRight(),
				game.getPits());

		BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits()).add(grabbedStonesFromFieldPlayerLeft);
		BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits()).add(grabbedStonesFromFieldPlayerRight);

		updatePlayersPoints(game);
		setGameEndResult(game);
	}

	private static GameStatus getNextTurnGameStatus(Game game) {
		return game.getCurrentPlayer().equals(game.getPlayerLeft()) ? GameStatus.PlayerRightTurn
				: GameStatus.PlayerLeftTurn;
	}

	private static void setGameEndResult(Game game) {
		if (game.getPlayerLeftPoints() > game.getPlayerRightPoints()) {
			game.setGameStatus(GameStatus.PlayerLeftWins);
		} else if (game.getPlayerLeftPoints() < game.getPlayerRightPoints()) {
			game.setGameStatus(GameStatus.PlayerRightWins);
		} else {
			game.setGameStatus(GameStatus.Draw);
		}
	}

	private static Player getTurnPlayer(Game game) {
		return game.getGameStatus() == GameStatus.PlayerLeftTurn ? game.getPlayerLeft() : game.getPlayerRight();
	}
}
