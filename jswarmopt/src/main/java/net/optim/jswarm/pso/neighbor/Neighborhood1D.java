package net.optim.jswarm.pso.neighbor;

import net.optim.jswarm.Particle;
import java.util.ArrayList;
import java.util.Collection;
import net.optim.jswarm.pso.Swarm;

/**
 * A one-dimensional neighborhood of particles
 * @author Paulius Danėnas <danpaulius@gmail.com>, original code by Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public class Neighborhood1D extends Neighborhood {

    private int size;
    private boolean circular;
    private ArrayList<Particle> array1d;

    /**
     * Create a 1-dimensional neighborhood (all particles have 2 neighbors: 1 to the left, 1 to the right)
     * @param size     : How many particles to each side do we consider? (total neighborhood is 2*size)
     * @param circular : If {@code true}, the first particle and the last particles are neighbors
     */
    public Neighborhood1D(int size, boolean circular) {
        super();
        this.size = size;
        this.circular = circular;
        array1d = new ArrayList<Particle>();
    }

    @Override
    public Collection<Particle> calcNeighbours(Particle p) {
        ArrayList<Particle> neigh = new ArrayList<>();
        int idx = findIndex(p); // Find this particle's index

        // Add all the particles in the neighborhood
        for (int i = idx - size; i <= (idx + size); i++) {
            Particle pp = getParticle(i);
            if ((pp != null) && (pp != p))
                neigh.add(pp); // Do not add 'p'
        }

        return neigh;
    }

    /**
     * Find a particle's number
     * @param p
     * @return
     */
    int findIndex(Particle p) {
        for (int i = 0; i < array1d.size(); i++) {
            if (p == array1d.get(i))
                return i;
        }
        throw new RuntimeException("Cannot find particle. This should never happen!\n" + p);
    }

    /**
     * Get {@link Particle} by given index
     * @param idx   The index of the particle
     * @return  The {@link Particle} instance
     */
    public Particle getParticle(int idx) {
        int arraySize = array1d.size();
        if ((idx >= 0) && (idx < array1d.size()))
            return array1d.get(idx); 
        if (!circular)
            return null; // Not circular? => Nothing to do

        if (idx >= arraySize)
            idx = idx % arraySize;
        else if (idx < 0)
            idx += arraySize; 

        return array1d.get(idx);
    }

    @Override
    public void init(Swarm swarm) {
        for (Particle p : swarm.getParticles())
            array1d.add(p);
        super.init(swarm); 
    }
}
