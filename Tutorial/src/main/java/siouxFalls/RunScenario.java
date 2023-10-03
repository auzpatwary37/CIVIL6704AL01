package siouxFalls;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

public class RunScenario {
	public static void main(String[] args) {
		Config config = ConfigUtils.createConfig();
		ConfigUtils.loadConfig(config, "siouxfalls-2014/config_default.xml");
		config.plans().setInputFile("siouxfalls-2014/Siouxfalls_population.xml.gz");
		config.facilities().setInputFile("siouxfalls-2014/Siouxfalls_facilities.xml.gz");
		config.network().setInputFile("siouxfalls-2014/Siouxfalls_network_PT.xml");
		config.transit().setTransitScheduleFile("siouxfalls-2014/Siouxfalls_transitSchedule.xml");
		config.transit().setVehiclesFile("siouxfalls-2014/Siouxfalls_vehicles.xml");
	
		config.controler().setOutputDirectory("outputSiouxFalls/");
		config.controler().setOverwriteFileSetting(
				OverwriteFileSetting.deleteDirectoryIfExists);
		
		config.qsim().setStartTime(0);
		config.qsim().setEndTime(27*3600);
		
		config.controler().setFirstIteration(0);
		config.controler().setLastIteration(250);
		config.strategy().setFractionOfIterationsToDisableInnovation(.8);
		
		Scenario scenario = ScenarioUtils.loadScenario(config);
		
		
		Controler controler = new Controler(scenario);
//		controler.addOverridingModule(new AbstractModule(){
//			@Override
//			public void install() {
//				this.addEventHandlerBinding().to(LinkPCUCountEventHandler.class);
//				//this.bind(LinkPCUCountEventHandler.class).toInstance(new LinkPCUCountEventHandler());
//			}
//		});
		controler.run();
	}
}
