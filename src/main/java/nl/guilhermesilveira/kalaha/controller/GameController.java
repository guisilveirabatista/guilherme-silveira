package nl.guilhermesilveira.kalaha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import nl.guilhermesilveira.kalaha.dto.GameDto;
import nl.guilhermesilveira.kalaha.dto.MoveDto;
import nl.guilhermesilveira.kalaha.dto.UserDto;
import nl.guilhermesilveira.kalaha.service.GameService;

@RestController
@RequestMapping("/kalaha")
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping
	@CrossOrigin(origins = "http://localhost:5500")
	public GameDto newGame() {
		UserDto userDto = new UserDto();
		userDto.setId((long) 1);
		try {
			return this.gameService.newGame(userDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/load")
	@CrossOrigin(origins = "http://localhost:5500")
	public GameDto loadGame(Long id) {
		try {
			return this.gameService.loadGame(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping
	@Transactional
	@CrossOrigin(origins = "http://localhost:5500")
	public GameDto makeMove(@RequestBody MoveDto moveDto) throws JsonMappingException, JsonProcessingException {
		try {
			return this.gameService.makeMove(moveDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
