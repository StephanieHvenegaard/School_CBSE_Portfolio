/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.se.common.data.entityparts;

import dk.se.common.data.Entity;
import dk.se.common.data.GameData;


/**
 *
 * @author Stephanie
 */
public class SplitterPart implements EntityPart {
    
    boolean shouldSplit = false;

    @Override
    public void process(GameData gameData, Entity entity) {
        
    }

    public boolean ShouldSplit() {
        return shouldSplit;
    }

    public void setShouldSplit(boolean shouldSplit) {
        this.shouldSplit = shouldSplit;
    }
    
    
}
