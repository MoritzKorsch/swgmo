package stuff;

public class Text {
	
	private final int id;
	private String title, body;
	private final User op;
	
	public Text(int id, String title, String body, User op) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.op = op;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String newTitle) {
		title = newTitle;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String newBody) {
		title = newBody;
	}
	
	public User getOp() {
		return op;
	}
}
