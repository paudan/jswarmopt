package net.optim.strategy;

import net.optim.jswarm.ConfigurationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.optim.jswarm.FitnessFunction;
import net.optim.jswarm.Particle;
import net.optim.jswarm.pso.Swarm;

/**
 * Simple "multiple swarms" training strategy. A total of n swarms are trained using a fixed number of iterations, and best global result is returned 
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public class MultipleRestartStrategy extends OptimizationStrategy {

    private Swarm swarm;
    private int numIterations = 20;
    private int numRestarts = 10;

    public MultipleRestartStrategy(int n, Swarm swarm) {
        this.swarm = swarm;
        this.fitness = swarm.getFitnessFunction();
        numRestarts = n;
    }

    public MultipleRestartStrategy(int n, Particle sample, FitnessFunction fitness) {
        swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, sample, fitness);
        this.fitness = fitness;
        this.numRestarts = n;
    }
    
    @Override
    public void optimize() throws ConfigurationException {
        if (swarm == null)
            throw new ConfigurationException("Swarms were not initialized");
        double [] best = new double[numRestarts];
        double[] [] bestSol = new double[numRestarts][];
        for (int i = 0; i < numRestarts; i++) {
            try {
                swarm = swarm.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(MultipleRestartStrategy.class.getName()).log(Level.SEVERE, null, ex);
                swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, swarm.getSampleParticle(), fitness);
            }
            for (int k = 0; k < numIterations; k++)
                swarm.evolve();
            best[i] = swarm.getBestFitness();
            bestSol[i] = swarm.getBestPosition();
            System.out.println("Iteration " + i + ": " + best[i]);
        }
        bestFitness = best[0];
        bestSolution = bestSol[0];
        if (numRestarts == 1)
            return;
        for (int i = 1; i < numRestarts; i++) {
            if (fitness.isMaximize()) {
                double nextBest = Math.max(bestFitness, best[i]);
                if (nextBest > bestFitness) {
                    bestFitness = nextBest;
                    bestSolution = bestSol[i];
                } 
            } else {
                double nextBest = Math.min(bestFitness, best[i]);
                if (nextBest < bestFitness) {
                    bestFitness = nextBest;
                    bestSolution = bestSol[i];
                }
            }
        }
    }
    
}
