package Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static Utils.Random.randomInRange;

public class Solver {

    final int POPULATION_SIZE = 200;
    final int CHROMOSOME_LEN = 30;

    public Solver() {

    }

    class Gene {
        String name = "";
        int index = 0;
        Gene(String name, int index) {
            this.name = name;
            this.index = index;
        }
        void doAction(Cube actor) {}
        public String toString() {
            return this.name;
        }
    }

    class Chromosome {
        int id = 0;
        Vector<Gene> genes = new Vector();
        int maxFitness = -1;
        int maxGeneIndex = 0;

        public Chromosome(int id) {
            this.id = id;
            for(int i = 0; i < CHROMOSOME_LEN; i++) {
                genes.add(null);
            }
        }

        public void setGene(int pos, Gene gene) {
            this.genes.set(pos, gene);
        }
    }

    // init functions
    Gene[] geneTypes = {
        new Gene("RotateTop1Time", 0) {void doAction(Cube actor) {actor.rotateTop(1);}},
        new Gene("RotateTop-CC-1Time", 1) {void doAction(Cube actor) {actor.rotateTopCC(1);}},
        new Gene("RotateBottom1Time", 2) {void doAction(Cube actor) {actor.rotateBottom(1);}},
        new Gene("RotateBottom-CC-1Time", 3) {void doAction(Cube actor) {actor.rotateBottomCC(1);}},
        new Gene("RotateLeft1Time", 4) {void doAction(Cube actor) {actor.rotateLeft(1);}},
        new Gene("RotateLeft-CC-1Time", 5) {void doAction(Cube actor) {actor.rotateLeftCC(1);}},
        new Gene("RotateRight1Time", 6) {void doAction(Cube actor) {actor.rotateRight(1);}},
        new Gene("RotateRight-CC-1Time", 7) {void doAction(Cube actor) {actor.rotateRightCC(1);}},
        new Gene("RotateBack1Time", 8) {void doAction(Cube actor) {actor.rotateBack(1);}},
        new Gene("RotateBack-CC-1Time", 9) {void doAction(Cube actor) {actor.rotateBackCC(1);}},
        new Gene("RotateFront1Time", 10) {void doAction(Cube actor) {actor.rotateFront(1);}},
        new Gene("RotateFront-CC-1Time", 11) {void doAction(Cube actor) {actor.rotateFrontCC(1);}},
    };

