package nl.guilhermesilveira.kalaha;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.guilhermesilveira.kalaha.game.Board;
import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.GameStatus;
import nl.guilhermesilveira.kalaha.model.Pit;

public class GameFactory {

	public static Game createGame(int[] simplePitState, GameStatus gameStatus) {

		List<Pit> pits = new ArrayList<Pit>();
		
		for(int i = 0; i < (simplePitState.length / 2); i++) {
			pits.add(new Pit(simplePitState[i], false, 1));
		}
		
		for(int i = (simplePitState.length / 2); i < simplePitState.length; i++) {
			pits.add(new Pit(simplePitState[i], false, 2));
		}
		
		pits.get(6).setIsKalaha(true);
		pits.get(13).setIsKalaha(true);
		

		Board board = Board.loadBoard(pits);

		Game game = new Game();
		game.setId((long) 1);
		game.setUser(null);
		game.setPlayer1Points(0);
		game.setPlayer2Points(0);
		game.setCurrentPit("");
		game.setPitsState(board.getPits());
		game.setGameStatus(gameStatus);
		game.setTurnNumber(0);
		game.setBoardSize(14);
		game.setIntialStones(6);
		game.setCreated(new Date());

		return game;
	}

}
