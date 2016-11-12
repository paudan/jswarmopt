package net.metaopt.swarm.pso.constraints;

import net.metaopt.swarm.Particle;

/**
 * Implements Personal best (PBest) settings (the particle is repositioned onto its personal best)
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public class PersonalBest implements ConstraintsHandler {
    
    @Override
    public double getVelocity(Particle particle, int index, double[] boundingVelocity) {
        return boundingVelocity[index];
    }

    @Override
    public double getPosition(Particle particle, int index, double[] boundingPosition) {
        return particle.getBestPosition()[index];
    }
    
}
