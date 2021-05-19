package app.wechat.pojo;

public class CommonButton extends Button {
	private String type;  
    private String key;  
  
    
    
    public CommonButton(String name, String type, String key) {
		super.setName(name);
		this.type = type;
		this.key = key;
	}

	public String getType() {  
        return type;  
    }  
  
    public void setType(String type) {  
        this.type = type;  
    }  
  
    public String getKey() {  
        return key;  
    }  
  
    public void setKey(String key) {  
        this.key = key;  
    }  
}
