package linux;

public class ServerEntity {
	private String HostName ="";
	private String IPv4="";
	private String userName = "";
	private String PassWord = "";
	public String getHostName() {
		return HostName;
	}
	public void setHostName(String hostName) {
		HostName = hostName;
	}
	public String getIPv4() {
		return IPv4;
	}
	public void setIPv4(String iPv4) {
		IPv4 = iPv4;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return PassWord;
	}
	public void setPassWord(String passWord) {
		PassWord = passWord;
	}
	

}
