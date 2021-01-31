package nl.guilhermesilveira.kalaha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.game.Board;
import nl.guilhermesilveira.kalaha.model.Pit;

public class BoardTest {

	@Test
	void testBoardSetup() throws GameException {

		Board board = new Board(14, 6);
		
		assertEquals(14, board.getPits().size());
		
		Pit kalaha1 = board.getPlayerKalaha(1);
		
		assertEquals(true, kalaha1.isKalaha());
		assertEquals(1, kalaha1.getPlayer());
		assertEquals(0, kalaha1.countStones());
		
		Pit kalaha2 = board.getPlayerKalaha(2);
		
		assertEquals(true, kalaha2.isKalaha());
		assertEquals(2, kalaha2.getPlayer());
		assertEquals(0, kalaha2.countStones());
		
		List<Pit> fieldP1 = board.getPits().stream().filter(p -> !p.isKalaha() && p.getPlayer() == 1).collect(Collectors.toList());		
		fieldP1.forEach(p -> {
			assertEquals(6, p.countStones());
		});
		
		List<Pit> fieldP2 = board.getPits().stream().filter(p -> !p.isKalaha() && p.getPlayer() == 2).collect(Collectors.toList());		
		fieldP2.forEach(p -> {
			assertEquals(6, p.countStones());
		});
		
	}
	
	@Test
	void testGetNextPit() throws GameException {
		Board board = new Board(14, 6);
		
		Pit pit = board.getPits().get(0);
		
		Pit nextPit = board.getNextPit(pit);
		
		assertEquals(1, board.getPits().indexOf(nextPit));
	}
	
	@Test
	void testGetOppositePit() throws GameException {
		Board board = new Board(14, 6);
		
		Pit pit = board.getPits().get(0);
		
		Pit oppositePit = board.getOppositePit(pit);
		
		assertEquals(12, board.getPits().indexOf(oppositePit));
	}
	
	@Test
	void testGetPlayerKalaha() throws GameException {
		Board board = new Board(14, 6);
		
		Pit kalahaP1 = board.getPlayerKalaha(1);
		
		assertEquals(true, kalahaP1.isKalaha());
		assertEquals(1, kalahaP1.getPlayer());
		
		Pit kalahaP2 = board.getPlayerKalaha(2);
		
		assertEquals(true, kalahaP2.isKalaha());
		assertEquals(2, kalahaP2.getPlayer());
	}
}
