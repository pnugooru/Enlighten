package enlighten;

import enlighten.controller.Controller;
import enlighten.model.DataAdapter;
import enlighten.view.TelemetryView;

public class Application {

	public static void main(String[] args) {
		DataAdapter mAdapter = new DataAdapter();
		mAdapter.openFile();
		TelemetryView display = new TelemetryView();
		Controller controller = new Controller(mAdapter, display);
		controller.alert();
	}

}
