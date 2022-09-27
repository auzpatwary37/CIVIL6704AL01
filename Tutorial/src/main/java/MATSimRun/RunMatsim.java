package MATSimRun;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

public class RunMatsim {

	public static void main(String[] args) {
		Config config = ConfigUtils.createConfig();
		ConfigUtils.loadConfig(config, "config.xml");
		Scenario scenario = ScenarioUtils.loadScenario(config);
		Controler controler = new Controler(scenario);
		controler.run();
	}

}
