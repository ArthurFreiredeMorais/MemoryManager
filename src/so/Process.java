package so;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import so.memory.AddressMemory;

public class Process {
	private int sizeInMemory;
	private String id;
	private AddressMemory am;

	public Process(int size, String id) {
		this.id = id;
		this.sizeInMemory = size;

	}

	public int getSizeInMemory() {
		return sizeInMemory;
	}

	public void setSizeInMemory(int sizeInMemory) {
		this.sizeInMemory = sizeInMemory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AddressMemory getAm() {
		return am;
	}

	public void setAm(AddressMemory am) {
		this.am = am;
	}

}
