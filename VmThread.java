package vmGui;

import org.jsystem.utils.windows.WindowsCommandLine;

public class VmThread implements  Runnable{
	private String ip;
	public VmThread(String ip) {
		this.ip=ip;
	}
	public void run() {
		WindowsCommandLine wcl = new WindowsCommandLine("C:\\Windows\\System32\\cmdkey.exe /generic:\""+ ip +"\" /user:\"administrator\" /pass:\"Libit123\" & mstsc /v:" + ip);
		try {
			wcl.execute();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
