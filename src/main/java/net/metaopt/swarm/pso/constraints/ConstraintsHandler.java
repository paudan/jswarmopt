package net.metaopt.swarm.pso.constraints;

import net.metaopt.swarm.Particle;

/**
 * Interface for implementing bound handling problems
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public interface ConstraintsHandler {
    
    public double getVelocity(Particle particle, int index, double [] boundingVelocity);
    
    public double getPosition(Particle particle, int index, double [] boundingPosition);
}
