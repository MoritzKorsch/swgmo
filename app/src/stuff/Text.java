package stuff;

public class Text {
	
	private final int id;
	private String title, body;
	private int projectID;
	
	public Text(int id, String title, String body, int projectID) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.projectID = projectID;
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
	
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int newProjectID) {
		projectID = newProjectID;
	}
}
