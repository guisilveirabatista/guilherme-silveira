package nl.guilhermesilveira.kalaha.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.guilhermesilveira.kalaha.dto.GameDto;
import nl.guilhermesilveira.kalaha.dto.MoveDto;
import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.form.MoveForm;
import nl.guilhermesilveira.kalaha.service.GameService;

@RestController
@RequestMapping("/kalaha")
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping
	public ResponseEntity<GameDto> getGame(Long id) {
		GameDto gameDto = null;
		try {
			if (id != null) {
				gameDto = this.gameService.loadGame(id);
			} else {
				gameDto = this.gameService.newGame();
			}
			if (gameDto == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(gameDto);
		} catch (GameException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(400).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping
	@Transactional
	public ResponseEntity<GameDto> makeMove(@RequestBody @Valid MoveForm moveForm) {
		try {
			MoveDto moveDto = moveForm.convertMoveFormToDto(moveForm);
			GameDto gameDto = this.gameService.makeMove(moveDto);
			return ResponseEntity.ok(gameDto);
		} catch (GameException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(400).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}

}