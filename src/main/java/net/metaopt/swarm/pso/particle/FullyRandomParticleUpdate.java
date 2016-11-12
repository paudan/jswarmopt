package net.metaopt.swarm.pso.particle;

import net.metaopt.swarm.Particle;
import net.metaopt.swarm.pso.Swarm;

/**
 * Particle update: Fully random approach
 * Note that rlocal and rother are randomly choosen for each particle and for each dimention
 * 
 * @author Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public class FullyRandomParticleUpdate implements ParticleUpdate {

    @Override
    public void update(Swarm swarm, Particle particle) {
            double position[] = particle.getPosition();
            double velocity[] = particle.getVelocity();
            double globalBestPosition[] = swarm.getBestPosition();
            double particleBestPosition[] = particle.getBestPosition();
            double neighBestPosition[] = swarm.getNeighborhoodBestPosition(particle);

            // Update velocity and position
            for (int i = 0; i < position.length; i++) {
                    // Update position
                    position[i] = position[i] + velocity[i];

                    // Update velocity
                    velocity[i] = swarm.getInertia() * velocity[i] // Inertia
                                    + Math.random() * swarm.getParticleIncrement() * (particleBestPosition[i] - position[i]) // Local best
                                    + Math.random() * swarm.getNeighborhoodIncrement() * (neighBestPosition[i] - position[i]) // Neighborhood best					
                                    + Math.random() * swarm.getGlobalIncrement() * (globalBestPosition[i] - position[i]); // Global best
            }
    }

    @Override
    public void begin(Swarm swarm) {
    }

    @Override
    public void end(Swarm swarm) {
    }
}
