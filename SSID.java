import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


// SSID object to hold the information for each SSID. Is used both for SSIDs that are associated
// with Networks and with Clients (both have similar information but there are some differences).
public class SSID {
    private String type, SSIDName, info, WPS, WPSManuf, devName, modelName, modelNum, WPAVersion;
    LocalDateTime firstSeen, lastSeen;
    double maxRate;
    private int beacon, packets;
    private ArrayList<String> encryptionList = new ArrayList<String>();
    private DateTimeFormatter stdFormatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy");
  
    
    // CONSTRUCTORS
    public SSID() {
        setType("N/A");
        setSSIDName("N/A");
        setInfo("N/A");
        setFirstSeen_date(LocalDateTime.now());
        setLastSeen_date(LocalDateTime.now());
        setMaxRate(0.0);
        setBeacon(0);
        setPackets(0);
        setWPS("N/A");
        setWPSManuf("N/A");
        setDevName("N/A");
        setModelName("N/A");
        setModelNum("N/A");
        setWPAVersion("N/A");
    } // SSID [default constructor]
    
    
    
    // GET METHODS
    public String getType() { return type; }
    public String getSSIDName() { return SSIDName; }
    public String getInfo() { return info; }
    public String getFirstSeen() { return firstSeen.format(stdFormatter); }
    public String getLastSeen() { return lastSeen.format(stdFormatter); }
    public LocalDateTime getFirstSeen_date() { return firstSeen; }
    public LocalDateTime getLastSeen_date() { return lastSeen; }
    public double getMaxRate() { return maxRate; }
    public int getBeacon() { return beacon; }
    public int getPackets() { return packets; }
    public String getWPS() { return WPS; }
    public String getWPSManuf() { return WPSManuf; }
    public String getDevName() { return devName; }
    public String getModelName() { return modelName; }
    public String getModelNum() { return modelNum; }
    public String getWPAVersion() { return WPAVersion; }
    public ArrayList<String> getEncryptionList() { return encryptionList; }
    public int getNumEncryptions() { return encryptionList.size(); }
    public String getEncryption_plain() {return (encryptionList.size() == 1) ? encryptionList.get(0) : "None"; }
    
    
    // SSIDs can have multiple encryption types; this method appends them into one string (without
    // duplicates) in order for them to all be displayed at once in the main information table.
    public String getEncryption() {
        String result = "";
        int index = 0;
        
        for (String encryption : encryptionList) {
            if (!encryption.equals("None")) {
                String [] tokens = encryption.split("[+-]");
                
                if (!result.contains(tokens[0]))
                    result += tokens[0];
                
                for (int i = 1; i < tokens.length; i++) {
                    if (!result.contains(tokens[i])) {
                        index = encryption.indexOf(tokens[i]);
                        result += (encryption.substring(index-1, index)+tokens[i]);
                    } // if
                } // for
            } // if
        } // for
        
        return result;
    } // getEncryption
    
    
    
    // SET METHODS
    public void setType(String type) { this.type = type; }
    public void setSSIDName(String SSIDName) { this.SSIDName = SSIDName; }
    public void setInfo(String info) { this.info = info; }
    
    public void setFirstSeen(String firstSeen) {
        this.firstSeen = LocalDateTime.parse(firstSeen.trim(), stdFormatter);
    } // setFirstSeen [string]
    
    public void setLastSeen(String lastSeen) {
        this.lastSeen = LocalDateTime.parse(lastSeen.trim(), stdFormatter);
    } // setLastSeen [string]
    
