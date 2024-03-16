package so.memory;

import so.Process;

public class MemoryManager {
	private String[] physicMemory;
	private Strategy strategy;


	public MemoryManager(Strategy strategy) {
		this.physicMemory = new String[128];
		this.strategy = strategy;
	}

	public void write(Process p) {
		if (this.strategy.equals(Strategy.FIRST_FIT)) {
			this.writeWithFirstFit(p);

		} else if (this.strategy.equals(Strategy.BEST_FIT)) {
			this.writeWithBestFit(p);

		} else if (this.strategy.equals(Strategy.WORST_FIT)) {
			this.writeWithWorstFit(p);

		}

	}

	private void writeWithBestFit(Process p) {

		int bestSize = -1;
		int actualSize = 0;
		int actualBestSize = 0;
		System.out.println("Process: " + p.getSizeInMemory());

		for (int i = 0; i < this.physicMemory.length; i++) {
			if (this.physicMemory[i] == null) {
				actualSize++;

				if (i == this.physicMemory.length - 1) {
					bestSize = i - actualSize + 1;
					if (actualSize < p.getSizeInMemory()) {
						bestSize = -1;

					}
				}

			} else {

				if ((actualSize - 1) == p.getSizeInMemory()) {
					bestSize = i - actualSize + 1;
					break;

				} else if (actualSize > p.getSizeInMemory() && actualSize < actualBestSize
						|| i == this.physicMemory.length - 1) {

					bestSize = (i - actualSize) + 1;

				} else if (actualSize > p.getSizeInMemory()) {
					actualBestSize = (i - actualSize) + 1;
					bestSize = actualBestSize;

				}
				actualSize = 0;
			}
		}
		System.out.println();
		if ( bestSize >= 0) {
			System.out.println("Writing memory with best fit");
			int start = bestSize;
			int end = start + p.getSizeInMemory();
			AddressMemory address = new AddressMemory(start, end);
			for (int i = address.getStart(); i < address.getEnd(); i++) {
				this.physicMemory[i] = p.getId();
				System.out.println(i);
			}
		} else {
			System.out.println("No space available in memory");
		}
		printMemoryStatus();
	}

	private void writeWithWorstFit(Process p) {
		int biggerSize = -1;
		int actualSize = 0;
		System.out.println("Process: " + p.getSizeInMemory());
		for (int i = 0; i < physicMemory.length; i++) {
			if (this.physicMemory[i] != null) {
				if (actualSize > biggerSize && actualSize >= p.getSizeInMemory()) {
					biggerSize = (i - actualSize) + 1;
					actualSize = 0;
				} else {
					actualSize = 0;
				}
			} else {
				actualSize++;
				if (this.physicMemory.length - 1 == i) {
					if (actualSize > biggerSize) {
						biggerSize = (i - actualSize) + 1;
					}
				}
			}
		}
		if (biggerSize >= 0) {
			System.out.println("Writing memory with worst fit");
			int start = biggerSize;
			int end = start + p.getSizeInMemory();
			AddressMemory address = new AddressMemory(start, end);
			for (int i = address.getStart(); i < address.getEnd(); i++) {
				this.physicMemory[i] = p.getId();
				System.out.println(i);
			}

		} else {
			System.out.println("No space available in memory");
		}
		printMemoryStatus();
	}

	private void writeWithFirstFit(Process p) {
		int actualSize = 0;
		System.out.println("Process Size: " + p.getSizeInMemory());
		for (int i = 0; i < physicMemory.length; i++) {
			if (this.physicMemory[i] == null) {
				actualSize++;
				if (actualSize == p.getSizeInMemory()) {
					int start = (i - actualSize) + 1;
					int end = i;
					AddressMemory address = new AddressMemory(start, end);
					System.out.println("Writing in memory with first fit");
					for (int ind = address.getStart(); ind <= address.getEnd(); ind++) {
						this.physicMemory[ind] = p.getId();
					}
					printMemoryStatus();
					break;
				}

			} else if (this.physicMemory[i] != null || actualSize != p.getSizeInMemory()) {
				actualSize = 0;
			}
		}
		if (actualSize < p.getSizeInMemory()) {
			System.out.println("No space available for process");
		}
		printMemoryStatus();
		
	}

	private void printMemoryStatus() {
		System.out.println(" Memory Status");

		for (int i = 0; i < physicMemory.length; i++) {
			if (physicMemory[i] != null) {
				System.out.print(physicMemory[i] + "|");

			} else {
				System.out.print("  " + "|");

			}
			if ((i + 1) % 40 == 0) {
				System.out.println();

			}
		}
		System.out.println();
	}

	public void delete(Process p) {
		System.out.println("Processes being deleted: " + p.getId());

		for (int i = 0; i < this.physicMemory.length; i++) {
			if (this.physicMemory[i] == p.getId()) {
				this.physicMemory[i] = null;

			}

			else {
			}
		}
		printMemoryStatus();
	}
}
