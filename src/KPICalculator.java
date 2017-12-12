import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Z003R9XK on 12/6/2017.
 */
public class KPICalculator {
    public static void main(String[] args) throws Exception{
        FileReader fr = new FileReader("testdata.csv");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String s;
        List<CNCMachine> machines = new ArrayList<>();
        while((s=br.readLine())!=null){
            String[] str = s.split(",");
            String date = getDate(str[0]);
            int hour = getHour(str[0]);
            double speed = Double.parseDouble(str[1]);
            double current = Double.parseDouble(str[2]);
            if(speed==0.0&&current!=0.0){
                current = 0.0;
            }
            int proStatus = Integer.parseInt(str[4]);
            int axisStatus = Integer.parseInt(str[5]);
            int cncStatus = Integer.parseInt(str[6]);
            int status;
            if(current==0.0 && current==0.0 && axisStatus==1){
                status = 1;
            }else if((speed>0.0 || current>0.0 || axisStatus==0) && cncStatus!=2){
                status = 2;
            }else if(speed>0.0 && current>0.0 && axisStatus==0 && cncStatus==2 && proStatus==3){
                status = 3;
            }else {
                status = 0;
            }
//            if(status==0){
//                System.out.println(str[0]);
//            }
            CNCMachine m = new CNCMachine(date, hour, speed, current, proStatus, axisStatus, cncStatus, status);
            machines.add(m);
        }
//        System.out.println(machines.size());
        double [][] kpis = new KPICalculator().get24HoursKPIByDate("2017-03-07", machines);
        for(int i=0; i<24; i++){
            System.out.print(i+": ");
            for(int j=0; j<4; j++){
                System.out.print(kpis[i][j]+" ");
            }
            System.out.println();
        }
        double [][] kpis2 = new KPICalculator().getTimeIntervalKPIsByDate("2017-03-07", machines);
        for(int i=0; i<3; i++){
            System.out.print(i+": ");
            for(int j=0; j<4; j++){
                System.out.print(kpis2[i][j]+" ");
            }
            System.out.println();
        }
    }

    // 获取日期，年-月-日
    public static String getDate(String s){
        String[] str = s.split("\\s+");
        return  str[0];
    }

    // 获取时间，小时数
    public static int getHour(String s) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  format.parse(s).getHours();
    }

    // 获取24小时的KPI分布
    public double[][] get24HoursKPIByDate(String date, List<CNCMachine> machines){
        int numOfStatus = 4;
        double[][] kpis = new double[24][numOfStatus];
        int[][] statusCounts = new int[24][numOfStatus];
        for(int i=0; i<24; i++){
            for(int j=0; j<numOfStatus; j++){
                statusCounts[i][j] = 0;
            }
        }
        for(CNCMachine m: machines){
            if(m.getDate().equals(date)){
                statusCounts[m.gethour()][m.getStatus()] += 1;
            }
        }
        int[] statusCountSum = new int[24];
        for(int i=0; i<24; i++){
            int sum = 0;
            for(int j=0; j<numOfStatus; j++){
                sum += statusCounts[i][j];
            }
            statusCountSum[i] = sum;
        }

        for(int i=0; i<24; i++){
            for(int j=0; j<numOfStatus; j++){
                if(statusCountSum[i] == 0){
                    kpis[i][j] = 0.0;
                }else{
                    kpis[i][j] = (double)statusCounts[i][j]/statusCountSum[i];
                }
            }
        }
        return kpis;
    }

    // 计算一天内早上、中午、晚上等时间区间内的的KPI
    public double[][] getTimeIntervalKPIsByDate(String date, List<CNCMachine> machines){
        int numOfStatus = 4;
        // 6:00-14:00 morning (0), 14:00-22:00 noon (1), 22:00-6:00 evening(2)
        int numOfIntervals = 3;
        double[][] kpis = new double[numOfIntervals][numOfStatus];
        int[][] statusCounts = new int[24][numOfStatus];
        for(int i=0; i<numOfIntervals; i++){
            for(int j=0; j<numOfStatus; j++){
                statusCounts[i][j] = 0;
            }
        }
        for(CNCMachine m: machines){
            if(m.getDate().equals(date)){
                int hour = m.gethour();
                if(hour>=0 && hour<14) {
                    statusCounts[0][m.getStatus()] += 1;
                }else if(hour>=14 && hour<22){
                    statusCounts[1][m.getStatus()] += 1;
                }else{
                    statusCounts[2][m.getStatus()] += 1;
                }
            }
        }
        int[] statusCountSum = new int[numOfIntervals];
        for(int i=0; i<numOfIntervals; i++){
            int sum = 0;
            for(int j=0; j<numOfStatus; j++){
                sum += statusCounts[i][j];
            }
            statusCountSum[i] = sum;
        }

        for(int i=0; i<numOfIntervals; i++){
            for(int j=0; j<numOfStatus; j++){
                if(statusCountSum[i] == 0){
                    kpis[i][j] = 0.0;
                }else{
                    kpis[i][j] = (double)statusCounts[i][j]/statusCountSum[i];
                }
            }
        }
        return kpis;
    }
}
