/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flowmate_team5;
import java.io.Serializable;

/**
 * Task 1.1: Interface representing an action to be executed.
 * Updated for Task 9: Must extend Serializable to allow saving/loading.
 * @author husse
 */
public interface Action extends Serializable {
    void execute();
}