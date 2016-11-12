package net.metaopt.swarm.pso.init;

import net.metaopt.swarm.pso.Swarm;

/**
 * Default (uniform initialization) 
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public class UniformInitialization implements GenericInitialization {
    private Swarm swarm;

    public UniformInitialization(Swarm swarm) {
        this.swarm = swarm;
    }
    
    @Override
    public double initPosition(int index) {
        double maxPosition[] = swarm.getMaxPosition();
        double minPosition[] = swarm.getMinPosition();
        
        if (Double.isNaN(maxPosition[index])) throw new RuntimeException("maxPosition[" + index + "] is NaN!");
        if (Double.isInfinite(maxPosition[index])) throw new RuntimeException("maxPosition[" + index + "] is Infinite!");

        if (Double.isNaN(minPosition[index])) throw new RuntimeException("minPosition[" + index + "] is NaN!");
        if (Double.isInfinite(minPosition[index])) throw new RuntimeException("minPosition[" + index + "] is Infinite!");
        
        return (maxPosition[index] - minPosition[index]) * Math.random() + minPosition[index];
    }

    @Override
    public double initVelocity(int index) {
        double minVelocity [] = swarm.getMinVelocity();
        double maxVelocity [] = swarm.getMaxVelocity();
        
        if (Double.isNaN(maxVelocity[index])) throw new RuntimeException("maxVelocity[" + index + "] is NaN!");
        if (Double.isInfinite(maxVelocity[index])) throw new RuntimeException("maxVelocity[" + index + "] is Infinite!");

        if (Double.isNaN(minVelocity[index])) throw new RuntimeException("minVelocity[" + index + "] is NaN!");
        if (Double.isInfinite(minVelocity[index])) throw new RuntimeException("minVelocity[" + index + "] is Infinite!");
            
        return (maxVelocity[index] - minVelocity[index]) * Math.random() + minVelocity[index];
    }
    
}
