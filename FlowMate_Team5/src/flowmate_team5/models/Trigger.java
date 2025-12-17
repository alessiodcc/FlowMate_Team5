/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flowmate_team5.models;

import java.io.Serializable;
/**
 *
 * @author husse
 */
/**
 * Task 1.2: Interface representing a condition that triggers a rule.
 */
public interface Trigger extends Serializable {
    boolean isTriggered();
}

