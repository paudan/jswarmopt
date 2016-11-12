package net.metaopt.swarm.pso.particle;

import net.metaopt.swarm.Particle;
import net.metaopt.swarm.pso.Swarm;

/**
 * Particle update strategy
 * 
 * Every Swarm.evolve() itereation the following methods are called
 * 		- begin(Swarm) : Once at the begining of each iteration
 * 		- update(Swarm,Particle) : Once for each particle
 * 		- end(Swarm) : Once at the end of each iteration
 * 
 * @author Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public class SimpleParticleUpdate implements ParticleUpdate {

    /** Random vector for local update */
    double rlocal[];
    /** Random vector for global update */
    double rglobal[];
    /** Random vector for neighborhood update */
    double rneighborhood[];

    /**
     * Constructor 
     * @param particle : Sample of particles that will be updated later
     */
    public SimpleParticleUpdate(Particle particle) {
        rlocal = new double[particle.getDimension()];
        rglobal = new double[particle.getDimension()];
        rneighborhood = new double[particle.getDimension()];
    }

    @Override
    public void begin(Swarm swarm) {
        int i, dim = swarm.getSampleParticle().getDimension();
        for (i = 0; i < dim; i++) {
            rlocal[i] = Math.random();
            rglobal[i] = Math.random();
            rneighborhood[i] = Math.random();
        }
    }

    @Override
    public void end(Swarm swarm) {
    }

    @Override
    public void update(Swarm swarm, Particle particle) {
        double position[] = particle.getPosition();
        double velocity[] = particle.getVelocity();
        double globalBestPosition[] = swarm.getBestPosition();
        double particleBestPosition[] = particle.getBestPosition();
        double neighBestPosition[] = swarm.getNeighborhoodBestPosition(particle);

        // Update velocity and position
        for (int i = 0; i < position.length; i++) {
            // Update velocity
            velocity[i] = swarm.getInertia() * velocity[i] // Inertia
                + rlocal[i] * swarm.getParticleIncrement() * (particleBestPosition[i] - position[i]) // Local best
                + rneighborhood[i] * swarm.getNeighborhoodIncrement() * (neighBestPosition[i] - position[i]) // Neighborhood best					
                + rglobal[i] * swarm.getGlobalIncrement() * (globalBestPosition[i] - position[i]); // Global best
            // Update position
            position[i] += velocity[i];
        }
    }
}
