package nl.guilhermesilveira.kalaha.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping
	@Transactional
	public ResponseEntity<?> newGame() {
		GameDto gameDto = null;
		try {
			gameDto = this.gameService.newGame();
			return ResponseEntity.ok(gameDto);
		} catch (GameException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping
	public ResponseEntity<?> loadGame(Long id) {
		GameDto gameDto = null;
		try {
			if (id != null) {
				gameDto = this.gameService.loadGame(id);
			}
			if (gameDto == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(gameDto);
		} catch (GameException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping
	@Transactional
	public ResponseEntity<?> makeMove(@RequestBody @Valid MoveForm moveForm) {
		try {
			MoveDto moveDto = moveForm.convertMoveFormToDto(moveForm);
			GameDto gameDto = this.gameService.makeMove(moveDto);
			if (gameDto == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(gameDto);
		} catch (GameException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}

}