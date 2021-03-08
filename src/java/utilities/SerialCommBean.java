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
    
    public void writeToDisplay(String aPortName, String aLine1Str, String aLine2Str) {
        try {
            System.out.println("-A-");
            String PortName = aPortName;
            if (this.isPortFound(PortName) == 1) {
                System.out.println("-b-");
                this.setSerialPort(PortName);
                this.serialPort.openPort();
                this.serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                this.serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
                this.initListener();
                //this.serialPort.writeString("Added 21,000");
                //this.serialPort.writeString("             ");
                this.serialPort.writeString(aLine1Str);
                this.serialPort.closePort();
                System.out.println("-C-");
            }
        } catch (Exception e) {
            System.out.println("-D-");
            System.out.println(e);
        }
    }

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

    public void setSerialPort(String aPortName) {
        serialPort = new SerialPort(aPortName);//can be like COM3
    }

    public int isPortFound(String aPortName) {
        int portfound = 0;
        //1. getting serial ports list into the array
        String[] portNames = SerialPortList.getPortNames();
        if (portNames.length == 0) {
            portfound = 0;
        } else {
            for (int i = 0; i < portNames.length; i++) {
                if (aPortName.equals(portNames[i])) {
                    portfound = 1;
                    break;
                }
            }
        }
        return portfound;
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
