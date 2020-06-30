/**
 * 
 */
package br.pucrs.interpretador.cache;

/**
 * @author vanderson
 *
 */
public class Palavra {

	private String palavra;
	private int bit_palavra;
	private int bit_select;

	public Palavra(int size_bit_word, int size_bit_select) {
		this.bit_palavra = size_bit_word;
		this.bit_select = size_bit_select;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra.substring(palavra.length() - this.bit_palavra);
	}

	public String getPalavra(String bits) {
		int mux = 0;
		int mult = this.bit_palavra;
		if (this.bit_select > 0) {
			mult = this.bit_palavra / (int) Math.pow(2, this.bit_select);
			mux = Integer.parseInt(bits.substring(bits.length() - this.bit_select), 2);
		}
		int p0 = mult * mux;
		int p1 = p0 + mult;
		StringBuilder selecionado = new StringBuilder();
		selecionado.append(ANSI.CYAN + "[" + this.palavra.substring(0, p0));
		selecionado.append(ANSI.YELLOW + this.palavra.substring(p0, p1));
		selecionado.append(ANSI.CYAN + this.palavra.substring(p1) + "]");
		return selecionado.toString();
	}

//	public boolean isPalavra(String palavra) {
//		return (this.palavra.equals(palavra));
//	}
	
//	public int sizePalavra() {
//		return this.palavra.length();
//	}

	@Override
	public String toString() {
		return ANSI.CYAN + "[" + this.palavra + "]";
	}

//	public Object show() {
//		// TODO Auto-generated method stub
//		return String.format("%0"+this.bit_palavra+"d", 0);
//	}

//	public Object showSelect(String bit) {
//		// TODO Auto-generated method stub
//		if (this.bit_select > 0) {
//			int t = (int) Math.pow(2, this.bit_select);
//			t = this.bit_palavra / t;
//			int b = (Integer.parseInt(bit, 2) * t);
//			StringBuilder ret = new StringBuilder();
//			ret.append(ANSI.BLUE + "[" + this.palavra.substring(0, b));
//			ret.append(ANSI.WHITE + this.palavra.substring(b, b+t));
//			ret.append(ANSI.BLUE + this.palavra.substring(b+t) + "]");
//			return ret.toString();
//		} else {
//			return ANSI.BLUE + "[" + ANSI.WHITE + this.palavra + ANSI.BLUE + "]";
//		}
//	}

}
