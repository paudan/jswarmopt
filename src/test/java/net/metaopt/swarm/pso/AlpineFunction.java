package net.metaopt.swarm.pso;

import net.metaopt.swarm.FitnessFunction;

/**
 * Maximize Alpine function,  http://clerc.maurice.free.fr/pso/Alpine/Alpine_Function.htm
 * 	
 * 		f( x1 , x2 ) = sin( x2 ) * sin( x2 ) * Sqrt( x1 * x2 )
 * 	
 * @author Įlvaro Jaramillo Duque <aduque@inescporto.pt>
 */
public class AlpineFunction {
    
    public void optimize() {
        
        System.out.println("Alpine function example\n");
        Swarm swarm = new RepulsiveSwarm(RepulsiveSwarm.DEFAULT_NUMBER_OF_PARTICLES, new Particle(2), new FitnessFunction(false) {

            @Override
            public double evaluate(double[] position) {
		return Math.sin(position[0]) * Math.sin(position[1]) * Math.sqrt(position[0] * position[1]); 
            }
                    
        });
        swarm.setInertia(0.95);
        swarm.setMaxPosition(100);
        swarm.setMinPosition(0);
        swarm.setMaxMinVelocity(0.1);

        int numberOfIterations = 10000;

        for( int i = 0; i < numberOfIterations; i++ )
            swarm.evolve();

        System.out.println(swarm.toStringStats());
    }
    
    public static void main(String[] args) { 
        new AlpineFunction().optimize();
    }
    
}
