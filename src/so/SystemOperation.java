package so;

import so.cpu.CpuManager;
import so.memory.MemoryManager;
import so.memory.Strategy;
import so.scheduler.Scheduler;

public class SystemOperation {
	
	private static MemoryManager mm;
	private static CpuManager cm;
	private static Scheduler scheduler;
	
	
	public static Process systemCall(SystemCallType type, Process p) {
		if(type.equals(SystemCallType.CREAT_PROCESS)){
			if(mm == null) {
				mm = new MemoryManager(Strategy.BEST_FIT);
			}
			if(cm == null) {
				cm = new CpuManager();
			}
			return new Process();
			
		} else if (type.equals(SystemCallType.WRITE_PROCESS)){
			mm.write(p);
			
		} else if (type.equals(SystemCallType.CLOSE_PROCESS)) {
			mm.delete(p);
		}
		return null;
	}

}
