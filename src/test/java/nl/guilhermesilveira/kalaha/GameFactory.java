package nl.guilhermesilveira.kalaha;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.GameStatus;
import nl.guilhermesilveira.kalaha.model.Pit;
import nl.guilhermesilveira.kalaha.model.Player;

public class GameFactory {

	public static Game createGame(int[] simplePitState, GameStatus gameStatus) {

		List<Pit> pits = new ArrayList<Pit>();

		for (int i = 0; i < (simplePitState.length / 2); i++) {
			pits.add(new Pit(simplePitState[i], false, Player.Player1));
		}

		for (int i = (simplePitState.length / 2); i < simplePitState.length; i++) {
			pits.add(new Pit(simplePitState[i], false, Player.Player2));
		}

		pits.get(6).setIsKalaha(true);
		pits.get(13).setIsKalaha(true);

		Game game = new Game();
		game.setId(Long.valueOf(1));
		game.setUser(null);
		game.setPlayerLeftPoints(0);
		game.setPlayerRightPoints(0);
		game.setPlayerLeft(Player.Player1);
		game.setPlayerRight(Player.Player2);
		game.setCurrentPlayer(Player.Player1);
		game.setCurrentPit(null);
		game.setGameStatus(gameStatus);
		game.setTurnNumber(0);
		game.setBoardSize(14);
		game.setIntialStones(6);
		game.setPits(pits);
		game.setCreated(new Date());

		return game;
	}

}
