/*
 * Copyright (C) 2017 Paulius Danenas <danpaulius@gmail.com>
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

import net.metaopt.swarm.ConfigurationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.metaopt.strategy.StateResult.ProcessState;
import net.metaopt.swarm.FitnessFunction;
import net.metaopt.swarm.Particle;
import net.metaopt.swarm.pso.Swarm;

/**
 * Simple "multiple swarms" training strategy. A total of n swarms are trained
 * using a fixed number of iterations, and best global result is returned
 */
public class MultipleRestartStrategy extends OptimizationStrategy {

    protected Swarm swarm;
    protected int numIterations = 20;
    protected int numRestarts = 10;

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
        double[] best = new double[numRestarts];
        double[][] bestSol = new double[numRestarts][];
        for (int i = 0; i < numRestarts; i++) {
            try {
                swarm = swarm.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(MultipleRestartStrategy.class.getName()).log(Level.SEVERE, null, ex);
                swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, swarm.getSampleParticle(), fitness);
            }
            StateResult state = new StateResult(swarm, ProcessState.STARTED);
            setChanged();
            notifyObservers(state);
            for (int k = 0; k < numIterations; k++)
                swarm.evolve();
            best[i] = swarm.getBestFitness();
            bestSol[i] = swarm.getBestPosition();
            state = new StateResult(swarm, best[i], bestSol[i], ProcessState.EXECUTING);
            state.numRuns = i;
            state.totalIterations = numIterations;
            setChanged();
            notifyObservers(state);
            //System.out.println("Iteration " + i + ": " + best[i]);
        }
        bestFitness = best[0];
        bestSolution = bestSol[0];
        if (numRestarts == 1)
            return;
        for (int i = 1; i < numRestarts; i++)
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
        StateResult state = new StateResult(swarm, bestFitness, bestSolution, ProcessState.FINISHED);
        state.numRuns = numRestarts;
        setChanged();
        notifyObservers(state);
    }

}
