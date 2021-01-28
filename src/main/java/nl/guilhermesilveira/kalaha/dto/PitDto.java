package nl.guilhermesilveira.kalaha.dto;

import java.util.ArrayList;
import java.util.List;

import nl.guilhermesilveira.kalaha.model.Pit;

public class PitDto {

	private int stones;
	private boolean isKalaha;
	private int player;

	public PitDto() {

	}

	public PitDto(Pit pit) {
		this.stones = pit.getStones();
		this.isKalaha = pit.isKalaha();
		this.player = pit.getPlayer();
	}

	public int getStones() {
		return stones;
	}

	public void setStones(int stones) {
		this.stones = stones;
	}

	public boolean isKalaha() {
		return isKalaha;
	}

	public void setKalaha(boolean isKalaha) {
		this.isKalaha = isKalaha;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public static List<PitDto> convertPitListToDto(List<Pit> pits) {
		List<PitDto> pitsDto = new ArrayList<PitDto>();
		pits.forEach(p -> {
			pitsDto.add(new PitDto(p));
		});
		return pitsDto;
	}

}
