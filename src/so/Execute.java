package so;

public class Execute {
	public static void main(String[] args) {

		Process p1 = SystemOperation.systemCall(SystemCallType.CREATE_PROCESS, new Process(20, "P1"));
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p1);
		Process p2 = SystemOperation.systemCall(SystemCallType.CREATE_PROCESS, new Process(38, "P2"));
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p2);
		Process p3 = SystemOperation.systemCall(SystemCallType.CREATE_PROCESS, new Process(38, "P3"));
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p3);
		Process p4 = SystemOperation.systemCall(SystemCallType.CREATE_PROCESS, new Process(20, "P4"));
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p4);
		Process p5 = SystemOperation.systemCall(SystemCallType.CREATE_PROCESS, new Process(8, "P5"));
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p5);
		Process p6 = SystemOperation.systemCall(SystemCallType.CREATE_PROCESS, new Process(40, "P5"));
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p6);
		
	}

}
