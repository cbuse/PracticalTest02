package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

/**
 * Created by cristina on 14.05.2016.
 */
public class Information {
    private String operation;
    private int op1;
    private int op2;

    public  Information() {
        this.operation = null;
        this.op1 = 0;
        this.op2 = 0;
    }

    public  Information(
            String operation,
            int op1,
            int op2
            ){
        this.operation = operation;
        this.op1 = op1;
        this.op2 = op2;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setOp1(int op1) {
        this.op1 = op1;
    }

    public int getOp1() {
       return op1;
    }

    public void setOp2(int op2) {
        this.op2 = op2;
    }

    public int getOp2() {
        return op2;
    }

    @Override
    public String toString() {
       /* return Constants.TEMPERATURE + ": " + temperature + "\n\r" +
                Constants.WIND_SPEED + ": " + windSpeed + "\n\r" +
                Constants.CONDITION + ": "+ condition + "\n\r" +
                Constants.PRESSURE + ": "+ pressure + "\n\r" +
                Constants.HUMIDITY + ": " + humidity;*/
        return operation+","+op1+","+op2;
    }

}
