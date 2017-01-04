/*
 * Copyright (C) 2017 Paulius Danenas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.metaopt.strategy;

import net.metaopt.strategy.StateResult.ProcessState;
import net.metaopt.swarm.ConfigurationException;
import net.metaopt.swarm.FitnessFunction;
import net.metaopt.swarm.Particle;
import net.metaopt.swarm.pso.Swarm;

/**
 * The swarm is iteratively trained until no further improvement can be observed
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public class ConvergenceStrategy extends OptimizationStrategy  {

    /* The number of search iterations, after which the search is terminated, if no improvement is observed */
    protected int numIterConverge = 50;
    protected Swarm swarm;
    protected int totalIterations = -1;
    protected boolean endOpt = false;
    
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
        totalIterations = 0;
        swarm.evolve();
        double currBest = swarm.getBestFitness();
        bestFitness = currBest;
        StateResult state = new StateResult(swarm, bestFitness, bestSolution, ProcessState.STARTED);
        setChanged();
        notifyObservers(state);
        boolean stop = false;
        while (!stop && !endOpt) {
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
            state = new StateResult(swarm, bestFitness, bestSolution, ProcessState.EXECUTING);
            state.currentIteration = numIter;
            state.totalIterations = totalIterations;
            setChanged();
            notifyObservers(state);
            //System.out.println("Iteration: " + numIter + ", best fitness: " + bestFitness);
        }
        bestSolution = swarm.getBestPosition();
        state = new StateResult(swarm, bestFitness, bestSolution, ProcessState.FINISHED);
        state.totalIterations = totalIterations;
        setChanged();
        notifyObservers(state);
    }
    
    public void stopOptimization() {
        endOpt = true;
    }

    public int getNumIterationsConverge() {
        return numIterConverge;
    }

    /**
     * Set the number of iterations which are run until convergence. Note that this is reset each time when more optimal solution is found
     * @param numIterConverge The number of convergence iterations
     */
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
    * Return the total number of iterations, which were needed to obtain optimum solution
    */
    public int getTotalIterations() {
        return totalIterations;
    }
    
}
