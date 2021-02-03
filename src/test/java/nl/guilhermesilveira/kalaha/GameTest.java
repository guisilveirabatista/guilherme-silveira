package nl.guilhermesilveira.kalaha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.game.GameLogic;
import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.GameStatus;
import nl.guilhermesilveira.kalaha.model.Move;
import nl.guilhermesilveira.kalaha.model.Pit;
import nl.guilhermesilveira.kalaha.model.Player;

public class GameTest {

	@Test
	void testFirstMove() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(0);

		game = GameLogic.makeMove(game, move);

		List<Pit> pits = game.getPits();

		int[] mockPitsExpectedResult = { 0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0 };

		for (int i = 0; i < mockPitsExpectedResult.length; i++) {
			assertEquals(mockPitsExpectedResult[i], pits.get(i).countStones());
		}

	}

	@Test
	void testStoneDistribution() throws GameException {

		// Some random game scenario
		int[] mockPits = { 1, 6, 2, 0, 8, 4, 22, 4, 8, 1, 3, 6, 1, 6 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerRightTurn);
		game.setCurrentPlayer(Player.Player2);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(8);

		game = GameLogic.makeMove(game, move);

		List<Pit> pits = game.getPits();

		int[] mockPitsExpectedResult = { 2, 7, 3, 0, 8, 4, 22, 4, 0, 2, 4, 7, 2, 7 };

		for (int i = 0; i < mockPitsExpectedResult.length; i++) {
			assertEquals(mockPitsExpectedResult[i], pits.get(i).countStones());
		}

	}

	@Test
	void testSkipOpponentsKalaha() throws GameException {

		// Game scenario which passes by opponet's kalaha
		int[] mockPits = { 1, 1, 1, 4, 1, 2, 7, 2, 3, 1, 16, 14, 2, 17 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerRightTurn);
		game.setCurrentPlayer(Player.Player2);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(10);

		game = GameLogic.makeMove(game, move);

		List<Pit> pits = game.getPits();

		assertEquals(7, pits.get(6).countStones());

	}

	@Test
	void testStoneAddedToKalaha() throws GameException {
		// Game scenario which passes by opponet's kalaha
		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(0);

		game = GameLogic.makeMove(game, move);

		List<Pit> pits = game.getPits();

		assertEquals(1, pits.get(6).countStones());
	}

	@Test
	void testChangeTurn() throws GameException {
		// Game scenario which passes by opponet's kalaha
		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(2);

		game = GameLogic.makeMove(game, move);

		assertEquals(GameStatus.PlayerRightTurn, game.getGameStatus());
	}

	@Test
	void testPlayerLeftStealStones() throws GameException {

		int[] mockPits = { 0, 0, 4, 0, 1, 0, 11, 9, 3, 4, 0, 0, 0, 32 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(4);

		game = GameLogic.makeMove(game, move);

		List<Pit> pits = game.getPits();

		// Check Kalaha 1
		assertEquals(21, pits.get(6).countStones());

		// Check Kalaha 2
		assertEquals(32, pits.get(13).countStones());
	}

	@Test
	void testPlayerRightStealStones() throws GameException {

		int[] mockPits = { 0, 8, 4, 0, 1, 0, 11, 9, 3, 4, 1, 0, 0, 20 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerRightTurn);
		game.setCurrentPlayer(Player.Player2);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(10);

		game = GameLogic.makeMove(game, move);

		List<Pit> pits = game.getPits();

		// Check Kalaha 1
		assertEquals(11, pits.get(6).countStones());

		// Check Kalaha 2
		assertEquals(29, pits.get(13).countStones());
	}

	@Test
	void testPlayerLeftWins() throws GameException {

		int[] mockPits = { 0, 0, 0, 0, 0, 1, 36, 0, 0, 0, 0, 0, 0, 35 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(5);

		game = GameLogic.makeMove(game, move);

		// Check Kalaha 1
		assertEquals(GameStatus.PlayerLeftWins, game.getGameStatus());
	}

	@Test
	void testPlayerRightWins() throws GameException {

		int[] mockPits = { 0, 0, 0, 0, 0, 0, 35, 0, 0, 0, 0, 0, 1, 36 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerRightTurn);
		game.setCurrentPlayer(Player.Player2);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(12);

		game = GameLogic.makeMove(game, move);

		// Check Kalaha 1
		assertEquals(GameStatus.PlayerRightWins, game.getGameStatus());
	}

	@Test
	void testDraw() throws GameException {

		int[] mockPits = { 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 1, 35 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerRightTurn);
		game.setCurrentPlayer(Player.Player2);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(12);

		game = GameLogic.makeMove(game, move);

		// Check Kalaha 1
		assertEquals(GameStatus.Draw, game.getGameStatus());
	}

	@Test
	void testCorrectNumberStonesInKalahasUponGameOver() throws GameException {

		int[] mockPits = { 0, 0, 0, 0, 0, 2, 11, 0, 3, 4, 0, 0, 0, 25 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(5);

		Game retorno = GameLogic.makeMove(game, move);

		List<Pit> pits = retorno.getPits();

		// Check Kalaha 1
		assertEquals(12, pits.get(6).countStones());

		// Check Kalaha 2
		assertEquals(33, pits.get(13).countStones());
	}

	@Test
	void testEmptySideTriggersGameOver() throws GameException {
		int[] mockPits = { 0, 0, 0, 0, 0, 1, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);

		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(5);

		game = GameLogic.makeMove(game, move);

		List<Pit> pits = game.getPits();

		int[] mockPitsExpectedResult = { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 36 };

		for (int i = 0; i < mockPitsExpectedResult.length; i++) {
			assertEquals(mockPitsExpectedResult[i], pits.get(i).countStones());
		}

		assertEquals(GameStatus.PlayerRightWins, game.getGameStatus());

	}

	@Test
	void testValidNewGame() {
		Move move = new Move();
		move.setGameId(Long.valueOf(1));
		move.setSelectedPit(0);
		Assertions.assertThrows(GameException.class, () -> {
			GameLogic.makeMove(null, move);
		});

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };
		Game game1 = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		Assertions.assertThrows(GameException.class, () -> {
			GameLogic.makeMove(game1, null);
		});

		Game game2 = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game2.setPits(null);
		Assertions.assertThrows(GameException.class, () -> {
			GameLogic.makeMove(game2, move);
		});

		Game game3 = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game3.setPits(new ArrayList<Pit>());
		Assertions.assertThrows(GameException.class, () -> {
			GameLogic.makeMove(game3, move);
		});

		Game game4 = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game4.getPits().remove(13);
		Assertions.assertThrows(GameException.class, () -> {
			GameLogic.makeMove(game4, move);
		});

		Game game5 = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game5.setGameStatus(null);
		Assertions.assertThrows(GameException.class, () -> {
			GameLogic.makeMove(game5, move);
		});

		Game game6 = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		move.setSelectedPit(null);
		Assertions.assertThrows(GameException.class, () -> {
			GameLogic.makeMove(game6, move);
		});

		Game game7 = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		move.setSelectedPit(100);
		Assertions.assertThrows(GameException.class, () -> {
			GameLogic.makeMove(game7, move);
		});
	}

}
