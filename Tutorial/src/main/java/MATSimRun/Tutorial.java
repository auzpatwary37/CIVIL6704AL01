package MATSimRun;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.ConfigWriter;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.config.groups.StrategyConfigGroup.StrategySettings;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule.DefaultPlansRemover;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule.DefaultSelector;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule.DefaultStrategy;

public class Tutorial {
	public static void main(String[] args) {
		
		//Population creation exercise
		
		Config config = ConfigUtils.createConfig();
		
		//Set strategy
		config.strategy().addStrategySettings(new StrategySettings().
				setStrategyName(DefaultStrategy.ReRoute).
				setWeight(.1).setDisableAfter(50));
		config.strategy().addStrategySettings(new StrategySettings().
				setStrategyName(DefaultSelector.ChangeExpBeta).
				setWeight(.9));
		config.strategy().setPlanSelectorForRemoval(DefaultPlansRemover.WorstPlanSelector.toString());
		
		//Set population location
		
		config.plans().setInputFile("population.xml");
		
		//set network file 
		config.network().setInputFile("network.xml");

		//Add activity parameters
		
		config.planCalcScore().addActivityParams(new ActivityParams("home")
				.setScoringThisActivityAtAll(true)
				.setTypicalDuration(8*3600)
				.setMinimalDuration(0.6*8*3600));
		config.planCalcScore().addActivityParams(new ActivityParams("work")
				.setScoringThisActivityAtAll(true)
				.setTypicalDuration(8*3600)
				.setMinimalDuration(0.6*8*3600));
		
		config.qsim().setStartTime(4*3600);
		config.qsim().setEndTime(10*3600);
		
		config.controler().setFirstIteration(0);
		config.controler().setLastIteration(100);
		
		config.global().setNumberOfThreads(2);
		
		config.controler().setOutputDirectory("output");
		config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);
		
		new ConfigWriter(config).write("config.xml");
	}
}
