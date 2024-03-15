package so;

public class Execute {
	public static void main(String[] args) {
		
		Process p1 = SystemOperation.systemCall(SystemCallType.CREAT_PROCESS, null);
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p1);
		
		Process p2 = SystemOperation.systemCall(SystemCallType.CREAT_PROCESS, null);
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p2);
		
		Process p3 = SystemOperation.systemCall(SystemCallType.CREAT_PROCESS, null);
		SystemOperation.systemCall(SystemCallType.WRITE_PROCESS, p3);
	}

}
