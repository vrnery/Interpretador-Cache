/**
 * 
 */
package br.pucrs.interpretador.cache;

/**
 * @author vanderson
 *
 */
public class MapDirectBlock implements L1_Cache {

	private Linha[] cache;
	private int bit_tag;
	private int bit_line;
	private int bit_dados;
	private int bit_palavra;
	private int bit_select;
	
	public void config(int bit_tag, int bit_line, int bit_dados, int bit_palavra, int bit_select) {
		this.bit_tag = bit_tag;
		this.bit_line = bit_line;
		this.cache = new Linha[(int) Math.pow(2, this.bit_line)];
		this.bit_dados = bit_dados;
		this.bit_palavra = bit_palavra;
		this.bit_select = bit_select;
		for (int i=0; i<this.cache.length; i++) {
			this.cache[i] = new Linha(this.bit_tag, this.bit_dados, this.bit_palavra, this.bit_select);
		}
	}
	
	@Override
	public int sizeCache() {
		// TODO Auto-generated method stub
		// linha * linhas
		return sizeLineCache() * (int) Math.pow(2, this.bit_line);
	}

	@Override
	public int sizeLineCache() {
		// TODO Auto-generated method stub
		// linha = (tag + bloco * palavra)
		return this.bit_tag + ((int) Math.pow(2, this.bit_dados) * this.bit_palavra);
	}

	@Override
	public String showCache() {
		// TODO Auto-generated method stub
		StringBuilder linha = new StringBuilder();
		linha.append(ANSI.RESET + "Cache: " + sizeCache() + "\n"
				+ "Direta [[TAG][DADOS]]\n");
		for (int i=0; i<this.cache.length; i++) {
			linha.append(ANSI.RESET + "["
					+ this.cache[i].toString()
					+ ANSI.RESET + "]\n");
			
		}
		return linha.toString();
	}

	@Override
	public String ajusteBits(String bits) {
		// TODO Auto-generated method stub
		int t = this.bit_tag + this.bit_line + this.bit_dados + this.bit_select;
		StringBuilder ajusBits = new StringBuilder();
		ajusBits.append(String.format("%0" + t + "d", 0));
		ajusBits.append(bits);
		return ajusBits.toString().substring(ajusBits.length() - t);
	}
	
	@Override
	public boolean isLineCache(String bits) {
		// TODO Auto-generated method stub
		bits = ajusteBits(bits);
		int nlinha = Integer.parseInt(bits.substring(this.bit_tag, (this.bit_tag + this.bit_line)), 2);
		boolean ret = false;
		StringBuilder linha = new StringBuilder();
		linha.append(ANSI.RESET + "Direta [[TAG][DADOS]]\n");
		for (int i=0; i<this.cache.length; i++) {
			linha.append(ANSI.RESET + "[");
			if (i == nlinha) {
				if (this.cache[i].isTag(bits)) {
					ret = true;
					linha.append(this.cache[i].getLinha(bits));
				} else {
					this.cache[i].setLinha(bits);
					linha.append(this.cache[i].toString());
				}
			} else
				linha.append(this.cache[i].toString());
			linha.append(ANSI.RESET + "]\n");
		}
		System.out.println(linha.toString());
		return ret;
	}

	@Override
	public String printBits(String bits) {
		// TODO Auto-generated method stub
		bits = ajusteBits(bits);
		StringBuilder printend = new StringBuilder("Endereço binario: ");
		if (this.bit_tag > 0) {
			printend.append(ANSI.YELLOW + bits.substring(0, this.bit_tag));
			bits = bits.substring(this.bit_tag);
		}
		if (this.bit_line > 0) {
			printend.append(ANSI.WHITE + bits.substring(0, this.bit_line));
			bits = bits.substring(this.bit_line);
		}
		if (this.bit_dados > 0) {
			printend.append(ANSI.RED + bits.substring(0, this.bit_dados));
			bits = bits.substring(this.bit_dados);
		}
		if (this.bit_select > 0) {
			printend.append(ANSI.PURPLE + bits.substring(0, this.bit_select));
			bits = bits.substring(this.bit_select);
		}
		printend.append("\n" + ANSI.YELLOW + "TAG "
				+ ANSI.WHITE + "LINHA "
				+ ANSI.RED + "DADOS "
				+ ANSI.PURPLE + "SEPARADOR BYTES");
		return printend.toString();
	}

	private class Linha {
		private Tag tag;
		private Dados dados;
		
		public Linha(int bit_tag, int bit_dados, int bit_palavra, int bit_select) {
			this.tag = new Tag(bit_tag);
			this.dados = new Dados(bit_dados, bit_palavra, bit_select);
		}
		
		public boolean isTag(String bits) {
			return this.tag.isTag(bits);
		}
		
		public String getLinha(String bits) {
			StringBuilder linha = new StringBuilder();
			linha.append(this.tag.getTag());
			linha.append(this.dados.getPalavras(bits));
			return linha.toString();
		}
		
		public void setLinha(String bits) {
			this.tag.setTag(bits);
			this.dados.setPalavras(bits);
		}

		@Override
		public String toString() {
			return tag.toString() + dados.toString();
		}
	}

	@Override
	public int sizeEndMemoria() {
		// TODO Auto-generated method stub
		return (int) Math.pow(2, (this.bit_tag + this.bit_line + this.bit_dados + this.bit_select));
	}

}
