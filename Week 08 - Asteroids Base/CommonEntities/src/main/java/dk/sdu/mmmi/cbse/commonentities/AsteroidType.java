/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonentities;

/**
 *
 * @author Stephanie
 */
public enum AsteroidType {
    LARGE(20f),
    MEDIUM(10f),
    SMALL(5f);

    private float size;

    private AsteroidType(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
    }
}