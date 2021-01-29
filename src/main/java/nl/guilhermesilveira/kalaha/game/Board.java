package nl.guilhermesilveira.kalaha.game;

import java.util.ArrayList;
import java.util.List;

import nl.guilhermesilveira.kalaha.model.Pit;

public class Board {

	private List<Pit> pits;

	public List<Pit> getPits() {
		return pits;
	}

	private void setPits(List<Pit> pits) {
		this.pits = pits;
	}

	public Board() {

	}

	public Board(int boardSize, int stones) {
		this.pits = new ArrayList<Pit>();
		for (int i = 0; i < boardSize; i++) {
			Pit pit = new Pit();
			this.pits.add(pit);
		}
		assignKalahas();
		assignPlayersToPits();
		pourStones(stones);
	}

	public void assignKalahas() {
		// Kalaha Player 1
		Pit kalahaPlayer1 = this.pits.get((this.pits.size() / 2) - 1);
		kalahaPlayer1.setPlayer(1);
		kalahaPlayer1.setIsKalaha(true);
		kalahaPlayer1.setStones(0);

		// Kalaha Player 2
//		Pit kalahaPlayer2 = this.pits.get(this.pits.size() / 2);
		Pit kalahaPlayer2 = this.pits.get(this.pits.size() - 1);
		kalahaPlayer2.setPlayer(2);
		kalahaPlayer2.setIsKalaha(true);
		kalahaPlayer2.setStones(0);
	}

	public void assignPlayersToPits() {
		int halfPits = (this.pits.size() / 2);

		// Player 1
		this.pits.subList(0, halfPits).stream().filter(p -> !p.isKalaha()).forEach(p -> p.setPlayer(1));
		// Player 2
		this.pits.subList(halfPits, this.pits.size()).stream().filter(p -> !p.isKalaha()).forEach(p -> p.setPlayer(2));
	}

	public void pourStones(int stones) {
		this.pits.stream().filter(p -> !p.isKalaha()).forEach(p -> p.add(stones));
	}

	public Pit getNextPit(Pit currentPit) {
		int indexCurrentPit = this.pits.indexOf(currentPit);
		Pit nextPit = null;
		if (indexCurrentPit == (this.pits.size() - 1)) {
			nextPit = this.pits.get(0);
		} else {
			nextPit = this.pits.get(indexCurrentPit + 1);
		}
		return nextPit;
	}

	public Pit getOppositePit(Pit currentPit) {

		// Check if pit is Kalaha
		if (currentPit.isKalaha()) {
			return null;
		}

		Pit oppositePit = null;
		int currentIndex = this.pits.indexOf(currentPit);
		int oppositeIndex = (this.pits.size() - 2) - currentIndex;
		oppositePit = this.pits.get(oppositeIndex);
		return oppositePit;
	}


	public Pit getPlayerKalaha(int player) {
		return this.pits.stream().filter(p -> p.getPlayer() == player && p.isKalaha() == true).findFirst().orElse(null);
	}

	public static Board loadBoard(List<Pit> pitsState) {
		Board board = new Board();
		board.setPits(pitsState);
		return board;
	}

}