    // accepts parent Cube type -- but only supports 2x2 dimensions at the moment
    private int calcFitness(Cube attempt) {
        int fitness = 0;
        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 2; x++) {
                for(int z = 0; z < 2; z++) {
                    fitness += attempt.getPiece(y,x,z).isInSameState(goalCube.getPiece(y,x,z)) ? 1 : 0;
                }
            }
        }
        return fitness;
    }

    final double MUTATION_RATE = 0.10;

    // Max number of generations before we restart the while evolution process
    final int MAX_GENS_BEFORE_RESTART = 50000;

    private Chromosome bestChromosome = null;
    private Chromosome prevGenBestChromosome = null;

    private Cube goalCube = null;
    private Cube scrambledCube = null;
    private Cube testingCube = null;

    private Vector<Chromosome> population = new Vector();

    private Gene getRandomGene() {
        return geneTypes[randomInRange(geneTypes.length)];
    }

    // For testing right now. Restructure later.
    private void evolve() {

        for(Chromosome chromosome : population) {
            for(int genePos = 0; genePos < CHROMOSOME_LEN; genePos++) {
                if(prevGenBestChromosome == null || Math.random() <= MUTATION_RATE) {
                    chromosome.setGene(genePos, getRandomGene());
                } else {
                    chromosome.setGene(genePos, prevGenBestChromosome.genes.get(genePos));
                }
            }
        }

        // Now assess the fitness of each chromosome in the population
        for(Chromosome chromosome : population) {
            // First reset testing cube back to our scrambled state
            scrambledCube.copyTo(testingCube);
            for(int geneIndex = 0; geneIndex < chromosome.genes.size(); geneIndex++) {
                chromosome.genes.get(geneIndex).doAction(testingCube);
                int cFitness = calcFitness(testingCube);
                if(cFitness > chromosome.maxFitness) {
                    chromosome.maxFitness = cFitness;
                    chromosome.maxGeneIndex = geneIndex;
                }
            }

            if(bestChromosome == null || chromosome.maxFitness > bestChromosome.maxFitness) {
                bestChromosome = chromosome;
            }
        }

        if(prevGenBestChromosome == null || bestChromosome.maxFitness > prevGenBestChromosome.maxFitness) {
            prevGenBestChromosome = bestChromosome;
        }

    }

    private void reset() {
        prevGenBestChromosome = null;
        bestChromosome = null;

        population.clear();

        for(int p = 0; p < POPULATION_SIZE; p++) {
            Chromosome chromosome = new Chromosome(p);
            population.add(chromosome);
        }
    }

    // returns List containing steps that solves the cube
    public List<Integer> solve(Cube cubeToSolve) {
        // cube that is always in a solved state that we can compare each chromosome's progress to
        goalCube = CubeBuilder.makeNewCubeOfSameType(cubeToSolve);

        // init other cubes
        scrambledCube = cubeToSolve;
        testingCube = CubeBuilder.makeNewCubeOfSameType(cubeToSolve);

        reset();

        int genNumber = 0;
        while(prevGenBestChromosome == null || prevGenBestChromosome.maxFitness < 8) {
            evolve();
            if(prevGenBestChromosome != null && genNumber > 0 && genNumber % 5000 == 0) {
                System.out.println("Gen #: " + genNumber + " fitness so far: " + prevGenBestChromosome.maxFitness + " max Gene Index: " + prevGenBestChromosome.maxGeneIndex);
            }
            genNumber++;
            if(genNumber == MAX_GENS_BEFORE_RESTART) {
                // If we've reached MAX_GENS_BEFORE_RESTART, then we've kind of stalled, so we'll just restart
                // which will increase our chances of hitting a solution sooner
                System.out.println("\nStalled and reached max # generations: " + MAX_GENS_BEFORE_RESTART + ". Restarting evolution.\n");
                genNumber = 0;
                reset();
            }
        }

        List<Integer> solution = new ArrayList();

        scrambledCube.copyTo(testingCube);
        for(int g = 0; g <= prevGenBestChromosome.maxGeneIndex; g++) {
            Gene gene = prevGenBestChromosome.genes.get(g);
            gene.doAction(testingCube);

            solution.add(gene.index);
        }

        System.out.println("prevGenBestChromosome state: " + testingCube.getFacesAsString());

        return solution;
    }

    public List<Integer> runStandalone() {
        Cube cube = CubeBuilder.makeCube(Cube.CubeType.TWO_BY_TWO);

        // scramble the cube -- this will serve as our starting state for each test
        int[] scrambleMoves = {0,4,0, 4,2,1, 5,1,1, 2,2,0, 5,3,0, 5,1,1, 1,1,0, 0,3,0, 4,3,1, 2,2,0, 1,2,0, 5,3,1,
                5,4,0, 4,2,1, 2,1,1, 1,2,0, 4,4,1, 2,4,1, 0,3,1, 1,3,1, 4,1,1, 0,3,0, 0,3,0, 2,4,0,
                0,4,1, 2,1,1, 5,4,1, 1,2,1, 1,4,1, 4,3,0, 5,1,0, 0,2,0, 1,1,1, 5,3,1, 4,1,1, 2,4,0,
                2,1,0, 5,3,1, 2,2,1, 2,4,1, 0,4,1, 5,4,0, 2,4,1, 0,2,0, 3,3,0, 5,1,0, 3,4,0, 1,1,1,
                5,1,0, 0,1,0};

        cube.scramble(scrambleMoves);

        return solve(cube);
    }

    public static void main(String[] args) {
        Solver solver = new Solver();
        solver.runStandalone();
    }
}

