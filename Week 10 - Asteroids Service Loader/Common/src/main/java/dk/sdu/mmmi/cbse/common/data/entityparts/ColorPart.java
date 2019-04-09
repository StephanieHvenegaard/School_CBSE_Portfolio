/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Stephanie
 */
public class ColorPart implements EntityPart {

    int red = 1;
    int green = 1;
    int blue = 1;

    public ColorPart() {

    }

    public ColorPart(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int[] getColor() {
        int[] color = {red, green, blue};
        return color;
    }

    public void setColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
