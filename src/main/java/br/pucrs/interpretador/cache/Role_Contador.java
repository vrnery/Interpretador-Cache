/**
 * 
 */
package br.pucrs.interpretador.cache;

/**
 * @author vanderson
 *
 */
public class Role_Contador {

	private int index;
	private int max;
	
	public Role_Contador(int max) {
		this.index = 0;
		this.max = max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public int getIndex() {
		int ret = this.index;
		this.index++;
		if (this.index >= this.max)
			this.index = 0;
		return ret;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
}
