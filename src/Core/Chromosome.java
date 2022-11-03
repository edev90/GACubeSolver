package Core;

import java.util.Vector;

public class Chromosome {
    protected int id = 0;
    protected Vector<Gene> genes = new Vector();
    protected int maxFitness = -1;
    protected int maxGeneIndex = 0;

    public Chromosome(int id, int chromosomeLen) {
        this.id = id;
        for(int i = 0; i < chromosomeLen; i++) {
            genes.add(null);
        }
    }

    public String toString() {
        return "Genes: " + genes;
    }

    public void setGene(int pos, Gene gene) {
        this.genes.set(pos, gene);
    }

    public Chromosome clone() {
        Chromosome copy = new Chromosome(id, this.genes.size());
        copy.genes.addAll(this.genes);
        copy.maxFitness = this.maxFitness;
        copy.maxGeneIndex = this.maxGeneIndex;
        return copy;
    }
}
