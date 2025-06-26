/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;

/**
 *
 * @author Thanasis
 */
public class AvailableTime {

    private int id;
    private String time_slot;

    public AvailableTime(int id, String time_slot) {
        this.id = id;
        this.time_slot = time_slot;
    }

    public int getId() {
        return id;
    }

    public String getTimeSlot() {
        return time_slot;
    }
}
