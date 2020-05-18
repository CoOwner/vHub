package rip.vexus.lobby.server;

public enum ServerTypes {
	
	HCFACTIONS("HCFactions", 23305),
	PRACTICE("Practice", 10007),
	KITS("Kits", 23306);
	
	private String name;
	private int port;
	
	ServerTypes(String name, int port) {
		this.name = name;
		this.port = port;
	}
	
	public String getName() {
		return name;
	}
	
	
	public int getPort() {
		return port;
	}
}
