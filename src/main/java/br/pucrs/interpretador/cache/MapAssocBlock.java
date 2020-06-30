/**
 * 
 */
package br.pucrs.interpretador.cache;

/**
 * @author vanderson
 *
 */
public class MapAssocBlock implements L1_Cache {

	private Role_Contador contador;
	private Tag[] assoc;
	private Dados[] cache;
	private int bit_tag;
	private int bit_line;
	private int bit_dados;
	private int bit_palavra;
	private int bit_select;

	public void config(int bit_tag, int bit_line, int bit_dados, int bit_palavra, int bit_select) {
		this.bit_tag = bit_tag;
		this.bit_line = bit_line;
		this.contador = new Role_Contador((int) Math.pow(2, this.bit_line));
		this.bit_dados = bit_dados;
		this.bit_palavra = bit_palavra;
		this.bit_select = bit_select;
		this.assoc = new Tag[(int) Math.pow(2, this.bit_line)];
		for (int i=0; i<this.assoc.length; i++) {
			this.assoc[i] = new Tag(bit_tag);
		}
		this.cache = new Dados[(int) Math.pow(2, bit_line)];
		for (int i=0; i<this.cache.length; i++) {
			this.cache[i] = new Dados(this.bit_dados, this.bit_palavra, this.bit_select);
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
		// linha = (bloco * palavra)
		return (int) Math.pow(2, this.bit_dados) * this.bit_palavra;
	}

	@Override
	public String showCache() {
		// TODO Auto-generated method stub
		StringBuilder linha = new StringBuilder();
		linha.append(ANSI.RESET + "Cache: " + sizeCache() + "\n"
				+ "Associativa [TAG] | Cache [DADOS]\n");
		for (int i=0; i<this.assoc.length; i++) {
			linha.append(ANSI.RESET + "["
					+ this.assoc[i].toString()
					+ ANSI.RESET + "] | ["
					+ this.cache[i].toString()
					+ ANSI.RESET + "]\n");
		}
		return linha.toString();
	}

	@Override
	public String ajusteBits(String bits) {
		// TODO Auto-generated method stub
		int t = this.bit_tag + this.bit_dados + this.bit_select;
		StringBuilder ajusBits = new StringBuilder();
		ajusBits.append(String.format("%0" + t + "d", 0));
		ajusBits.append(bits);
		return ajusBits.toString().substring(ajusBits.length() - t);
	}

	@Override
	public boolean isLineCache(String bits) {
		// TODO Auto-generated method stub
		bits = ajusteBits(bits);
		boolean ret = false;
		int idx = -1;
		for (int i=0; i<this.assoc.length; i++) {
			if (this.assoc[i].isTag(bits)) {
				idx = i;
				ret = true;
				break;
			}
		}
		if (!ret) {
			idx = contador.getIndex();
			this.assoc[idx].setTag(bits);
			this.cache[idx].setPalavras(bits);
			idx = -1;
		}
		StringBuilder linha = new StringBuilder();
		linha.append(ANSI.RESET + "Associativa [TAG] | Cache [DADOS]\n");
		for (int i=0; i<this.assoc.length; i++) {
			linha.append(ANSI.RESET + "[");
			if (idx == i) {
				linha.append(this.assoc[i].getTag()
						+ ANSI.RESET + "] | ["
						+ this.cache[i].getPalavras(bits));
			} else {
				linha.append(this.assoc[i].toString()
						+ ANSI.RESET + "] | ["
						+ this.cache[i].toString());
			}
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
		if (this.bit_dados > 0) {
			printend.append(ANSI.RED + bits.substring(0, this.bit_dados));
			bits = bits.substring(this.bit_dados);
		}
		if (this.bit_select > 0) {
			printend.append(ANSI.PURPLE + bits.substring(0, this.bit_select));
			bits = bits.substring(this.bit_select);
		}
		printend.append("\n" + ANSI.YELLOW + "TAG "
				+ ANSI.RED + "DADOS "
				+ ANSI.PURPLE + "SEPARADOR BYTES");
		return printend.toString();
	}

	@Override
	public int sizeEndMemoria() {
		// TODO Auto-generated method stub
		return (int) Math.pow(2, (this.bit_tag + this.bit_dados + this.bit_select));
	}

}
