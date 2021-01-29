package nl.guisilveira.guilhermesilveira;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.game.GameLogic;
import nl.guilhermesilveira.kalaha.game.IGameLogic;
import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.GameStatus;
import nl.guilhermesilveira.kalaha.model.Move;
import nl.guilhermesilveira.kalaha.model.Pit;

public class GameTest {

	private IGameLogic gameLogic = new GameLogic();

	@Test
	void testCorrectNumberStonesInKalahasUponGameOver() throws GameException {

		int[] mockPits = { 0, 0, 0, 0, 0, 2, 11, 0, 3, 4, 0, 0, 0, 25 };

		Game game = GameFactory.createGameFromMock(mockPits, GameStatus.Player1Turn);

		Move move = new Move();
		move.setGameId((long) 1);
		move.setSelectedPit(5);
		move.setPlayerNumber(1);

		Game retorno = gameLogic.makeMove(game, move);

		List<Pit> pits = retorno.getPitsState();

		// Check Kalaha 1
		assertEquals(12, pits.get(6).countStones());

		// Check Kalaha 2
		assertEquals(33, pits.get(13).countStones());
	}

	@Test
	void testP1StealStones() throws GameException {

		int[] mockPits = { 0, 0, 4, 0, 1, 0, 11, 9, 3, 4, 0, 0, 0, 32 };

		Game game = GameFactory.createGameFromMock(mockPits, GameStatus.Player1Turn);

		Move move = new Move();
		move.setGameId((long) 1);
		move.setSelectedPit(4);
		move.setPlayerNumber(1);

		game = gameLogic.makeMove(game, move);

		List<Pit> pits = game.getPitsState();

		// Check Kalaha 1
		assertEquals(21, pits.get(6).countStones());

		// Check Kalaha 2
		assertEquals(32, pits.get(13).countStones());
	}

	@Test
	void testP2StealStones() throws GameException {

		int[] mockPits = { 0, 8, 4, 0, 1, 0, 11, 9, 3, 4, 1, 0, 0, 20 };

		Game game = GameFactory.createGameFromMock(mockPits, GameStatus.Player2Turn);

		Move move = new Move();
		move.setGameId((long) 1);
		move.setSelectedPit(10);
		move.setPlayerNumber(2);

		game = gameLogic.makeMove(game, move);

		List<Pit> pits = game.getPitsState();

		// Check Kalaha 1
		assertEquals(11, pits.get(6).countStones());

		// Check Kalaha 2
		assertEquals(29, pits.get(13).countStones());
	}

}
