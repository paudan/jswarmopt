package net.metaopt.swarm.pso.particle;

import net.metaopt.swarm.Particle;
import net.metaopt.swarm.pso.Swarm;

/**
 * Particle update strategy
 * 
 * Every Swarm.evolve() iteration the following methods are called
 * 		- begin(Swarm) : Once at the beginning of each iteration
 * 		- update(Swarm,Particle) : Once for each particle
 * 		- end(Swarm) : Once at the end of each iteration
 * 
 * @author Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public interface ParticleUpdate {

	public void begin(Swarm swarm);

	public void end(Swarm swarm);

	public void update(Swarm swarm, Particle particle);
}
