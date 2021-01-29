package nl.guilhermesilveira.kalaha.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.guilhermesilveira.kalaha.dto.GameDto;
import nl.guilhermesilveira.kalaha.dto.MoveDto;
import nl.guilhermesilveira.kalaha.dto.UserDto;
import nl.guilhermesilveira.kalaha.exception.GameException;
import nl.guilhermesilveira.kalaha.form.MoveForm;
import nl.guilhermesilveira.kalaha.service.GameService;

@RestController
@RequestMapping("/kalaha")
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping
	@CrossOrigin(origins = "http://localhost:5500")
	public ResponseEntity<GameDto> newGame() {
		UserDto userDto = new UserDto();
		userDto.setId((long) 1);
		try {
			GameDto gameDto = this.gameService.newGame(userDto);
			return ResponseEntity.ok(gameDto);
//			return ResponseEntity.notFound().build();
		} catch (GameException e) {
			e.printStackTrace();
			// Implement
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/load")
	@CrossOrigin(origins = "http://localhost:5500")
	public ResponseEntity<GameDto> loadGame(Long id) {
		try {
			GameDto gameDto = this.gameService.loadGame(id);
			return ResponseEntity.ok(gameDto);
//			return ResponseEntity.notFound().build();
		} catch (GameException e) {
			e.printStackTrace();
			// Implement
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping
	@Transactional
	@CrossOrigin(origins = "http://localhost:5500")
	public ResponseEntity<GameDto> makeMove(@RequestBody @Valid MoveForm moveForm) {
		try {
			MoveDto moveDto = moveForm.convertMoveFormToDto(moveForm);
			GameDto gameDto = this.gameService.makeMove(moveDto);
			return ResponseEntity.ok(gameDto);
//			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
