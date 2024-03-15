package so.memory;

import so.Process;

public class MemoryManager {
	
	private String[] physicMemory;
	private Strategy strategy;
	//quem for usar paginacao
	private String[] logicMemroy;
	
	public MemoryManager (Strategy strategy) {
		this.physicMemory = new String[128];
		this.strategy = strategy;
	}
	
	
	public void write(Process p) {
		if(this.strategy.equals(Strategy.FIRST_FIT)) {
			this.writeWithFirstFit(p);
		} else if(this.strategy.equals(Strategy.BEST_FIT)) {
			this.writeWithBestFit(p);
		} else if(this.strategy.equals(Strategy.WORST_FIT)) {
			this.writeWithWorstFit(p);
		}
	}

	public void delete(Process p) {
	    if (strategy.equals(Strategy.FIRST_FIT)) {
	        deleteWithFirstFit(p);
	    } else if (strategy.equals(Strategy.BEST_FIT)) {
	    	deleteWithBestFit(p);
	    } else if (strategy.equals(Strategy.WORST_FIT)) {
	        deleteWithWorstFit(p);
	    }
	}

	private void deleteWithFirstFit(Process p) {
	    System.out.println("Deletando o processo da memória usando o First Fit");
	    boolean found = false;
	    int startOfBlock = -1;
	    int endOfBlock = -1;

	    // Encontrar o bloco de memória ocupado pelo processo a ser deletado
	    for (int i = 0; i < physicMemory.length; i++) {
	        if (physicMemory[i] != null && physicMemory[i].equals(p.getId())) {
	            startOfBlock = i;
	            endOfBlock = i;
	            found = true;

	            // Expandir o bloco para incluir todas as posições ocupadas pelo processo
	            while (endOfBlock < physicMemory.length - 1 && physicMemory[endOfBlock + 1] != null && physicMemory[endOfBlock + 1].equals(p.getId())) {
	                endOfBlock++;
	            }

	            break;
	        }
	    }

	    if (!found) {
	        System.out.println("Processo não encontrado na memória.");
	    } else {
	        // Liberar o bloco de memória ocupado pelo processo
	        for (int i = startOfBlock; i <= endOfBlock; i++) {
	            physicMemory[i] = null;
	        }
	        System.out.println("Processo removido com sucesso da memória.");
	    }

	    printMemoryStatus();
	}


	private void deleteWithWorstFit(Process p) {
	    System.out.println("Deletando o processo da memória usando o Worst Fit");
	    int maxBlockSize = 0;
	    int startOfBlock = -1;
	    int endOfBlock = -1;

	    // Encontrar o bloco de memória ocupado pelo processo a ser deletado
	    for (int i = 0; i < physicMemory.length; i++) {
	        if (physicMemory[i] != null && physicMemory[i].equals(p.getId())) {
	            int blockSize = 1;
	            for (int j = i + 1; j < physicMemory.length; j++) {
	                if (physicMemory[j] != null && physicMemory[j].equals(p.getId())) {
	                    blockSize++;
	                } else {
	                    break;
	                }
	            }
	            if (blockSize > maxBlockSize) {
	                maxBlockSize = blockSize;
	                startOfBlock = i;
	                endOfBlock = i + blockSize - 1;
	            }
	        }
	    }

	    if (startOfBlock == -1 || endOfBlock == -1) {
	        System.out.println("Processo não encontrado na memória.");
	    } else {
	        // Liberar o bloco de memória ocupado pelo processo
	        for (int i = startOfBlock; i <= endOfBlock; i++) {
	            physicMemory[i] = null;
	        }
	        System.out.println("Processo removido com sucesso da memória.");
	    }

	    printMemoryStatus();
	}

