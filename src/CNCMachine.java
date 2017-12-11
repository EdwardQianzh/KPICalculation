import java.util.*;

/**
 * Created by Z003R9XK on 12/6/2017.
 */
public class CNCMachine {
    private String date; // 年月日
    private int hour; // 小时
    private double speed; // 转速
    private double current; // 电流
    private int progStatus; // 程序状态
    private int axisStatus; // 轴状态
    private int cncStatus; // 机床状态
    private int status; // 运行状态; 0：未运行，1：开启，2：测试，3：切割

    public CNCMachine(String date, int hour, double speed, double current, int progStatus, int axisStatus, int cncStatus, int status) {
        this.date = date;
        this.hour = hour;
        this.speed = speed;
        this.current = current;
        this.progStatus = progStatus;
        this.axisStatus = axisStatus;
        this.cncStatus = cncStatus;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int gethour() {
        return hour;
    }

    public void sethour(int hour) {
        this.hour = hour;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public int getProgStatus() {
        return progStatus;
    }

    public void setProgStatus(int progStatus) {
        this.progStatus = progStatus;
    }

    public int getAxisStatus() {
        return axisStatus;
    }

    public void setAxisStatus(int axisStatus) {
        this.axisStatus = axisStatus;
    }

    public int getCncStatus() {
        return cncStatus;
    }

    public void setCncStatus(int cncStatus) {
        this.cncStatus = cncStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
