package nl.guilhermesilveira.kalaha.game;

import java.util.ArrayList;
import java.util.List;

import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.Pit;
import nl.guilhermesilveira.kalaha.model.Player;

public class BoardLogic {

	public static List<Pit> prepareBoard(Game game) {
		game.setPits(new ArrayList<Pit>());
		for (int i = 0; i < game.getBoardSize(); i++) {
			Pit pit = new Pit();
			game.getPits().add(pit);
		}
		assignKalahas(game);
		assignPlayersToPits(game);
		pourStones(game.getIntialStones(), game.getPits());

		return game.getPits();
	}

	public static Pit getNextPit(Pit currentPit, List<Pit> pits) {
		int indexCurrentPit = pits.indexOf(currentPit);
		if (indexCurrentPit == (pits.size() - 1)) {
			return pits.get(0);
		} else {
			return pits.get(indexCurrentPit + 1);
		}
	}

	public static Pit getOppositePit(Pit currentPit, List<Pit> pits) {
		// Check if pit is Kalaha
		if (currentPit.isKalaha()) {
			return null;
		}
		int currentIndex = pits.indexOf(currentPit);
		int oppositeIndex = (pits.size() - 2) - currentIndex;
		return pits.get(oppositeIndex);
	}

	public static Pit getPlayerKalaha(Player player, List<Pit> pits) {
		return pits.stream().filter(p -> p.getPlayer() == player && p.isKalaha() == true).findFirst().orElse(null);
	}

	public static int countStonesOnPlayerField(Player player, List<Pit> pits) {
		return pits.stream().filter((p) -> !p.isKalaha() && p.getPlayer() == player).mapToInt(Pit::getStones).sum();
	}

	public static int countAllStonesOnField(List<Pit> pits) {
		return pits.stream().filter((p) -> !p.isKalaha()).map((p) -> p.countStones()).reduce(0, (x, y) -> x + y);
	}

	public static int countStonesOnPlayerKalaha(Player player, List<Pit> pits) {
		return getPlayerKalaha(player, pits).countStones();
	}

	public static int grabAllStonesFromPlayerField(Player player, List<Pit> pits) {
		return pits.stream().filter((p) -> !p.isKalaha() && p.getPlayer() == player).mapToInt(Pit::grabAllStones).sum();
	}

	private static void assignKalahas(Game game) {
		// Kalaha Player 1
		Pit kalahaLeft = game.getPits().get((game.getPits().size() / 2) - 1);
		kalahaLeft.setPlayer(game.getPlayerLeft());
		kalahaLeft.setIsKalaha(true);
		kalahaLeft.setStones(0);

		// Kalaha Player 2
		Pit kalahaRight = game.getPits().get(game.getPits().size() - 1);
		kalahaRight.setPlayer(game.getPlayerRight());
		kalahaRight.setIsKalaha(true);
		kalahaRight.setStones(0);
	}

	private static void assignPlayersToPits(Game game) {
		int halfPits = (game.getPits().size() / 2);

		// Player Kalaha Left
		game.getPits().subList(0, halfPits).stream().filter(p -> !p.isKalaha())
				.forEach(p -> p.setPlayer(game.getPlayerLeft()));

		// Player Kalah Right
		game.getPits().subList(halfPits, game.getPits().size()).stream().filter(p -> !p.isKalaha())
				.forEach(p -> p.setPlayer(game.getPlayerRight()));
	}

	private static void pourStones(int stones, List<Pit> pits) {
		pits.stream().filter(p -> !p.isKalaha()).forEach(p -> p.add(stones));
	}

}
