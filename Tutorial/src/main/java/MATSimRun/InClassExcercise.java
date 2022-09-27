package MATSimRun;

import java.util.HashSet;
import java.util.Set;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.ConfigWriter;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.config.groups.StrategyConfigGroup.StrategySettings;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule.DefaultPlansRemover;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule.DefaultSelector;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule.DefaultStrategy;
import org.matsim.core.scenario.ScenarioUtils;

public class InClassExcercise {
	public static void main(String[] args) {
		Config config = ConfigUtils.createConfig();
		Scenario scenario = ScenarioUtils.createScenario(config);
		Network network = scenario.getNetwork();
		NetworkFactory netFac = network.getFactory();
		
		Node o1 = netFac.createNode(Id.create("o1", Node.class), 
				new Coord(0,510));
		Node n1 = netFac.createNode(Id.create("1", Node.class), 
				new Coord(10,510));
		Node n2 = netFac.createNode(Id.create("2", Node.class), 
				new Coord(510,10));
		Node n3 = netFac.createNode(Id.create("3", Node.class), 
				new Coord(1010,510));
		Node n4 = netFac.createNode(Id.create("4", Node.class), 
				new Coord(1510,510));
		Node o2 = netFac.createNode(Id.create("o2", Node.class), 
				new Coord(510,0));
		Node d = netFac.createNode(Id.create("d", Node.class), 
				new Coord(1520,510));
		
		network.addNode(o1);
		network.addNode(o2);
		network.addNode(n1);
		network.addNode(n2);
		network.addNode(n3);
		network.addNode(n4);
		network.addNode(d);
		
		Set<String> modes = new HashSet<>();
		modes.add("car");
		
		Link lo11 = netFac.createLink(Id.create("o1_1", Link.class),
				o1, n1);
		lo11.setCapacity(300);
		lo11.setFreespeed(14);
		lo11.setAllowedModes(modes);
		lo11.setLength(10);
		network.addLink(lo11);
		
		Link lo22 = netFac.createLink(Id.create("o2_2", Link.class),
				o2, n2);
		lo22.setCapacity(300);
		lo22.setFreespeed(14);
		lo22.setAllowedModes(modes);
		lo22.setLength(10);
		network.addLink(lo22);
		
		Link l12 = netFac.createLink(Id.create("1_2", Link.class),
				n1, n2);
		l12.setCapacity(300);
		l12.setFreespeed(14);
		l12.setAllowedModes(modes);
		l12.setLength(707);
		network.addLink(l12);
		
		Link l23 = netFac.createLink(Id.create("2_3", Link.class),
				n2, n3);
		l23.setCapacity(300);
		l23.setFreespeed(14);
		l23.setAllowedModes(modes);
		l23.setLength(707);
		network.addLink(l23);
		
		Link l13 = netFac.createLink(Id.create("1_3", Link.class),
				n1, n3);
		l13.setCapacity(300);
		l13.setFreespeed(14);
		l13.setAllowedModes(modes);
		l13.setLength(500);
		network.addLink(l13);
		
		Link l34 = netFac.createLink(Id.create("3_4", Link.class),
				n3, n4);
		l34.setCapacity(300);
		l34.setFreespeed(14);
		l34.setAllowedModes(modes);
		l34.setLength(500);
		network.addLink(l34);
		
		Link l4d = netFac.createLink(Id.create("4_d", Link.class),
				n4, d);
		l13.setCapacity(300);
		l13.setFreespeed(14);
		l13.setAllowedModes(modes);
		l13.setLength(10);
		network.addLink(l4d);
		
		new NetworkWriter(network).write("network.xml");
		
		Population population = scenario.getPopulation();
		PopulationFactory popFac = population.getFactory();
		
		for(int i = 0;i<50;i++) {
			Person person = popFac.createPerson(Id.create("o1_d_"+i, Person.class));
			Plan plan = popFac.createPlan();
			Activity a1 = popFac.createActivityFromCoord("home", new Coord(0,510));
			a1.setEndTime(8*3600);
			Leg leg = popFac.createLeg("car");
			Activity a2 = popFac.createActivityFromCoord("work", new Coord(1520,510));
			plan.addActivity(a1);
			plan.addLeg(leg);
			plan.addActivity(a2);
			person.addPlan(plan);
			population.addPerson(person);
		}
		for(int i = 0;i<60;i++) {
			Person person = popFac.createPerson(Id.create("o2_d_"+i, Person.class));
			Plan plan = popFac.createPlan();
			Activity a1 = popFac.createActivityFromCoord("home", new Coord(510,0));
			a1.setEndTime(8*3600);
			Leg leg = popFac.createLeg("car");
			Activity a2 = popFac.createActivityFromCoord("work", new Coord(1520,510));
			plan.addActivity(a1);
			plan.addLeg(leg);
			plan.addActivity(a2);
			person.addPlan(plan);
			population.addPerson(person);
		}
		
		new PopulationWriter(population).write("population.xml");
		
		ActivityParams a1 =new ActivityParams("home");
		a1.setTypicalDuration(8*3600);
		a1.setMinimalDuration(4*3600);
		config.planCalcScore().addActivityParams(a1);
		
		ActivityParams a2 =new ActivityParams("work");
		a2.setTypicalDuration(8*3600);
		a2.setMinimalDuration(4*3600);
		config.planCalcScore().addActivityParams(a2);
		
		StrategySettings s1 = new StrategySettings();
		s1.setStrategyName(DefaultStrategy.ReRoute);
		s1.setWeight(0.1);
		s1.setDisableAfter(40);
		config.strategy().addStrategySettings(s1);
		
		StrategySettings s2 = new StrategySettings();
		s2.setStrategyName(DefaultSelector.ChangeExpBeta);
		s2.setWeight(0.9);
		config.strategy().addStrategySettings(s2);
		
		config.strategy().setPlanSelectorForRemoval(DefaultPlansRemover.
				WorstPlanSelector.toString());
		
		config.plans().setInputFile("population.xml");
		config.network().setInputFile("network.xml");
		
		config.qsim().setStartTime(7*3600);
		config.qsim().setEndTime(10*3600);
		
		config.controler().setLastIteration(50);
		config.global().setNumberOfThreads(2);
		
		config.controler().setOutputDirectory("output");
		
		config.controler().setOverwriteFileSetting(
				OverwriteFileSetting.deleteDirectoryIfExists);
		
		new ConfigWriter(config).write("config.xml");
		
		Scenario scenario2 = ScenarioUtils.loadScenario(config);
		Controler controler = new Controler(scenario2);
		controler.run();
	}

}
