package net.metaopt.swarm;

/**
 * Swarm configuration exception
 * @author Paulius Danėnas, <danpaulius@gmail.com>
 */
public class ConfigurationException extends Exception {

    public ConfigurationException() {
    }

    public ConfigurationException(String msg) {
        super(msg);
    }
}
