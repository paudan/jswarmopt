package net.optim.pso;

import net.metaopt.swarm.FitnessFunction;
import net.metaopt.swarm.Particle;
import net.metaopt.swarm.pso.Swarm;

/**
 * PSO test on Rosenbrock function
 * @author Paulius Danėnas, <danpaulius@gmail.com>
 */
public class RosenbrockFunction {
    
    public static class RosenbrockParticle extends Particle {

        public RosenbrockParticle() {
            super(2);
        }
    }
    
    public void optimize() {
        
        System.out.println("Begin: Example Rosenbrock\n");
        Swarm swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, new RosenbrockParticle(), new FitnessFunction(false) {

            @Override
            public double evaluate(double[] position) {
                double x1 = position[0];
		double x2 = position[1];
		return (100 * Math.pow((x2 - Math.pow(x1, 2)), 2) + Math.pow((1 - x1), 2)); 
            }
                    
        });
        // Set position (and velocity) constraints. I.e.: where to look for solutions
        swarm.setInertia(0.99); // Tuned up intertia (just a little)
        swarm.setMaxPosition(30);
        swarm.setMinPosition(0);
        swarm.setMaxMinVelocity(0.1);

        int numberOfIterations = 10000; // Added some more iterations

        // Optimize (and time it)
        for (int i = 0; i < numberOfIterations; i++)
                swarm.evolve();

        // Print en results
        System.out.println(swarm.toStringStats()); 
    }
    
    public static void main(String[] args) { 
        new RosenbrockFunction().optimize();
    }
    
}
