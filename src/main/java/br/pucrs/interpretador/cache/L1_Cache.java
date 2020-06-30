/**
 * 
 */
package br.pucrs.interpretador.cache;

/**
 * @author vanderson
 *
 */
public interface L1_Cache {
	public int sizeCache();
	public int sizeLineCache();
	public int sizeEndMemoria();
	public String showCache();
	public String ajusteBits(String bits);
	public String printBits(String bits);
	public boolean isLineCache(String bits);
}
