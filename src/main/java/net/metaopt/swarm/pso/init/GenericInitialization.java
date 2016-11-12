package net.metaopt.swarm.pso.init;

/**
 * Interface to implement initial variable value initialization 
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public interface GenericInitialization {
    
    public double initPosition(int index);
    
    public double initVelocity(int index);
}
