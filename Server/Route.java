/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AsusPC
 */
import java.io.Serializable;

public class Route implements Serializable {
    private String busNum;
    private String time;
    private double fares;
    
    public Route(String routeNum, String time,double fares) {
        this.busNum = routeNum;
        this.time = time;
        this.fares = fares;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public String getRouteNum() {
        return busNum;
    }
    
    public String getTime() {
        return time;
    }
    
    public double getFares() {
        return fares;
    }
}