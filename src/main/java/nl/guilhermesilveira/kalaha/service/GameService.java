package nl.guilhermesilveira.kalaha.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.guilhermesilveira.kalaha.dto.GameDto;
import nl.guilhermesilveira.kalaha.dto.MoveDto;
import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.game.GameLogic;
import nl.guilhermesilveira.kalaha.model.Game;
import nl.guilhermesilveira.kalaha.model.Move;
import nl.guilhermesilveira.kalaha.repository.GameRepository;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	public GameDto newGame() throws GameException {

		Game game = GameLogic.newGame();

		this.gameRepository.save(game);

		return new GameDto(game);
	}

	public GameDto loadGame(Long gameId) throws GameException {
		Optional<Game> gameOpt = this.gameRepository.findById(gameId);
		if (gameOpt.isPresent()) {
			Game game = gameOpt.get();

			game = GameLogic.loadGame(game);

			return new GameDto(game);
		}
		return null;
	}

	public GameDto makeMove(MoveDto moveDto) throws GameException {
		Move move = moveDto.convertToMove();
		Optional<Game> optional = this.gameRepository.findById(move.getGameId());
		if (optional.isPresent()) {
			Game game = optional.get();

			game = GameLogic.makeMove(game, move);

			this.gameRepository.save(game);

			return new GameDto(game);
		}
		return null;
	}

}
