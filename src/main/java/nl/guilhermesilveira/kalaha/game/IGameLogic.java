package nl.guilhermesilveira.kalaha.game;

import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.Move;

public interface IGameLogic {

	Game newGame() throws GameException;

	Game loadGame(Game game) throws GameException;

	Game makeMove(Game game, Move move) throws GameException;

}
