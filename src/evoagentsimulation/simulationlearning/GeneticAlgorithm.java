/*******************************************************************************
 *  * EvoAgentStudent : A simulation platform for agents using neurocontrollers
 *  * Copyright (c)  2020 Suro François (suro@lirmm.fr)
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/

package evoagentsimulation.simulationlearning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.SortedSet;

public class GeneticAlgorithm {
    private int populationSize = 200;
    private int maxGeneration = 100;
    private int repetitions = 1;
    int genomeSize = 0;
    ArrayList<Individual> population;

    public class Individual implements Comparable<Individual> {
        double score = 0;
        double genome[];

        public Individual() {
            genome = new double[genomeSize];
        }

        public int compareTo(Individual compare) {

            return Double.compare(compare.score, score);
        }

        public void random() {
            for (int i = 0; i < genome.length; i++) {
                genome[i] = (Math.random() * 2.0) - 1.0;
            }
        }

        public Individual crossbreed(Individual i2) {
            double[] childWeights = new double[genomeSize];
            Random rand = new Random();
            int pivot = rand.nextInt(genomeSize - 1);
            for (int i = 0; i < pivot; i++) {
                childWeights[i] = this.genome[i];
            }
            for (int i = pivot; i < genomeSize; i++) {
                childWeights[i] = i2.genome[i];
            }
            Individual child = new Individual();
            child.genome = childWeights;
            return child;
        }

        public void mutation(double probabilite) {
            boolean mut = Math.random() < probabilite;
            double mutVal;
            Random rand = new Random();
            int mutTirage = 5;
            for (int i = 0; i < mutTirage - 1; i++) {
                mutVal = Math.random();
                if (mutVal < probabilite) {
                    int flipPoint = rand.nextInt(genomeSize);
                    int tirage = rand.nextInt(100);
                    int sum = tirage < 50 ? -1 : 1;
                    //genome[flipPoint] += (0.05 * genome[flipPoint] + 0.01) * sum;
                    genome[flipPoint] += 0.01 * sum;
                }
            }
            /**
             if (mut) {
             Random rand = new Random();
             int flipPoint = rand.nextInt(genomeSize);
             int tirage = rand.nextInt(100);
             int sum = tirage < 50 ? -1 : 1;
             genome[flipPoint] += (0.05 * genome[flipPoint] + 0.01) * sum;
             //int flipPoint = rand.nextInt(genomeSize - 2);
             double temp = genome[flipPoint];
             genome[flipPoint] = genome[flipPoint + 1];
             genome[flipPoint + 1] = temp;

             }**/
        }

        public double[] getGenome() {
            return genome;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double val) {
            score = val;
        }

    }

    public GeneticAlgorithm(int genomeS) {
        genomeSize = genomeS;
    }

    public void initialise() {
        if (this.population == null || this.population.isEmpty()) {
            population = new ArrayList<Individual>();
            Individual individual;
            for (int i = 0; i < populationSize; i++) {
                individual = new Individual();
                individual.random();
                population.add(individual);
            }
        } else {
            breedNew();
        }

    }

    public void breedNew() {
        double kBestPerc = 0.15; //les meilleurs
        int kBest = (int) (populationSize * kBestPerc);
        population.sort(Individual::compareTo);
        ArrayList<Individual> newPop = new ArrayList<>();
        Individual newInd;
        newPop.addAll(population.subList(0, kBest));
        /**
        for (int i = 0; i < kBest; i++) {
            newPop.get(i).mutation(0.05);
        }**/
        for (int i = kBest; i < populationSize; i++) {
            int x = (int) (Math.random() * (kBest - 1)); //On prend un élément aléatoire parmis les n meilleurs
            int y = (int) (((Math.random() * (kBest - 2)) + x + 1)) % (kBest - 1); //On prend un second élément aléatoire parmis les n meilleurs différent du premier élément
            newInd = newPop.get(x).crossbreed(newPop.get(y));
            newInd.mutation(0.05);
            newPop.add(newInd);
        }
    }

    public Individual getBest() {
        population.sort(Individual::compareTo);
        return population.get(0);
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public int getMaxGeneration() {
        return maxGeneration;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setMaxGeneration(int maxGeneration) {
        this.maxGeneration = maxGeneration;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
}
