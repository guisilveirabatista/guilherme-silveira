package nl.guilhermesilveira.kalaha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.model.Pit;

public class PitTest {

	@Test
	void testPitGrabAllStones() throws GameException {
		Pit pit = new Pit(6);

		assertEquals(6, pit.getStones());

		pit.grabAllStones();

		assertEquals(0, pit.getStones());
	}

	@Test
	void testEmptyPit() throws GameException {
		Pit pit = new Pit(6);

		assertEquals(6, pit.getStones());

		pit.empty();

		assertEquals(0, pit.getStones());
	}

	@Test
	void testCountStones() throws GameException {
		Pit pit = new Pit(6);

		assertEquals(6, pit.getStones());

		assertEquals(6, pit.countStones());
	}

}
