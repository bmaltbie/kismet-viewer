import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.StringBuilder;


// Client object to hold the information for each client. Has SSID ArrayList which contains SSID
// objects that are associated with this client.
public class Client {
    private String manuf, type, MAC, seenBy;
    private LocalDateTime firstSeen, lastSeen;
    private int channel, maxSeen, LLC, data, crypt, fragments, retries, total, datasize;
    
    private ArrayList<SSID> SSIDClientList = new ArrayList<SSID>();
    private ArrayList<String> frequencyList = new ArrayList<String>();
    private ArrayList<String> carrierList = new ArrayList<String>();
    private ArrayList<String> encodingList = new ArrayList<String>();
    
    private DateTimeFormatter stdFormatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy");
    
    
    // CONSTRUCTORS
    public Client() {
        setManuf("N/A");
        setFirstSeen_date(LocalDateTime.now());
        setLastSeen_date(LocalDateTime.now());
        setType("N/A");
        setMAC("N/A");
        setChannel(0);
        setMaxSeen(0);
        setLLC(0);
        setData(0);
        setCrypt(0);
        setFragments(0);
        setRetries(0);
        setTotal(0);
        setDatasize(0);
        setSeenBy("N/A");
    } // Client [default constructor]
    
    
    
    // GET METHODS
    public String getManuf() { return manuf; }
    public String getFirstSeen() { return firstSeen.format(stdFormatter); }
    public String getLastSeen() { return lastSeen.format(stdFormatter); }
    public LocalDateTime getFirstSeen_date() { return firstSeen; }
    public LocalDateTime getLastSeen_date() { return lastSeen; }
    public String getType() { return type; }
    public String getMAC() { return MAC; }
    public int getChannel() { return channel; }
    public int getMaxSeen() { return maxSeen; }
    public int getLLC() { return LLC; }
    public int getData() { return data; }
    public int getCrypt() { return crypt; }
    public int getFragments() { return fragments; }
    public int getRetries() { return retries; }
    public int getTotal() { return total; }
    public int getDatasize() { return datasize; }
    public String getSeenBy() { return seenBy; }
    
    public ArrayList<SSID> getSSIDClientList() { return SSIDClientList; }
    public ArrayList<String> getFrequencyList() { return frequencyList; }
    public ArrayList<String> getCarrierList() { return carrierList; }
    public ArrayList<String> getEncodingList() { return encodingList; }
    
    // Only called if the lists are of size 0 or 1
    public String getFrequency() { return (frequencyList.size() == 1) ? frequencyList.get(0) : "None"; }
    public String getCarrier() { return (carrierList.size() == 1) ? carrierList.get(0) : "None"; }
    public String getEncoding() { return (encodingList.size() == 1) ? encodingList.get(0) : "None"; }
    
    // Helper get functions
    public String getSSIDCount() { return (SSIDClientList.size() + " associated SSID(s)"); }
    public int getNumSSIDs() { return SSIDClientList.size(); }
    public int getNumFrequencies() { return frequencyList.size(); }
    public int getNumCarriers() { return carrierList.size(); }
    public int getNumEncodings() { return encodingList.size(); }

    // 'Seen By' in normal Kismet output files is two lines but it's saved as one string. This
    // replicates printing out seen by in the form of two lines so that the file can be re-read
    // by KismetViewer when exported to .txt. The line length is capped at 36 characters so that
    // it doesn't extend off the GUI screen.
    public String getSeenBy_Formatted() {
        String formatted = "", check = "";
        String [] tokens = seenBy.split(" ");
        
        for (int i = 0; i < tokens.length; i++) {
            if (check.length() + tokens[i].length() <= 36)
                check += " " + tokens[i];
            else if (tokens[i].length() > 36) {
                formatted += check + "\n" + tokens[i] + "\n";
                check = "";
            }
            else {
                formatted += check + "\n";
                check = tokens[i];
            } // else
        } // for
        formatted += check;
        
        return formatted.trim();
    } // getSeenBy_Formatted

    
    
    // SET METHODS
    public void setManuf(String manuf) { this.manuf = manuf; }
    
    public void setFirstSeen(String firstSeen) {
        this.firstSeen = LocalDateTime.parse(firstSeen.trim(), stdFormatter);
    } // setFirstSeen [string]
    
    public void setLastSeen(String lastSeen) {
        this.lastSeen = LocalDateTime.parse(lastSeen.trim(), stdFormatter);
    } // setLastSeen [string]
    