    public void setFirstSeen_date(LocalDateTime firstSeen) { this.firstSeen = firstSeen; }
    public void setLastSeen_date(LocalDateTime lastSeen) { this.lastSeen = lastSeen; }
    public void setMaxRate(double maxRate) { this.maxRate = maxRate; }
    public void setBeacon(int beacon) { this.beacon = beacon; }
    public void setPackets(int packets) { this.packets = packets; }
    public void setWPS(String WPS) { this.WPS = WPS; }
    public void setWPSManuf(String WPSManuf) { this.WPSManuf = WPSManuf; }
    public void setDevName(String devName) { this.devName = devName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public void setModelNum(String modelNum) { this.modelNum = modelNum; }
    public void setWPAVersion(String WPAVersion) { this.WPAVersion = WPAVersion; }
    public void addEncryption(String encryption) { encryptionList.add(encryption); }
    

    // TO STRING
    // Default toString method, not really used, can be changed without causing issues.
    public String toString() {
        return ("SSID" + "\n\tType: " + type + "\n\tSSID: " + SSIDName + "\n\tFirst: "+ info +
                "\n\tFirst: " + firstSeen + "\n\tLast: " + lastSeen + "\n\tMax Rate: " + maxRate +
                "\n\tPackets: " + packets + "\n\tWPS: " + WPS + "\n\tWPA Version: " + WPAVersion);
    } // toString
    
    
    // toString method called when outputting SSIDs to a .csv file (when user chooses to export
    // values to .csv). SSIDs are enclosed in {} brackets and Clients are enclosed in [] brackets.
    public String toString_CSV() {
        StringBuilder result = new StringBuilder();
        
        result.append("{" + type + "," + SSIDName + "," + getFirstSeen() + "," + getLastSeen() + ","
                      + maxRate + "," + beacon + "," + packets + "," + WPS + "," + WPSManuf + "," +
                      devName + "," + modelName + "," + modelNum + ",");
        
        for (String encryption : encryptionList)
            result.append(encryption + ",");
        
        result.append(WPAVersion + "}");
        
        return result.toString();
    } // toString_CSV

    
    // toString method called when outputting networks to a .txt file (when user chooses to export
    // values to .txt). SSIDs associated with Clients have different relevant information than
    // those associated with Networks so this method is specific to the case where a Client's
    // SSID is printed when outputting to a .txt file.
    public String toString_TXTClient() {
        StringBuilder result = new StringBuilder();
        result.append("SSID\n");
        result.append("\t Type : " + type + "\n");
        result.append("\t SSID : " + SSIDName + "\n");
        result.append("\t First : " + getFirstSeen() + "\n");
        result.append("\t Last : " + getLastSeen() + "\n");
        result.append("\t Max Rate : " + maxRate + "\n");
        result.append("\t Packets : " + packets + "\n");
        
        if (!encryptionList.get(0).equals("None"))
            for (String encryption : encryptionList)
                result.append("\t Encryption : " + encryption + "\n");
        
        return result.toString();
    } // toString_TXTClient
    
    
    // toString method called when outputting networks to a .txt file (when user chooses to export
    // values to .txt). SSIDs associated with Networks have different relevant information than
    // those associated with Clients so this method is specific to the case where a Network's
    // SSID is printed when outputting to a .txt file.
    public String toString_TXTNetwork() {
        StringBuilder result = new StringBuilder();
        result.append("SSID\n");
        result.append("\t Type : " + type + "\n");
        result.append("\t SSID : " + SSIDName + "\n");
        result.append("\t First : " + getFirstSeen() + "\n");
        result.append("\t Last : " + getLastSeen() + "\n");
        result.append("\t Max Rate : " + maxRate + "\n");
        result.append("\t Beacon : " + beacon + "\n");
        result.append("\t Packets : " + packets + "\n");
        result.append("\t WPS : " + WPS + "\n");
        result.append("\t WPS Manuf : " + WPSManuf + "\n");
        result.append("\t Dev Name : " + devName + "\n");
        result.append("\t Model Name : " + modelName + "\n");
        result.append("\t Model Num : " + modelNum + "\n");
        
        if (!encryptionList.get(0).equals("None"))
            for (String encryption : encryptionList)
                result.append("\t Encryption : " + encryption + "\n");
        
        result.append("\t WPA Version : " + WPAVersion + "\n");
        
        return result.toString();
    } // toString_TXTNetwork

} // SSID


/*
 SSID FORMAT
    type
    SSID name
    info - [0,1]
    first
    last
    max rate
    beacon - [0,1]
    packets
    WPS
    WPS Manuf - [0,1]
    Dev Name - [0,1]
    Model Name - [0,1]
    Model Num - [0,1]
    encryption [] - [1,inf)
    WPA version - [0,1]
*/
