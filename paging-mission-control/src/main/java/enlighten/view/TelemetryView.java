package enlighten.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TelemetryView {

	public void displayAdapter(Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(object);
		System.out.println(jsonOutput);
	}

}