    public void setFirstSeen_date(LocalDateTime firstSeen) { this.firstSeen = firstSeen; }
    public void setLastSeen_date(LocalDateTime lastSeen) { this.lastSeen = lastSeen; }
    public void setType(String type) { this.type = type; }
    public void setMAC(String MAC) { this.MAC = MAC; }
    public void setChannel(int channel) { this.channel = channel; }
    public void setMaxSeen(int maxSeen) { this.maxSeen = maxSeen; }
    public void setLLC(int LLC) { this.LLC = LLC; }
    public void setData(int data) { this.data = data; }
    public void setCrypt(int crypt) { this.crypt = crypt; }
    public void setFragments(int fragments) { this.fragments = fragments; }
    public void setRetries(int retries) { this.retries = retries; }
    public void setTotal(int total) { this.total = total; }
    public void setDatasize(int datasize) { this.datasize = datasize; }
    public void setSeenBy(String seenBy) { this.seenBy = seenBy; }
    
    public void addSSIDClient(SSID ssidC) { SSIDClientList.add(ssidC); }
    public void addFrequency(String frequency) { frequencyList.add(frequency); }
    public void addCarrier(String carrier) { carrierList.add(carrier); }
    public void addEncoding(String encoding) { encodingList.add(encoding); }



    // TO STRING
    // Default toString method, not really used, can be changed without causing issues.
    public String toString() {
        return ("Client" + "\n\tManuf: " + manuf + "\n\tFirst: " + firstSeen + "\n\tLast: " +
                lastSeen + "\n\tType: " + type + "\n\tMAC: " + MAC + "\n\tChannel: " + channel +
                "\n\tMax Seen: " + maxSeen + "\n\tLLC: " + LLC + "\n\tData: " +
                data + "\n\tCrypt: " + crypt + "\n\tFragments: " + "\n\tRetries: " + "\n\tTotal: " +
                "\n\tDatasize: " + datasize + "\n\tSeen By: " + seenBy);
    } // toString
    
    
    // toString method called when outputting networks to a .csv file (when user chooses to export
    // values to .csv). SSIDs are enclosed in {} brackets and Clients are enclosed in [] brackets.
    public String toString_CSV() {
        StringBuilder result = new StringBuilder();
        
        result.append("[" + manuf + "," + getFirstSeen() + "," + getLastSeen() + "," + type + "," +
                      MAC + ",");
        
        if (!SSIDClientList.isEmpty())
            for (SSID ssid : SSIDClientList)
                result.append(ssid.toString_CSV() + ",");
        else
            result.append("None,");
        
        result.append(channel + ",");
        
        for (String frequency : frequencyList)
            result.append(frequency.replaceAll(",","") + ",");
        
        result.append(maxSeen + ",");
        
        // can have 0 carriers
        if (!carrierList.isEmpty())
            for (String carrier : carrierList)
                result.append(carrier + ",");
        else
            result.append("None,");
        
        // can have 0 encodings
        if (!encodingList.isEmpty())
            for (String encoding : encodingList)
                result.append(encoding + ",");
        else
            result.append("None,");
        
        result.append(LLC + "," + data + "," + crypt + "," + fragments + "," + retries + "," + total
                      + "," + datasize + "," + seenBy + "]");
        
        return result.toString();
    } // toString_CSV
    
    
    // toString method called when outputting networks to a .txt file (when user chooses to export
    // values to .txt). Mimics regular Kismet output .nettxt files.
    public String toString_TXT() {
        StringBuilder result = new StringBuilder();
        result.append("Client\n");
        result.append("    Manuf : " + manuf + "\n");
        result.append("    First : " + getFirstSeen() + "\n");
        result.append("    Last : " + getLastSeen() + "\n");
        result.append("    Type : " + type + "\n");
        result.append("    MAC : " + MAC + "\n");
        
        if (!SSIDClientList.isEmpty())
            for (SSID ssid : SSIDClientList)
                result.append("\t" + ssid.toString_TXTClient());
        
        result.append("    Channel : " + channel + "\n");
        
        for (String frequency : frequencyList)
            result.append("    Frequency : " + frequency + "\n");
        
        result.append("    Max Seen : " + maxSeen + "\n");
        
        if (!carrierList.isEmpty())
            for (String carrier : carrierList)
                result.append("    Carrier : " + carrier + "\n");
        
        if (!encodingList.isEmpty())
            for (String encoding : encodingList)
                result.append("    Encoding : " + encoding + "\n");
        
        result.append("    LLC : " + LLC + "\n");
        result.append("    Data : " + data + "\n");
        result.append("    Crypt : " + crypt + "\n");
        result.append("    Fragments : " + fragments + "\n");
        result.append("    Retries : " + retries + "\n");
        result.append("    Total : " + total + "\n");
        result.append("    Datasize : " + datasize + "\n");
        result.append("    Seen By : " + seenBy + "\n");

        return result.toString();
    } // toString_TXT
    
} // Client


/*
 CLIENT FORMAT:
    Client #
    manuf
    first
    last
    type
    MAC
    SSID objects [] - [0,inf)
    channel
    frequency [] - [1,inf)
    maxseen
    carrier - [0,1]
    encoding [] - [0,inf)
    llc
    data
    crypt
    fragments
    retries
    total
    datasize
    seenby
*/
