

public class GerarMapaPixelado {

	// args[0] --> imagem de entrada. Exemplo: "umaImagem.png"
	// args[1] --> nome do documento de saída. Exemplo: "umaSaida.png"
	public static void main(String[] args) {
		
		// Se args[0] e args[1] estiverem vazios, pare o programa.
		if (args.length < 2) {
			System.out.println("O programa espera dois argumentos de entrada.");
			System.out.println("O primeiro eh o nome da imagem de entrada.");
			System.out.println("O segundo eh o nome do arquivo de saida.");
			return;
		}
		
		Mapa imagemDeEntrada = new Mapa();
		Mapa imagemDeSaida = null;
		
		// Tenta carregar a imagem de entrada. Se não conseguir, ele para o programa.
		if (!imagemDeEntrada.carregarImagem(args[0])) {
			System.out.println("Verifique se o nome do arquivo de entrada esta correto.");
			return;
		}
		
		imagemDeSaida = new Mapa(imagemDeEntrada.getLargura(), imagemDeEntrada.getAltura());
		
		for (int c = 0; c < imagemDeEntrada.getLargura(); c++) {
			for (int l = 0; l < imagemDeEntrada.getAltura(); l++) {
				// a função getRGB devolve um número do tipo int, mas com os 8 primeiros bits
				// representando a transparencia. Eu não quero isso aqui!
				// Por isso faço uma operação AND binária do getRGB com 0x00ffffff.
				imagemDeSaida.simplificaPixelRGB(imagemDeEntrada.getRGB(c, l) & 0x00ffffff, c, l);
			}
		}
		
		// Tenta salvar a imagem carregada. Se não conseguir, ele para o programa.
		if (!imagemDeSaida.salvarImagem(args[1])) {
			System.out.println("Verifique se o nome do arquivo de saida esta correto.");
			return;
		}

	}

}
