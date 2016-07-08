package stuff;

import java.util.ArrayList;
import java.util.List;

public class Project {
	
	private final int id;
	private String title, description;
	private List<Text> content = new ArrayList<Text>();
	private final int owner;
	
	public Project(int id, String title, String description, List<Text> content, int owner) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.owner = owner;
	}
	public Project(int id, String title, String description, int owner) {
		this(id, title, description, new ArrayList<Text>(), owner);
	}
	public Project(int id, String title, int owner) {
		this(id, title, "", owner);
	}
	public Project(int id, int owner) {
		this(id, "no title", owner);
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Text> getContent() {
		return content;
	}
	public void setContent(List<Text> newContent) {
		content = newContent;
	}
	
	public int getOwner() {
		return owner;
	}
	
	public boolean addContent(Text newText) {
		return content.add(newText);
	}
	
}
