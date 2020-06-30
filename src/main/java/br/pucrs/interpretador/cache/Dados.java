/**
 * 
 */
package br.pucrs.interpretador.cache;

/**
 * @author vanderson
 *
 */
public class Dados {
	private Palavra[] palavras;
	private int bit;
	private int bit_palavra;
	private int bit_select;
	
	public Dados(int bit_dados, int bit_palavra, int bit_select) {
		this.bit = bit_dados;
		this.bit_palavra = bit_palavra;
		this.bit_select = bit_select;
		if (this.bit < 1)
			this.palavras = new Palavra[1];
		else
			this.palavras = new Palavra[(int) Math.pow(2, this.bit)];
		for (int i=0; i<this.palavras.length; i++) {
			this.palavras[i] = new Palavra(this.bit_palavra, this.bit_select);
		}
	}
	
	public void setPalavras(String bits) {
		int parte_binario = (int) Math.pow(2, this.bit_select);
		int cont_parte;
		int contador = 0;
		String binario;
		String fixa = "";
		int bit_parte = 0;
		if (parte_binario > 0)
			bit_parte = (this.bit_palavra / parte_binario) - this.bit;
		else
			bit_parte = this.bit_palavra - this.bit;
		if (bit_parte > 0)
			fixa = bits.substring(bits.length() - bit_parte - this.bit_select, bits.length() - this.bit_select);
		for (int i=0; i<this.palavras.length; i++) {
			binario = "";
			cont_parte = 0;
			do {
				binario += fixa;
				binario += gerarPartePalavra(contador, this.bit);
				cont_parte++;
				contador++;
			} while (cont_parte < parte_binario);
			this.palavras[i].setPalavra(binario);
		}
	}
	
	public String getPalavras(String bits) {
		int p = Integer.parseInt(bits.substring(bits.length() - this.bit - this.bit_select, bits.length() - this.bit_select), 2);
		StringBuilder dados = new StringBuilder();
		for (int i=0; i<this.palavras.length; i++) {
			if (i == p)
				dados.append(this.palavras[i].getPalavra(bits));
			else
				dados.append(this.palavras[i]);
		}
		return ANSI.RED + "[" + dados.toString() + ANSI.RED + "]";
	}
	
	public String gerarPartePalavra(int contador, int bit_length) {
		String bits_contador = Integer.toBinaryString(contador);
		if (bits_contador.length() < bit_length)
			bits_contador = String.format("%0" + (bit_length - bits_contador.length()) + "d", 0) + bits_contador;
		return bits_contador;
	}

	@Override
	public String toString() {
		StringBuilder tos = new StringBuilder();
		for (Palavra palavra : palavras) {
			tos.append(palavra);
		}
		return ANSI.RED + "[" + tos.toString() + ANSI.RED + "]";
	}
}
