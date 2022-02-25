package br.com.jexpJavaFiles.mapa;
//import java.awt.image; //n�o sei se precisa desta

//este pacote se refere a uma classe que trabalha com imagens no java.
import java.awt.image.BufferedImage;

import java.util.ArrayList;
//import java.lang.Math.*;


//pacotes para lidar com arquivos (salvar)
import java.io.File;
import java.io.IOException;

//pacote para poder salvar imagem
import javax.imageio.ImageIO;

/* Esta fun��o define o mapa e seus m�todos
* Nesse programa, mapa � uma estrutura BufferedImage.
*/

public class Mapa {
	// Dimens�es do mapa. Estou fazendo separados para ser mais f�cil ajustar.
	private int dimensaoHorizontalDoMapa = 1000;
	private int dimensaoVerticalDoMapa = 1000;
	private ArrayList<Integer> listaDeCoresParaPixels = null;
	
	//c�digos RGB sendo relacionados a elementos
	public static final int AGUA = 0x0000ff;
	public static final int GRAMA = 0x00ff00;
	public static final int FLORESTA = 0x228b22;
	public static final int AREIA = 0xf5deb3;
	public static final int ROCHA = 0xa9a9a9;
	public static final int NEVE = 0xfffafa;
	public static final int GELO = 0x87cefa;
	public static final int TERRA = 0xcd853f;
	public static final int PRETO = 0x000000;
	public static final int VERMELHO = 0xff0000;
	
	
	// meu mapa em si
	BufferedImage mapa = null;
	
	//construtor
	public Mapa() {
		// BufferedImage(largura, altura, tipo da imagem)
		// TYPE_3BYTE_BGR representa uma imagem com 8 bits para cada cor RGB.
		this.mapa = new BufferedImage(dimensaoHorizontalDoMapa, dimensaoVerticalDoMapa,
				BufferedImage.TYPE_INT_RGB);
		
		this.listaDeCoresParaPixels = new ArrayList<Integer>();
		this.listaDeCoresParaPixels.add(Mapa.AGUA);
		this.listaDeCoresParaPixels.add(Mapa.GRAMA);
		this.listaDeCoresParaPixels.add(Mapa.FLORESTA);
		this.listaDeCoresParaPixels.add(Mapa.AREIA);
		this.listaDeCoresParaPixels.add(Mapa.ROCHA);
		this.listaDeCoresParaPixels.add(Mapa.NEVE);
		this.listaDeCoresParaPixels.add(Mapa.GELO);
		this.listaDeCoresParaPixels.add(Mapa.TERRA);
		this.listaDeCoresParaPixels.add(Mapa.PRETO);
		this.listaDeCoresParaPixels.add(Mapa.VERMELHO);
		
	}
	
	// construtor quando especificamos comprimentos horizontal e vertical
	public Mapa(int novaDimensaoHorizontal, int novaDimensaoVertical) {
		this.mapa = new BufferedImage(novaDimensaoHorizontal, novaDimensaoVertical,
				BufferedImage.TYPE_INT_RGB);
		
		this.listaDeCoresParaPixels = new ArrayList<Integer>();
		this.listaDeCoresParaPixels.add(Mapa.AGUA);
		this.listaDeCoresParaPixels.add(Mapa.GRAMA);
		this.listaDeCoresParaPixels.add(Mapa.FLORESTA);
		this.listaDeCoresParaPixels.add(Mapa.AREIA);
		this.listaDeCoresParaPixels.add(Mapa.ROCHA);
		this.listaDeCoresParaPixels.add(Mapa.NEVE);
		this.listaDeCoresParaPixels.add(Mapa.GELO);
		this.listaDeCoresParaPixels.add(Mapa.TERRA);
		this.listaDeCoresParaPixels.add(Mapa.PRETO);
		this.listaDeCoresParaPixels.add(Mapa.VERMELHO);
	}
	
	// Fun��o para salvar a imagem (sempre em formato PNG)
	// Recebe o nome do arquivo a ser salvo, contendo a extens�o .png no final.
	// Devolve True se der certo, false caso contrario.
	public boolean salvarImagem(String nomeDoArquivoDeSaida) {
		try {
			File arquivoDeSaida = new File(nomeDoArquivoDeSaida);
			ImageIO.write(this.mapa, "png", arquivoDeSaida);
			System.out.println("Imagem salva com sucesso!");
			return true;
		} catch (IOException ex) {
			// Deu algum erro
			System.out.println("Nao foi possivel salvar a imagem.");
			return false;
		}
	}
	