	private void deleteWithBestFit(Process p) {
	    System.out.println("Deletando o processo da memória usando o Best Fit");
	    int minBlockSize = Integer.MAX_VALUE;
	    int startOfBlock = -1;
	    int endOfBlock = -1;
	    boolean found = false;

	    // Encontrar o bloco de memória ocupado pelo processo a ser deletado
	    for (int i = 0; i < physicMemory.length; i++) {
	        if (physicMemory[i] != null && physicMemory[i].equals(p.getId())) {
	            int blockSize = 1;
	            for (int j = i + 1; j < physicMemory.length; j++) {
	                if (physicMemory[j] != null && physicMemory[j].equals(p.getId())) {
	                    blockSize++;
	                } else {
	                    break;
	                }
	            }
	            if (blockSize >= p.getSizeInMemory() && blockSize < minBlockSize) {
	                minBlockSize = blockSize;
	                startOfBlock = i;
	                endOfBlock = i + blockSize - 1;
	                found = true;
	            }
	        }
	    }

	    if (!found) {
	        System.out.println("Processo não encontrado na memória.");
	    } else {
	        // Liberar o bloco de memória ocupado pelo processo
	        for (int i = startOfBlock; i <= endOfBlock; i++) {
	            physicMemory[i] = null;
	        }
	        System.out.println("Processo removido com sucesso da memória.");
	    }

	    printMemoryStatus();
	}

	
	private void writeWithFirstFit(Process p) {
		System.out.println("Escrevendo o processo na memoria");
		int actualSize = 0;
		AddressMemory bestPage = null;
		for(int i = 0; i < this.physicMemory.length; i++) {
			if(i == (physicMemory.length - 1)) {
				if(actualSize > 0) {
					int start = (i + 1) - actualSize;
					int end = i -1;
					AddressMemory address = new AddressMemory(start, end);
					if(p.getSizeInMemory() <= address.size()) {
						bestPage = new AddressMemory(start, end);
					}
				}
				
			} else if (this.physicMemory[i] == null) {
				actualSize++;
			} else  {
				if(actualSize > 0) {
					int start = i - actualSize;
					int end = i -1;
					AddressMemory address = new AddressMemory(start, end);
					if(p.getSizeInMemory() <= address.size()) {
						bestPage = new AddressMemory(start, end);
					}
						
				}
				actualSize = 0;
			}
		}
		if(actualSize == 0) {
			System.out.println("Não ha espaço na memoria");
		} else {
			for(int i = bestPage.getStart(); i <= bestPage.getEnd(); i++) {
				this.physicMemory[i] = p.getId();
			}
			System.out.println("Processo inserido com sucesso");
		}
		
		printMemoryStatus();
		
	}
	
	private void printMemoryStatus() {
		for(int i = 0; i < physicMemory.length; i++) {
			System.out.print(physicMemory[i] + " | ");
		}
		
	}
	
	private void writeWithWorstFit(Process p) {
		 System.out.println("Escrevendo o processo na memoria usando o Worst Fit");
		    int maxBlockSize = 0;
		    int startOfBlock = -1;
		    int endOfBlock = -1;

		    for (int i = 0; i < physicMemory.length; i++) {
		        if (physicMemory[i] == null) {
		            int blockSize = 1;
		            for (int j = i + 1; j < physicMemory.length; j++) {
		                if (physicMemory[j] == null) {
		                    blockSize++;
		                } else {
		                    break;
		                }
		            }

		            if (blockSize > maxBlockSize) {
		                maxBlockSize = blockSize;
		                startOfBlock = i;
		                endOfBlock = i + blockSize - 1;
		            }
		        }
		    }

		    if (maxBlockSize < p.getSizeInMemory()) {
		        System.out.println("Não há espaço suficiente na memória para inserir o processo.");
		    } else {
		        for (int i = startOfBlock; i <= endOfBlock; i++) {
		            physicMemory[i] = p.getId();
		        }
		        System.out.println("Processo inserido com sucesso na pior posição de ajuste.");
		    }

		    printMemoryStatus();
		}
		

	private void writeWithBestFit(Process p) {
	    System.out.println("Escrevendo o processo na memoria usando o Best Fit");
	    int minBlockSize = Integer.MAX_VALUE;
	    int startOfBlock = -1;
	    int endOfBlock = -1;

	    for (int i = 0; i < physicMemory.length; i++) {
	        if (physicMemory[i] == null) {
	            int blockSize = 1;
	            for (int j = i + 1; j < physicMemory.length; j++) {
	                if (physicMemory[j] == null) {
	                    blockSize++;
	                } else {
	                    break;
	                }
	            }

	            if (blockSize >= p.getSizeInMemory() && blockSize < minBlockSize) {
	                minBlockSize = blockSize;
	                startOfBlock = i;
	                endOfBlock = i + blockSize - 1;
	            }
	        }
	    }

	    if (startOfBlock == -1 || endOfBlock == -1) {
	        System.out.println("Não há espaço suficiente na memória para inserir o processo.");
	    } else {
	        for (int i = startOfBlock; i <= endOfBlock; i++) {
	            physicMemory[i] = p.getId();
	        }
	        System.out.println("Processo inserido com sucesso na melhor posição de ajuste.");
	    }

	    printMemoryStatus();
	}

}
