package oodp.modules;

/**
 * IModule interface that all modules (boundary) should implement
 */
public interface IModule {
	/**
	 * All modules should implement this method to receive the provided user input
	 * @param choice The integer user input
	 */
	public abstract void process(int choice);
}