	// Fun��o para carregar uma imagem (sempre em formato PNG)
	// Recebe o nome do arquivo a ser lido, bem como o caminho at� ele (ex.: C:\...\imagem.png).
	// Reescreve o conte�do do mapa.
	// Devolve true se der certo, false se der errado.
	public boolean carregarImagem(String nomeDoArquivoCarregado) {
		BufferedImage imagemCarregada = null;
		
		//tentando ler
		try {
			imagemCarregada = ImageIO.read(new File(nomeDoArquivoCarregado));
			System.out.println("Imagem carregada com sucesso!");
			this.mapa = imagemCarregada;
			return true;
		} catch (IOException ex) {
			System.out.println("Nao foi possivel carregar a imagem.");
			return false;
		}

	}
	
	//configura o valor do pixel RGB
	public void setRGB (int posicaoHorizontal, int posicaoVertical, int codigoRGB) {
		this.mapa.setRGB(posicaoHorizontal, posicaoVertical, codigoRGB);
	}
	
	// pega o valor rgb do pixel
	public int getRGB (int posicaoHorizontal, int posicaoVertical) {
		return this.mapa.getRGB(posicaoHorizontal, posicaoVertical);
	}
	
	// pega o valor da largura da imagem
	public int getLargura () {
		return this.mapa.getWidth();
	}
	
	// pega o valor da altura da imagem
	public int getAltura() {
		return this.mapa.getHeight();
	}
	
	/* Esta fun��o vai receber o valor RGB de um pixel e vai buscar na lista listaDeCoresParaPixels
	 * qual � a cor que mais se aproxima. Depois disso, ela vai substituir o pixel da imagem original
	 * por esse novo valor.
	 * 
	 * Entrada: int corDoPixelOriginal, posi��oHorizontal, posi��oVertical.
	 * A imagemSaida deve ter as mesmas dimens�es da imagem original e vai ser a da instancia que chamou este m�todo.
	 * N�o devolve nada.
	 */
	
	public void simplificaPixelRGB (int corDoPixelOriginal, int posicaoHorizontal, int posicaoVertical) {
		
		// Come�o configurando um padr�o.
		int corParaSubstituirOPixel = Mapa.AGUA;
		// Uma vari�vel auxiliar. Ela come�a com um valor muito grande, ao qual a diferen�a entre a cor dos pixels ser� sempre
		// menor.
		int auxiliarParaCalculoDaDiferenca = 0;
		int menorDiferencaEncontrada = 0x1000000;
		
		// Vou calcular o m�dulo da diferen�a entre o valor RGB do pixel original com cada elemento da listaDeCoresParaPixels.
		// Aquele cuja diferen�a for a menor ser� a cor a substituir o original.
		
		for (int posicaoNaLista = 0; posicaoNaLista < this.listaDeCoresParaPixels.size(); posicaoNaLista++) {
			// Claro, existem possibilidades de otimizar isso envolvendo ordenar essa lista, mas por enquanto n�o
			// vou fazer nada do tipo.
			
			
			// Atualiza o auxiliar diferen�a encontrada.
			// Vou trabalhar com as componenetes separadas
			// 0x00ff0000 --> filtro para pegar componentes vermelhas
			// 0x0000ff00 --> filtro para pegar componenetes verdes
			// 0x000000ff --> filtro para pegar componenetes azuis
			// Eu ainda preciso fazer deslocamentos de bits para "normalizar" a diferen�a.
			auxiliarParaCalculoDaDiferenca = Math.abs(((corDoPixelOriginal & 0x00ff0000) >> 16) 
					- ((this.listaDeCoresParaPixels.get(posicaoNaLista) & 0x00ff0000) >> 16));
			
			auxiliarParaCalculoDaDiferenca += Math.abs(((corDoPixelOriginal & 0x0000ff00) >> 8) 
					- ((this.listaDeCoresParaPixels.get(posicaoNaLista) & 0x0000ff00) >> 8));
			
			auxiliarParaCalculoDaDiferenca += Math.abs(corDoPixelOriginal & 0x000000ff 
					- this.listaDeCoresParaPixels.get(posicaoNaLista) & 0x000000ff);
			
			
			if (auxiliarParaCalculoDaDiferenca < menorDiferencaEncontrada) {
				
				// Atualiza a cor para substitui��o de acordo com o conte�do da posi��o posicaoNaLista da lista de cores.
				corParaSubstituirOPixel = this.listaDeCoresParaPixels.get(posicaoNaLista);
				
				// Atualiza a menor diferen�a encontrada.
				menorDiferencaEncontrada = auxiliarParaCalculoDaDiferenca;
				
			}
		}
		
		// Encontrada a cor que mais se aproxima da original, � hora de substituir na imagem de sa�da.
		this.mapa.setRGB(posicaoHorizontal, posicaoVertical, corParaSubstituirOPixel);
	}
	

}