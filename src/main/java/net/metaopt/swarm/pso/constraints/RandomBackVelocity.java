package net.metaopt.swarm.pso.constraints;

import net.metaopt.swarm.Particle;

/**
 * Implements Random Back velocity modification
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public class RandomBackVelocity implements ConstraintsHandler {

    @Override
    public double getVelocity(Particle particle, int index, double[] boundingVelocity) {
        return -Math.random() * boundingVelocity[index];
    }

    @Override
    public double getPosition(Particle particle, int index, double[] boundingPosition) {
        return boundingPosition[index];
    }
    
}
