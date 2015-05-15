package net.optim.jswarm.pso.init;

import net.optim.jswarm.pso.Swarm;

/**
 * Initialization of all particles at the same point
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public class SinglePointInitialization implements GenericInitialization {

    private double initial[];
    private Swarm swarm;
    
    public SinglePointInitialization(double[] initial, Swarm swarm) {
        this.initial = initial;
        this.swarm = swarm;
    }

    @Override
    public double initPosition(int index) {
        return initial[index];
    }

    @Override
    public double initVelocity(int index) {
        double [] maxVelocity = swarm.getMaxVelocity();
        if (Double.isNaN(maxVelocity[index])) throw new RuntimeException("maxVelocity[" + index + "] is NaN!");
        if (Double.isInfinite(maxVelocity[index])) throw new RuntimeException("maxVelocity[" + index + "] is Infinite!");

        double [] minVelocity = swarm.getMinVelocity();
        if (Double.isNaN(minVelocity[index])) throw new RuntimeException("minVelocity[" + index + "] is NaN!");
        if (Double.isInfinite(minVelocity[index])) throw new RuntimeException("minVelocity[" + index + "] is Infinite!");
            
        return (maxVelocity[index] - minVelocity[index]) * Math.random() + minVelocity[index];
    }
    
}
