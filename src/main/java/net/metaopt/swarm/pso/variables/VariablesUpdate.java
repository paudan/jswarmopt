package net.metaopt.swarm.pso.variables;

import net.metaopt.swarm.pso.Swarm;

/**
 * Swarm variables update
 * Every Swarm.evolve() iteration, update() is called 
 * 
 * @author Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public interface VariablesUpdate {

    public void update(Swarm swarm);
}
