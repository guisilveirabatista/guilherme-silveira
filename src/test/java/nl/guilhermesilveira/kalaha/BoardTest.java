package nl.guilhermesilveira.kalaha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.game.BoardLogic;
import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.GameStatus;
import nl.guilhermesilveira.kalaha.model.Pit;
import nl.guilhermesilveira.kalaha.model.Player;

public class BoardTest {

	@Test
	void testBoardSetup() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game.setPits(BoardLogic.prepareBoard(game));

		assertEquals(14, game.getPits().size());

		Pit kalahaPlayerLeft = BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits());

		assertEquals(true, kalahaPlayerLeft.isKalaha());
		assertEquals(Player.Player1, kalahaPlayerLeft.getPlayer());
		assertEquals(0, kalahaPlayerLeft.countStones());

		Pit kalahaPlayerRight = BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits());

		assertEquals(true, kalahaPlayerRight.isKalaha());
		assertEquals(Player.Player2, kalahaPlayerRight.getPlayer());
		assertEquals(0, kalahaPlayerRight.countStones());

		List<Pit> fieldPlayerLeft = game.getPits().stream()
				.filter(p -> !p.isKalaha() && p.getPlayer() == game.getPlayerLeft()).collect(Collectors.toList());
		fieldPlayerLeft.forEach(p -> {
			assertEquals(6, p.countStones());
		});

		List<Pit> fieldPlayerRight = game.getPits().stream()
				.filter(p -> !p.isKalaha() && p.getPlayer() == game.getPlayerRight()).collect(Collectors.toList());
		fieldPlayerRight.forEach(p -> {
			assertEquals(6, p.countStones());
		});

	}

	@Test
	void testGetNextPit() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game.setPits(BoardLogic.prepareBoard(game));

		game.setCurrentPit(game.getPits().get(0));

		Pit nextPit = BoardLogic.getNextPit(game.getCurrentPit(), game.getPits());

		assertEquals(1, game.getPits().indexOf(nextPit));
	}

	@Test
	void testGetOppositePit() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game.setPits(BoardLogic.prepareBoard(game));

		game.setCurrentPit(game.getPits().get(0));

		Pit oppositePit = BoardLogic.getOppositePit(game.getCurrentPit(), game.getPits());

		assertEquals(12, game.getPits().indexOf(oppositePit));
	}

	@Test
	void testGetPlayerKalaha() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game.setPits(BoardLogic.prepareBoard(game));

		Pit kalahaPlayerLeft = BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits());

		assertEquals(true, kalahaPlayerLeft.isKalaha());
		assertEquals(Player.Player1.name(), kalahaPlayerLeft.getPlayer().name());

		Pit kalahaPlayerRight = BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits());

		assertEquals(true, kalahaPlayerRight.isKalaha());
		assertEquals(Player.Player2.name(), kalahaPlayerRight.getPlayer().name());
	}

	@Test
	void testCountStonesOnPlayerField() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game.setPits(BoardLogic.prepareBoard(game));

		int stonesLeft = BoardLogic.countStonesOnPlayerField(game.getPlayerLeft(), game.getPits());
		int stonesRight = BoardLogic.countStonesOnPlayerField(game.getPlayerRight(), game.getPits());

		assertEquals(36, stonesLeft);
		assertEquals(36, stonesRight);
	}

	@Test
	void testCountAllStonesOnField() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game.setPits(BoardLogic.prepareBoard(game));

		int allStones = BoardLogic.countAllStonesOnField(game.getPits());

		assertEquals(72, allStones);
	}

	@Test
	void testCountStonesOnPlayerKalaha() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game.setPits(BoardLogic.prepareBoard(game));

		Pit playerLeftKalaha = BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits());
		Pit playerRightKalaha = BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits());

		playerLeftKalaha.setStones(40);
		playerRightKalaha.setStones(30);

		int kalahaLeftStones = BoardLogic.countStonesOnPlayerKalaha(game.getPlayerLeft(), game.getPits());
		int kalahaRightStones = BoardLogic.countStonesOnPlayerKalaha(game.getPlayerRight(), game.getPits());

		assertEquals(40, kalahaLeftStones);
		assertEquals(30, kalahaRightStones);
	}

	@Test
	void grabAllStonesFromPlayerField() throws GameException {

		int[] mockPits = { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 };

		Game game = GameFactory.createGame(mockPits, GameStatus.PlayerLeftTurn);
		game.setPits(BoardLogic.prepareBoard(game));

		Pit playerLeftKalaha = BoardLogic.getPlayerKalaha(game.getPlayerLeft(), game.getPits());
		Pit playerRightKalaha = BoardLogic.getPlayerKalaha(game.getPlayerRight(), game.getPits());

		playerLeftKalaha.setStones(40);
		playerRightKalaha.setStones(30);

		BoardLogic.grabAllStonesFromPlayerField(game.getPlayerLeft(), game.getPits());
		BoardLogic.grabAllStonesFromPlayerField(game.getPlayerRight(), game.getPits());

		int stonesFieldLeft = BoardLogic.countStonesOnPlayerField(game.getPlayerLeft(), game.getPits());
		int stonesFieldRight = BoardLogic.countStonesOnPlayerField(game.getPlayerLeft(), game.getPits());

		assertEquals(0, stonesFieldLeft);
		assertEquals(0, stonesFieldRight);
	}
}
