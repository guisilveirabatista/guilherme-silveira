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
public class GameLogic implements IGameLogic {

	// These values could be dynamic in the future
	public static final int BOARD_SIZE = 14;
	public static final int INITIAL_STONES = 6;

	@Override
	public Game newGame() {

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

	@Override
	public Game loadGame(Game game) {
		// Implement additional logic if necessary
		return game;
	}

	@Override
	public Game makeMove(Game game, Move move) throws GameException {

		validateGameAndMoveSetup(game, move);

		changeCurrentPit(game, move);

		isLegalMove(game, move);

		sowStones(game);

		updatePlayersPoints(game);

		changeGameTurn(game);

		if (isGameOver(game)) {
			endGame(game);
			setGameEndResult(game);
		}

		return game;
	}

	private void changeCurrentPit(Game game, Move move) {
		game.setCurrentPit(game.getPits().get(move.getSelectedPit()));
	}

	private void validateGameAndMoveSetup(Game game, Move move) throws GameException {

		// Validates Game and Move
		if (game == null || move == null) {
			throw new GameException("Game error! Game not found or move not created!");
		}
		// Validates Pit List
		if (game.getPits() == null || game.getPits().size() < BOARD_SIZE) {
			throw new GameException("Pit state invalid!");
		}

		// Validate Turn
		List<String> listEnumGameStatus = Stream.of(GameStatus.values()).map(GameStatus::name)
				.collect(Collectors.toList());
		if (game.getGameStatus() == null || !listEnumGameStatus.contains(game.getGameStatus().toString())) {
			throw new GameException("Turn info invalid!");
		}

		// Validates Selected Pit
		if (move.getSelectedPit() == null || move.getSelectedPit() > game.getPits().size()) {
			throw new GameException("Pit invalid!");
		}
	}

	private void isLegalMove(Game game, Move move) throws GameException {

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

	private void sowStones(Game game) {
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

	private boolean canStealStones(Game game) {
		if (game.getCurrentPit().countStones() > 1 || game.getCurrentPit().isKalaha()) {
			return false;
		}
		Pit oppositePit = BoardLogic.getOppositePit(game.getCurrentPit(), game.getPits());
		if (!oppositePit.isOpponentsPit(game.getCurrentPlayer())) {
			return false;
		}
		return true;
	}

	private void stealStones(Game game) {
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

	private void changeGameTurn(Game game) {
		if (!checkIsLastPitIsPlayerSKalaha(game)) {
			game.setGameStatus(getNextTurnGameStatus(game));
			game.setCurrentPlayer(getTurnPlayer(game));
		}
		game.setTurnNumber(game.getTurnNumber() + 1);
	}

	private void updatePlayersPoints(Game game) {
		game.setPlayerLeftPoints(BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits()).countStones());
		game.setPlayerRightPoints(BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits()).countStones());
	}

	private boolean checkIsLastPitIsPlayerSKalaha(Game game) {
		return game.getCurrentPit().isPlayersKalaha(game.getCurrentPlayer());
	}

	private boolean isGameOver(Game game) {
		if (game.getGameStatus() == GameStatus.PlayerLeftWins || game.getGameStatus() == GameStatus.PlayerRightWins
				|| game.getGameStatus() == GameStatus.Draw) {
			return true;
		}

		// Maybe turn this condition on if necessary
		if (isImpossibleToWin(game)) {
//			return true;
		}

		if (isOnePlayerFieldsEmpty(game)) {
			return true;
		}
		return false;
	}

	private boolean isImpossibleToWin(Game game) {
		int stonesOnField = game.getPits().stream().filter((p) -> !p.isKalaha()).map((p) -> p.countStones()).reduce(0,
				(x, y) -> x + y);
		int kalahaPlayerLeft = BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits()).countStones();
		int kalahaPlayerRight = BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits()).countStones();

		// impossible for Player Right
		if (kalahaPlayerLeft > (stonesOnField + kalahaPlayerRight)) {
			return true;
		}

		// impossible for Player Left
		if (kalahaPlayerRight > (stonesOnField + kalahaPlayerLeft)) {
			return true;
		}

		return false;
	}

	private boolean isOnePlayerFieldsEmpty(Game game) {
		int stonesOnFieldPlayerLeft = game.getPits().stream()
				.filter((p) -> !p.isKalaha() && p.getPlayer() == game.getPlayerLeft()).mapToInt(Pit::getStones).sum();
		int stonesOnFieldPlayerRight = game.getPits().stream()
				.filter((p) -> !p.isKalaha() && p.getPlayer() == game.getPlayerRight()).mapToInt(Pit::getStones).sum();
		if (stonesOnFieldPlayerLeft == 0) {
			return true;
		}
		if (stonesOnFieldPlayerRight == 0) {
			return true;
		}
		return false;
	}

	public void endGame(Game game) {
		int stonesOnFieldPlayerLeft = game.getPits().stream()
				.filter((p) -> !p.isKalaha() && p.getPlayer() == game.getPlayerLeft()).mapToInt(Pit::grabAllStones)
				.sum();
		int stonesOnFieldPlayerRight = game.getPits().stream()
				.filter((p) -> !p.isKalaha() && p.getPlayer() == game.getPlayerRight()).mapToInt(Pit::grabAllStones)
				.sum();

		BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits()).add(stonesOnFieldPlayerLeft);
		BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits()).add(stonesOnFieldPlayerRight);
		updatePlayersPoints(game);
	}

	private GameStatus getNextTurnGameStatus(Game game) {
		return game.getCurrentPlayer().equals(game.getPlayerLeft()) ? GameStatus.PlayerRightTurn
				: GameStatus.PlayerLeftTurn;
	}

	private void setGameEndResult(Game game) {
		if (game.getPlayerLeftPoints() > game.getPlayerRightPoints()) {
			game.setGameStatus(GameStatus.PlayerLeftWins);
		} else if (game.getPlayerLeftPoints() < game.getPlayerRightPoints()) {
			game.setGameStatus(GameStatus.PlayerRightWins);
		} else {
			game.setGameStatus(GameStatus.Draw);
		}
	}

	private Player getTurnPlayer(Game game) {
		return game.getGameStatus() == GameStatus.PlayerLeftTurn ? game.getPlayerLeft() : game.getPlayerRight();
	}
}
