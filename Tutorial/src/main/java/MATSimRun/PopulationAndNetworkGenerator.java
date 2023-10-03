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
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;



public class PopulationAndNetworkGenerator {
	public static void main(String[] args) {
		Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
		Population population = scenario.getPopulation();
		Network network = scenario.getNetwork();
		PopulationFactory popFac = population.getFactory();
		NetworkFactory netFac = network.getFactory();
		
		//Generate the network first
		
		Node o1 = netFac.createNode(Id.create("o1", Node.class), new Coord(0,510));
		Node o2 = netFac.createNode(Id.create("o2", Node.class), new Coord(510,0));
		Node n1 = netFac.createNode(Id.create("1", Node.class), new Coord(10,510));
		Node n2 = netFac.createNode(Id.create("2", Node.class), new Coord(510,10));
		Node n3 = netFac.createNode(Id.create("3", Node.class), new Coord(1010,510));
		Node n4 = netFac.createNode(Id.create("4", Node.class), new Coord(1510,510));
		Node d = netFac.createNode(Id.create("d", Node.class), new Coord(1520,510));
		
		network.addNode(o1);
		network.addNode(o2);
		network.addNode(n1);
		network.addNode(n2);
		network.addNode(n3);
		network.addNode(n4);
		network.addNode(d);
		
		Set<String> mode = new HashSet<>();
		mode.add("car");
		
		
		Link lo11 = netFac.createLink(Id.create("o1_1", Link.class), o1, n1);
		lo11.setCapacity(1800);
		lo11.setFreespeed(14);
		lo11.setAllowedModes(mode);
		lo11.setLength(10);
		
		Link lo22 = netFac.createLink(Id.create("o2_2", Link.class), o2, n2);
		lo22.setCapacity(1800);
		lo22.setFreespeed(14);
		lo22.setAllowedModes(mode);
		lo22.setLength(10);
		
		Link l12 = netFac.createLink(Id.create("1_2", Link.class), n1, n2);
		l12.setCapacity(1800);
		l12.setFreespeed(14);
		l12.setAllowedModes(mode);
		l12.setLength(707);
		
		Link l13 = netFac.createLink(Id.create("1_3", Link.class), n1, n3);
		l13.setCapacity(1800);
		l13.setFreespeed(14);
		l13.setAllowedModes(mode);
		l13.setLength(500);
		
		Link l23 = netFac.createLink(Id.create("2_3", Link.class), n2, n3);
		l23.setCapacity(1800);
		l23.setFreespeed(14);
		l23.setAllowedModes(mode);
		l23.setLength(707);
		
		Link l34 = netFac.createLink(Id.create("3_4", Link.class), n3, n4);
		l34.setCapacity(1800);
		l34.setFreespeed(14);
		l34.setAllowedModes(mode);
		l34.setLength(500);
		
		Link l4d = netFac.createLink(Id.create("4_d", Link.class), n4, d);
		l4d.setCapacity(1800);
		l4d.setFreespeed(14);
		l4d.setAllowedModes(mode);
		l4d.setLength(10);
		
		network.addLink(lo11);
		network.addLink(lo22);
		network.addLink(l12);
		network.addLink(l23);
		network.addLink(l13);
		network.addLink(l34);
		network.addLink(l4d);
		
		new NetworkWriter(network).write("network.xml");
		
		//Generate the population
		//o1-d
		for(int i = 0; i<50; i++) {
			Person person = popFac.createPerson(Id.create("p_o1_d_"+i, Person.class));
			Plan plan = popFac.createPlan();
			Activity act1 = popFac.createActivityFromCoord("home", new Coord(0,510));
			act1.setEndTime(8*3600);
			
			Leg leg = popFac.createLeg("car");
			Activity act2 = popFac.createActivityFromCoord("work", new Coord(1520,510));
			plan.addActivity(act1);
			plan.addLeg(leg);	
			plan.addActivity(act2);
			person.addPlan(plan);
			population.addPerson(person);
		}
		
		//o2-d
		for(int i = 0; i<50; i++) {
			Person person = popFac.createPerson(Id.create("p_o2_d_"+i, Person.class));
			Plan plan = popFac.createPlan();
			Activity act1 = popFac.createActivityFromCoord("home", new Coord(510,0));
			act1.setEndTime(8*3600);
			Leg leg = popFac.createLeg("car");
			Activity act2 = popFac.createActivityFromCoord("work", new Coord(1520,510));
			plan.addActivity(act1);
			plan.addLeg(leg);	
			plan.addActivity(act2);
			person.addPlan(plan);
			population.addPerson(person);
		}
		
		
		
		new PopulationWriter(population).write("population.xml");
	}
}
