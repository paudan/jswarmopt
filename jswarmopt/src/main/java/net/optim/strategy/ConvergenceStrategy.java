package net.optim.strategy;

import net.optim.jswarm.ConfigurationException;
import net.optim.jswarm.FitnessFunction;
import net.optim.jswarm.Particle;
import net.optim.jswarm.pso.Swarm;

/**
 * The swarm is iteratively trained until no further improvement can be observed
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public class ConvergenceStrategy extends OptimizationStrategy {

    /* The number of search iterations, after which the search is terminated, if no improvement is observed */
    private int numIterConverge = 50;
    private Swarm swarm;
    private int totalIterations = -1;
    
    public ConvergenceStrategy(Swarm swarm) {
        this.swarm = swarm;
        this.fitness = swarm.getFitnessFunction();
    }

    public ConvergenceStrategy(Particle sample, FitnessFunction fitness) {
        swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, sample, fitness);
        this.fitness = fitness;
    }
    
    @Override
    public void optimize() throws ConfigurationException {
        if (swarm == null)
            throw new ConfigurationException("Swarm was not initialized");
        int numIter = 0;
        swarm.evolve();
        double currBest = swarm.getBestFitness();
        bestFitness = currBest;
        boolean stop = false;
        while (!stop) {
            swarm.evolve();
            currBest = swarm.getBestFitness();
            boolean cond = fitness.isMaximize() ? currBest > bestFitness : currBest < bestFitness;
            if (cond) {
                bestFitness = currBest;
                numIter = 0;
            } else
                numIter++;
            totalIterations++;
            stop = numIter == numIterConverge;
            System.out.println("Iteration: " + numIter + ", best fitness: " + bestFitness);
        }
        bestSolution = swarm.getBestPosition();
    }

    public int getNumIterationsConverge() {
        return numIterConverge;
    }

    public void setNumIterationsConverge(int numIterConverge) {
        this.numIterConverge = numIterConverge;
    }

    public Swarm getSwarm() {
        return swarm;
    }

    public void setSwarm(Swarm swarm) {
        this.swarm = swarm;
    }

    /* 
    * Return total number of iterations, needed to obtain optima
    */
    public int getTotalIterations() {
        return totalIterations;
    }
    
}
