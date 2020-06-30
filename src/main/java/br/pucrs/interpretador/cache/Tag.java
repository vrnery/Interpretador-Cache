/**
 * 
 */
package br.pucrs.interpretador.cache;

/**
 * @author vanderson
 *
 */
public class Tag {

	private String tag;
	private int bit;
	
	public Tag(int bit) {
		this.tag = "";
		this.bit = bit;
	}
	
	public void setTag(String tag) {
		this.tag = tag.substring(0, this.bit);
	}
	
	public boolean isTag(String tag) {
		return this.tag.equals(tag.substring(0, this.bit));
	}
	
	public String getTag() {
		return ANSI.YELLOW + "[" + tag + "]" + ANSI.RESET;
	}

	@Override
	public String toString() {
		return ANSI.GREEN + "[" + tag + "]" + ANSI.RESET;
	}
}
