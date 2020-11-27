package enlighten.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import enlighten.DataDto;
import enlighten.model.DataAdapter;
import enlighten.view.TelemetryView;

public class Controller {
	private DataAdapter model;
	private TelemetryView view;

	public Controller(DataAdapter model, TelemetryView view) {
		super();
		this.model = model;
		this.view = view;
	}

	public void alert() {
		List<DataAdapter> telemetry = model.getTelemetry();
		int count = 0;
		DataAdapter display, check;
		List<DataDto> listDto = new ArrayList<>();
		for (int i = 0; i < telemetry.size(); i++) {
			// count = 0;
			display = telemetry.get(i);
			for (int j = i; j < telemetry.size(); j++) {
				check = telemetry.get(j);

				// System.out.println("I don't know what is happening");
				if (display.getSateliteID() == check.getSateliteID()) {
					count += verify(display, check);

					// calls view to display message
					if (count >= 3) {
						display = telemetry.get(i);
						count = 0;
						listDto.add(createDataDto(display));

					}
				}
			}
		}

		view.displayAdapter(listDto);
	}

	private DataDto createDataDto(DataAdapter adapter) {
		DataDto dto = new DataDto();
		String severity;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS");
		LocalDateTime dateTime1 = LocalDateTime.parse(adapter.getTimestamp(), formatter);
		String strTime = dateTime1.toString();
		if (adapter.getComponent().equals("TSTAT")) {
			severity = "RED HIGH";
		} else {
			severity = "RED LOW";
		}
		dto.setComponent(adapter.getComponent());
		dto.setSateliteID(adapter.getSateliteID());
		dto.setTimestamp(strTime);
		dto.setSeverity(severity);
		return dto;
	}

	// Verifies whether the conditions for alert is met or not
	public int verify(DataAdapter display, DataAdapter check) {
		int count = 0;

		// calls a check on the other fields
		int feedback = checkTimeStamp(display.getTimestamp(), check.getTimestamp());

		if (feedback > -1) {
			count = count + confirm(display, check);
			return count;
		}
		return 0;
	}

	// This method checks the volt limit
	private int checkVoltLimit(Double rawValue, int lowLimit) {
		if (rawValue < lowLimit) {
			return 1;
		}
		return 0;
	}

	// This method checks the temperature limit
	private int checkTempLimit(Double rawValue, int highLimit) {
		if (rawValue > highLimit) {
			return 1;
		}
		return 0;
	}

	// This method checks time
	private int checkTimeStamp(String time1, String time2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS");
		LocalDateTime dateTime1 = LocalDateTime.parse(time1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(time2, formatter);
		LocalDateTime minimumTime = dateTime1.minusMinutes((long) 5);

		int result = dateTime2.compareTo(minimumTime);
		if (result > -1) {
			return 0;
		}

		return -1;

	}

	// this method does further checks to confirm the data is important
	private int confirm(DataAdapter a, DataAdapter b) {
		String TSTAT = "TSTAT";
		String BATT = "BATT";
		String componentA = a.getComponent();
		String componentB = b.getComponent();
		int output = 0;

		// Compare components A & B
		if (componentA.equals(componentB) && componentA.equals(TSTAT) && a.getSateliteID().equals(b.getSateliteID())) {
			output = checkTempLimit(b.getRawValue(), b.getRedHighLimit());
		}
		if (componentA.equals(componentB) && componentA.equals(BATT) && a.getSateliteID().equals(b.getSateliteID())) {
			output = checkVoltLimit(b.getRawValue(), b.getRedLowLimit());

		}

		return output;
	}

}
