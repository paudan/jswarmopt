package net.optim.jswarm.pso.variables;

import net.optim.jswarm.pso.Swarm;

/**
 * Swarm variables update
 * Every {@link Swarm#evolve()} iteration, update() is called 
 * 
 * @author Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public interface VariablesUpdate {

    public void update(Swarm swarm);
}
