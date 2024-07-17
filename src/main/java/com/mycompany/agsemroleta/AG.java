package com.mycompany.agsemroleta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AG {

    private Random random = new Random();

    public Individuo executar(IndividuoFactory indFact, int nPop, int nElite, int numGer) {
        List<Individuo> popIni = new ArrayList<>();
        for (int i = 0; i < nPop; i++) {
            popIni.add(indFact.getIndividuo());
        }

        for (int g = 0; g < numGer; g++) {
            // Gerar os filhos utilizando o popIni
            List<Individuo> popAux = new ArrayList<>(popIni);
            List<Individuo> filhos = new ArrayList<>();
            for (int i = 0; i < nPop / 2; i++) {
                // Retirar o pai1 aleatoriamente de popAux
                Individuo pai1 = popAux.remove(random.nextInt(popAux.size()));
                // Retirar o pai2 aleatoriamente de popAux
                Individuo pai2 = popAux.remove(random.nextInt(popAux.size()));
                // List<Individuo> 2filhos = pai1.recombinar(pai2);
                List<Individuo> doisFilhos = pai1.recombinar(pai2);
                filhos.addAll(doisFilhos);
            }

            // Gerar os mutantes utilizando o popIni
            List<Individuo> mutantes = new ArrayList<>();
            for (int i = 0; i < nPop; i++) {
                Individuo pai1 = popIni.get(i);
                mutantes.add(pai1.mutar());
            }

            List<Individuo> joinList = new ArrayList<>();
            joinList.addAll(popIni);
            joinList.addAll(filhos);
            joinList.addAll(mutantes);

            List<Individuo> newPop = new ArrayList<>();

            // newPop recebe os nElite melhores individuos de joinList
            joinList.sort((ind1, ind2) -> Double.compare(ind2.getAvaliacao(), ind1.getAvaliacao()));
            for (int i = 0; i < nElite; i++) {
                newPop.add(joinList.remove(joinList.size() - 1));
            }

            // newPop recebe os outros (nPop - nElite) individuos utilizando seleção por torneio
            while (newPop.size() < nPop) {
                newPop.add(selecaoPorTorneio(joinList, 3)); // 3 é o tamanho do torneio
            }

            popIni.clear();
            popIni.addAll(newPop);

            // Impressão do progresso
            Individuo melhorIndividuo = popIni.get(0);
            double melhorAvaliacao = melhorIndividuo.getAvaliacao();
            System.out.println("Geracao " + (g + 1) + ": Melhor Avaliacao = " + melhorAvaliacao);
            if (melhorIndividuo instanceof IndividuoNRainhas) {
                ((IndividuoNRainhas) melhorIndividuo).imprimirTabuleiro();
            }
              // Parar se encontrar um indivíduo com conflitos 0
            if (melhorAvaliacao == 0) {
                System.out.println("Solucao encontrada na geracao " + (g + 1));
                return melhorIndividuo;
            }
        }
        
        // Individuo melhor = o melhor individuo de popIni.
        popIni.sort((ind1, ind2) -> Double.compare(ind2.getAvaliacao(), ind1.getAvaliacao()));
        return popIni.get(0);
    }

    private Individuo selecaoPorTorneio(List<Individuo> individuos, int tamanhoTorneio) {
        List<Individuo> torneio = new ArrayList<>();
        for (int i = 0; i < tamanhoTorneio; i++) {
            torneio.add(individuos.get(random.nextInt(individuos.size())));
        }
        torneio.sort((ind1, ind2) -> Double.compare(ind2.getAvaliacao(), ind1.getAvaliacao()));
        return torneio.get(0);
    }
}