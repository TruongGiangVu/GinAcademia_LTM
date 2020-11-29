package Module;

import java.util.HashMap;
// class for checking data by regex and error
public class MyRegEx {
	public HashMap<String,String> pattern = new HashMap<String, String>();
	public HashMap<String,String> error = new HashMap<String, String>();
	public MyRegEx() {
		// TO DO Auto-generated constructor stub
		SeedPattern();
		SeedError();
	}
	private void SeedPattern() {
		pattern.put("id", "^\\d{5}$");
		pattern.put("name", "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
		pattern.put("email", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
		pattern.put("username", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
		pattern.put("password", "^[a-z0-9A-Z]{5,40}$");
	}
	private void SeedError() {
		error.put("id", "ID chỉ gồm 5 chữ số!");
		error.put("name", "Sai định dạng tên");
		error.put("email", "Sai định dạng email!");
		error.put("username", "Sai định dạng email!");
		error.put("password", "Pasword phải có từ 5 tới 20 ký tự!");
	}
	public String getPattern(String data) {
		data = this.cleanString(data);
		return pattern.get(data);
	}
	public String getError(String data) {
		data = this.cleanString(data);
		return error.get(data);
	}
	private String cleanString(String str) {
		str = str.toLowerCase();
		str = str.trim();
		str = str.replaceAll("//s+", " ");
		return str;
	}
	public String getAllKey() {
		String ans ="{";
		for( String key: pattern.keySet() )
			ans = ans + key + ":" + pattern.get(key)+",";
		ans+="}";
		return ans;
	}
}
