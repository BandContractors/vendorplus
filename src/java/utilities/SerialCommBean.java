package utilities;

import java.io.Serializable;
import javax.faces.bean.*;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

@ManagedBean
@SessionScoped
public class SerialCommBean implements Serializable, SerialPortEventListener {

    private static final long serialVersionUID = 1L;
    SerialPort serialPort = null;

//    public static void main(String[] args) {
//        try {
//            SerialCommBean scb = new SerialCommBean();
//            scb.setSerialPort();
//            scb.serialPort.openPort();
//            scb.serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//            scb.serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
//            scb.initListener();
//            scb.serialPort.writeString("IweWence");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }

    //starts the event listener that knows whenever data is available to be read
    //pre style="font-size: 11px;": an open serial port
    //post: an event listener for the serial port that knows when data is received
    public void initListener() {
        try {
            serialPort.addEventListener(this);
        } catch (SerialPortException e) {
            System.out.println("Too many listeners:" + e);
        }
    }

    public void setSerialPort() {
        //1. getting serial ports list into the array
        String[] portNames = SerialPortList.getPortNames();
        if (portNames.length == 0) {
            System.out.println("There are no serial-ports.");
        }
        for (int i = 0; i < portNames.length; i++) {
            System.out.println(portNames[i]);
        }
        serialPort = new SerialPort(portNames[0]);//can be like COM3
    }
    
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                String receivedData = serialPort.readString(event.getEventValue());
                System.out.println("Received response: " + receivedData);
            } catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }
    }
}
