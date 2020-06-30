package br.pucrs.interpretador.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
	private static L1_Cache CACHE;
	private static int LN = 8;
	private static int TG = 8;
	private static int BL = 0;
	private static int PL = 16;
	private static int BP = 0;
	private static String ARQ;
	private static String[] SEQUENCIA;
	private static int HIT = 0;
	private static int MISS = 0;

	public static void main( String[] args )
	{
		System.out.println("Interpretador de acesso a cache\n");
		try {
			if (args.length > 0)
				// help
				if(args[0].equals("-h") || args[0].equals("help")) {
					help();
				} else {
					// validar parametros
					for (String s : args) {
						switch (s.substring(0, 3)) {
						case "mds":
							//CACHE = map.MDS;
							break;

						case "mdb":
							CACHE = new MapDirectBlock();
							break;

						case "mas":
							//CACHE = map.MAS;
							break;

						case "mab":
							CACHE = new MapAssocBlock();
							break;

						case "ln=":
							LN = Integer.parseInt(s.substring(3));
							break;

						case "tg=":
							TG = Integer.parseInt(s.substring(3));
							break;

						case "bl=":
							BL = Integer.parseInt(s.substring(3));
							break;

						case "pl=":
							PL = Integer.parseInt(s.substring(3));
							break;

						case "bp=":
							BP = Integer.parseInt(s.substring(3));
							break;

						case "arq":
							ARQ = s.substring(4);
							break;

						default:
							break;
						}
					}
					validFile();
					configCache();
					acessarCache();
					System.out.println(CACHE.showCache());
					System.out.println(String.format("Hit: %d | Miss: %d", HIT, MISS));
					System.out.println(String.format("Percentual de Hits: %.2f%s | Miss: %.2f%s", ((HIT * 100) / (double)SEQUENCIA.length), "%", ((MISS * 100) / (double)SEQUENCIA.length), "%"));
				}
			else
				throw new Exception("Não foi informado corretamente os argumentos, consulte o manual.\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			help();
		}
	}

	public static void help() {
		System.out.println("Uso: cache [<parametros>] [<arquivo>]\n");
		System.out.println("<parametros>");
		System.out.println("\tmds: Mapeamento Direto sem Bloco");
		System.out.println("\tmdb: Mapeamento Direto com Bloco");
		System.out.println("\tmas: Mapeamento Associativo sem Bloco");
		System.out.println("\tmab: Mapeamento Associativo com Bloco");
		System.out.println("\ttg=bit: Quantidade de bits da tag");
		System.out.println("\tln=bit: Quantidade de bits da linhas");
		System.out.println("\tbl=bit: Quantidade de bits do bloco");
		System.out.println("\tpl=bit: Quantidade de bits da palavra");
		System.out.println("\tbp=bit: Quantidade de bits separador de palavra\n");
		System.out.println("<arquivo>");
		System.out.println("\tarq=path: Arquivo txt com os endereços de acesso a memoria em \n"
				+ "\t\thexadecimal e separados por virgula (,)\n");
		System.out.println("Exemplo sem bloco: \n"
				+ "\tjava -jar cache.jar mds tg=9 ln=3 arq=memoria-hexa.txt\n");
		System.out.println("Exemplo com bloco: \n"
				+ "\tjava -jar cache.jar mdb tg=9 ln=3 bl=3 pl=3 arq=memoria-hexa.txt");
	}
	
	public static void validFile() throws Exception {
		if (ARQ == null)
			throw new Exception("Não foi informado o arquivo.\n");
		try {
			BufferedReader br = new BufferedReader(new FileReader(ARQ));
			String linha;
			while ((linha = br.readLine()) != null) {
				String[] end = linha.split(", ");
				SEQUENCIA = end;
				System.out.println("Total de referências: "+end.length);
				System.out.println("Primeiro endereço: "+end[0]);
				System.out.println("Ultimo endereço: "+end[end.length-1]+"\n");
			}
			br.close();
		} catch (Exception e) {
			throw new Exception("Verificar o arquivo informado.\n");
		}
	}
	
	private static void configCache() {
		if (CACHE instanceof MapDirectBlock) {
			((MapDirectBlock) CACHE).config(TG, LN, BL, PL, BP);
		} else if (CACHE instanceof MapAssocBlock) {
			((MapAssocBlock) CACHE).config(TG, LN, BL, PL, BP);
		}
		System.out.println("Tamanho da cache: " + CACHE.sizeCache() + " bits");
		System.out.println("Quantidade de linhas: " + (int) Math.pow(2, LN) + " linhas");
		System.out.println("Tamanho da linha: " + CACHE.sizeLineCache() + " bits");
		System.out.println("Capacidade de endereçamento de memoria: " + CACHE.sizeEndMemoria() + " bits\n");
	}

	private static void acessarCache() {
		// TODO Auto-generated method stub
		Scanner pause = new Scanner(System.in);
		for (String endereco : SEQUENCIA) {
			String bits;
			System.out.println("Endereço hexadecimal: " + endereco);
			bits = Integer.toBinaryString(Integer.parseInt(endereco, 16));
			System.out.println(CACHE.printBits(bits));
			if (CACHE.isLineCache(bits))
				HIT++;
			else
				MISS++;
			System.out.println(String.format("Hit: %d | Miss: %d", HIT, MISS));
			pause.nextLine();
		}
		pause.close();
	}
	
}
