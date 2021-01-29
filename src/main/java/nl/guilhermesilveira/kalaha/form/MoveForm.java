package nl.guilhermesilveira.kalaha.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import nl.guilhermesilveira.kalaha.dto.MoveDto;

public class MoveForm {

	@NotNull
	@Min(value = 1)
	private Long gameId;
	@NotNull
	@Min(value = 0)
	private Integer selectedPit;

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Integer getSelectedPit() {
		return selectedPit;
	}

	public void setSelectedPit(Integer selectedPit) {
		this.selectedPit = selectedPit;
	}

	public MoveDto convertMoveFormToDto(MoveForm moveForm) {
		MoveDto moveDto = new MoveDto();
		moveDto.setGameId(moveForm.getGameId());
		moveDto.setSelectedPit(moveForm.getSelectedPit());
		return moveDto;
	}

}
