package net.optim.jswarm.pso.particle;

import net.optim.jswarm.Particle;
import net.optim.jswarm.pso.Swarm;

/**
 * Particle update: Each particle selects an rlocal and rother 
 * independently from other particles' values
 * 
 * @author Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public class RandomByParticleUpdate implements ParticleUpdate {

    @Override
    public void update(Swarm swarm, Particle particle) {
        double position[] = particle.getPosition();
        double velocity[] = particle.getVelocity();
        double globalBestPosition[] = swarm.getBestPosition();
        double particleBestPosition[] = particle.getBestPosition();
        double neighBestPosition[] = swarm.getNeighborhoodBestPosition(particle);

        double rlocal = Math.random();
        double rneighborhood = Math.random();
        double rglobal = Math.random();

        for (int i = 0; i < position.length; i++) {
            position[i] = position[i] + velocity[i];
            velocity[i] = swarm.getInertia() * velocity[i] // Inertia
                + rlocal * swarm.getParticleIncrement() * (particleBestPosition[i] - position[i]) // Local best
                + rneighborhood * swarm.getNeighborhoodIncrement() * (neighBestPosition[i] - position[i]) // Neighborhood best					
                + rglobal * swarm.getGlobalIncrement() * (globalBestPosition[i] - position[i]); // Global best
        }
    }

    @Override
    public void begin(Swarm swarm) {
    }

    @Override
    public void end(Swarm swarm) {
    }
}
