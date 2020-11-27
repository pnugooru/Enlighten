package enlighten;

import java.time.LocalDateTime;

public class DataDto {
	private String sateliteID;
	private String severity;
	private String component;
	private String timestamp;

	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String strTime) {
		this.timestamp = strTime;
	}
	public String getSateliteID() {
		return sateliteID;
	}
	public void setSateliteID(String sateliteID) {
		this.sateliteID = sateliteID;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
}
