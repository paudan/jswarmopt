package net.optim.strategy;

import net.optim.jswarm.ConfigurationException;
import net.optim.jswarm.FitnessFunction;

/**
 * Abstract class for optimization strategy implementation 
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public abstract class OptimizationStrategy {
    
    protected double bestFitness = Double.NaN;
    protected double [] bestSolution = null;
    protected FitnessFunction fitness = null;
            
    public double getBestFitness() {
        return bestFitness;
    }
    
    public double [] getBestSolution() {
        return bestSolution;
    }
    
    public abstract void optimize() throws ConfigurationException;
}
