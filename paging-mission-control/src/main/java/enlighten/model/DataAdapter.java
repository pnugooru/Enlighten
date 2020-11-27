package enlighten.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataAdapter {

	// The fields in the input file
	private String timestamp;
	private String sateliteID;
	private int redHighLimit;
	private int yellowHighLimit;
	private int yellowLowLimit;
	private int redLowLimit;
	private double rawValue;
	private String component;

	// ArrayList for storing input information
	private List<DataAdapter> telemetry = new ArrayList<DataAdapter>();

	// This function opens the text file for reading
	public void openFile() {
		File file = new File("TelemetryInput.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			// Check if there is another line of input
			while (sc.hasNextLine()) {
				String str = sc.nextLine();
				parseLine(str);
			}

		} catch (IOException exp) {
			exp.printStackTrace();
		}

		sc.close();
	}

	private void parseLine(String str) {
		DataAdapter data = new DataAdapter();
		Scanner sc = new Scanner(str);
		sc.useDelimiter("[|]");
		StringBuilder builder = new StringBuilder();

		// Check if there is another line of input
		while (sc.hasNext()) {
			String temp = sc.next();
			builder.append(temp.substring(0, 4));
			builder.append("-");
			builder.append(temp.substring(4, 6));
			builder.append("-");
			builder.append(temp.substring(6, 8));
			builder.append(" ");
			builder.append(temp.substring(9));
			data.setTimestamp(builder.toString());
			data.setSateliteID(sc.next());
			data.setRedHighLimit(Integer.parseInt(sc.next()));
			data.setYellowHighLimit(Integer.parseInt(sc.next()));
			data.setYellowLowLimit(Integer.parseInt(sc.next()));
			data.setRedLowLimit(Integer.parseInt(sc.next()));
			data.setRawValue(Double.parseDouble(sc.next()));
			data.setComponent(sc.next());
			// Save the parsed telemetry data as an object inside ArrayList.
			telemetry.add(data);
		}
		sc.close();
	}

	// Returns the ArrayList containing the telemetry data
	public List<DataAdapter> getTelemetry() {
		openFile();
		return telemetry;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String string) {
		this.timestamp = string;
	}

	public String getSateliteID() {
		return sateliteID;
	}

	public void setSateliteID(String sateliteID) {
		this.sateliteID = sateliteID;
	}

	public int getRedHighLimit() {
		return redHighLimit;
	}

	public void setRedHighLimit(int redHighLimit) {
		this.redHighLimit = redHighLimit;
	}

	public int getYellowHighLimit() {
		return yellowHighLimit;
	}

	public void setYellowHighLimit(int yellowHighLimit) {
		this.yellowHighLimit = yellowHighLimit;
	}

	public int getYellowLowLimit() {
		return yellowLowLimit;
	}

	public void setYellowLowLimit(int yellowLowLimit) {
		this.yellowLowLimit = yellowLowLimit;
	}

	public int getRedLowLimit() {
		return redLowLimit;
	}

	public void setRedLowLimit(int redLowLimit) {
		this.redLowLimit = redLowLimit;
	}

	public double getRawValue() {
		return rawValue;
	}

	private void setRawValue(double rawValue) {
		this.rawValue = rawValue;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
