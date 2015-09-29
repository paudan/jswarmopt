package net.optim.jswarm;

/**
 * Base Fitness Function
 * @author Paulius Danėnas, <danpaulius@gmail.com>, original code by Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public abstract class FitnessFunction {

    /** Should this function be maximized or minimized */
    boolean maximize;

    /** Default constructor */
    public FitnessFunction() {
        maximize = true; // Default: Maximize
    }

    /**
     * Constructor for {@link FitnessFunction}
     * @param maximize If set to {@code true}, the function will be maximized; otherwise, it will be minimized
     *                 Should we try to maximize or minimize this function?
     */
    public FitnessFunction(boolean maximize) {
        this.maximize = maximize;
    }

    /**
     * Abstract function to evaluate a particle at a given position. Should be implemented
     *
     * @param position : Particle's position
     * @return Fitness value for a particle
     */
    public abstract double evaluate(double position[]);

    /**
     * Evaluate a particle
     * @param particle : Particle to evaluate
     * @return Fitness value for a particle
     */
    public double evaluate(Particle particle) {
        double position[] = particle.getPosition();
        double fit = evaluate(position);
        particle.setFitness(fit, maximize);
        return fit;
    }

    /**
     * Compare two values, returned by fitness function
     * @param fitness
     * @param otherValue
     * @return {@code true} if {@code otherValue} is "better" than {@code fitness}
     */
    public boolean isBetterThan(double fitness, double otherValue) {
        if (maximize) {
            if (otherValue > fitness)
                return true;
        } else {
            if (otherValue < fitness)
                return true;
        }
        return false;
    }

    /** Are we maximizing this fitness function? */
    public boolean isMaximize() {
        return maximize;
    }

    /**
     * Set if the fitness function should be maximized
     * @param maximize If set to {@code true}, the fitness function will be maximized; otherwise, it will be minimized
     */
    public void setMaximize(boolean maximize) {
        this.maximize = maximize;
    }

}
