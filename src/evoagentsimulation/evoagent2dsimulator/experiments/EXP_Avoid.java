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

package evoagentsimulation.evoagent2dsimulator.experiments;

import evoagentsimulation.evoagent2dsimulator.experiments.elements.*;
import org.jbox2d.common.Vec2;

import evoagentmindelements.EvoAgentMind;
import evoagentsimulation.SimulationEnvironment;
import evoagentsimulation.simulationlearning.GeneticAlgorithm;

public class EXP_Avoid extends SimulationEnvironment {

    public EXP_Avoid() {
        super();
        this.name = "Avoid";
        hasObstacles = true;
        makeCircleWorld = true;
    }

    public void initialisation() {
        botStartPos = new Vec2(0.0f, 5.0f);
        minObstacleSize = 2.2;
        maxObstacleSizeVariability = 4.0;
        maxObstacleSpacingVariability = 12.0;
        obstacleSpacing = 23.0;
        int WORLD_SIZE = 200;
        setSquareBorders(WORLD_SIZE);
        makeWorld();
        getBot().registerBotToWorld();

        // ...
        controlFunctions.add(new CF_NextOnTimeout(getBot(), this, 15000));
        rewardFunctions.add(new RW_ForwardMotion(getBot(), 0.002)); //0.002
        rewardFunctions.add(new RW_Speed(getBot(), 0.0001)); //0.00005
        rewardFunctions.add(new RW_PunishOnCollision(getBot(), 15));
        rewardFunctions.add(new RW_AvoidObs(getBot(), 2, 0.5));
        //controlFunctions.add(new CF_NextOnCollisionAndTimeout(getBot(),this, ...));
        //rewardFunctions.add(...);
    }

    public GeneticAlgorithm makeGeneticAlgorihm(int genomeS) {
        GeneticAlgorithm gen = new GeneticAlgorithm(genomeS);
        gen.setMaxGeneration(1000);
        gen.setPopulationSize(300);
        // ...

        return gen;
    }

    public EvoAgentMind makeMind() {
        EvoAgentMind mind = new EvoAgentMind();
        String sens = "S";
        for (int i = 1; i < 11; i++) {
            mind.addSensor(sens + i);

        }
        mind.addActuator("MotR");
        mind.addActuator("MotL");

        mind.addSensor("DOF");
        mind.setHiddenLayerCount(2);
        mind.setHiddenLayerSize(4);
        // ...
        //mind.addActuator("...");
        //mind.addSensor("...");
        //mind.set ...;

        return mind;
    }
}
