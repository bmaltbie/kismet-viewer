import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.StringBuilder;


// Network object to hold the information for each network. Has Client and SSID ArrayLists which
// contains Client objects and SSID objects, respectively, that are associated with this network.
public class Network {
    private String manuf, type, BSSID, seenBy;
    private LocalDateTime firstSeen, lastSeen, lastBSSTS;
    private int channel, maxSeen, LLC, data, crypt, fragments, retries, total, datasize,
                lastSignalDbm, minSignalDbm, maxSignalDbm;
    
    private ArrayList<SSID> SSIDNetworkList = new ArrayList<SSID>();
    private ArrayList<String> frequencyList = new ArrayList<String>();
    private ArrayList<String> carrierList = new ArrayList<String>();
    private ArrayList<String> encodingList = new ArrayList<String>();
    private ArrayList<Client> clientList = new ArrayList<Client>();
    private ArrayList<String> alertList = new ArrayList<String>();
    
    // DateTimeFormatters based on the format that dates are being read in/outputted
    private DateTimeFormatter stdFormatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy");
    private DateTimeFormatter bsstsFormatter = DateTimeFormatter.ofPattern("MMM d HH:mm:ss yyyy");
    private DateTimeFormatter bsstsNoYearFormatter = DateTimeFormatter.ofPattern("MMM d HH:mm:ss");
    

    // CONSTRUCTORS
    public Network() {
        setManuf("N/A");
        setFirstSeen_date(LocalDateTime.now());
        setLastSeen_date(LocalDateTime.now());
        setType("N/A");
        setBSSID("N/A");
        setChannel(0);
        setMaxSeen(0);
        setLLC(0);
        setData(0);
        setCrypt(0);
        setFragments(0);
        setRetries(0);
        setTotal(0);
        setDatasize(0);
        setLastBSSTS_date(LocalDateTime.now());
        setSeenBy("N/A");
    } // Network [default constructor]
    
    
    // GET METHODS
    public String getManuf() { return manuf; }
    public String getFirstSeen() { return firstSeen.format(stdFormatter); }
    public String getLastSeen() { return lastSeen.format(stdFormatter); }
    public LocalDateTime getFirstSeen_date() { return firstSeen; }
    public LocalDateTime getLastSeen_date() { return lastSeen; }
    public String getType() { return type; }
    public String getBSSID() { return BSSID; }
    public int getChannel() { return channel; }
    public int getMaxSeen() { return maxSeen; }
    public int getLLC() { return LLC; }
    public int getData() { return data; }
    public int getCrypt() { return crypt; }
    public int getFragments() { return fragments; }
    public int getRetries() { return retries; }
    public int getTotal() { return total; }
    public int getDatasize() { return datasize; }
    public String getLastBSSTS() { return lastBSSTS.format(stdFormatter); }
    public LocalDateTime getLastBSSTS_date() { return lastBSSTS; }
    public String getLastBSSTS_NoYear() { return lastBSSTS.format(bsstsNoYearFormatter); }
    public String getSeenBy() { return seenBy; }
    public String getSeenBy_TXT() { return (seenBy + "\n"); }
    public int getLastSignalDbm() { return lastSignalDbm; }
    public int getMinSignalDbm() { return minSignalDbm; }
    public int getMaxSignalDbm() { return maxSignalDbm; }
    
    public ArrayList<SSID> getSSIDNetworkList() { return SSIDNetworkList; }
    public ArrayList<String> getFrequencyList() { return frequencyList; }
    public ArrayList<String> getCarrierList() { return carrierList; }
    public ArrayList<String> getEncodingList() { return encodingList; }
    public ArrayList<Client> getClientList() { return clientList; }
    public ArrayList<String> getAlertList() { return alertList; }
    
    // Only called if the lists are of size 0 or 1
    public String getFrequency() { return (frequencyList.size() == 1) ? frequencyList.get(0) : "None"; }
    public String getCarrier() { return (carrierList.size() == 1) ? carrierList.get(0) : "None"; }
    public String getEncoding() { return (encodingList.size() == 1) ? encodingList.get(0) : "None"; }
    public String getAlert() { return (alertList.size() == 1) ? alertList.get(0) : "None"; }
    
    
    // Helper get functions
    public String getSSIDCount() { return (SSIDNetworkList.size() + " associated SSID(s)"); }
    public String getClientCount() { return (clientList.size() + " associated Client(s)"); }
    public int getNumClients() { return clientList.size(); }
    public int getNumSSIDs() { return SSIDNetworkList.size(); }
    public int getNumFrequencies() { return frequencyList.size(); }
    public int getNumCarriers() { return carrierList.size(); }
    public int getNumEncodings() { return encodingList.size(); }
    public int getNumAlerts() { return alertList.size(); }
    public int getYear() { return lastSeen.getYear(); }
    
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
    
    public String getEncryption() {
        if (SSIDNetworkList.isEmpty())
            return "None";
        else {
            String result = SSIDNetworkList.get(0).getEncryption();
            if (result.trim().equals("") || result == null)
                result = "None";
            return result;
        } // else
    } // getEncryption
    
    
    
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
    public void setBSSID(String BSSID) { this.BSSID = BSSID; }
    public void setChannel(int channel) { this.channel = channel; }
    public void setMaxSeen(int maxSeen) { this.maxSeen = maxSeen; }
    public void setLLC(int LLC) { this.LLC = LLC; }
    public void setData(int data) { this.data = data; }
    public void setCrypt(int crypt) { this.crypt = crypt; }
    public void setFragments(int fragments) { this.fragments = fragments; }
    public void setRetries(int retries) { this.retries = retries; }
    public void setTotal(int total) { this.total = total; }
    public void setDatasize(int datasize) { this.datasize = datasize; }
    
    public void setLastBSSTS(String lastBSSTS) {
        this.lastBSSTS = LocalDateTime.parse(lastBSSTS + getYear(), bsstsFormatter);
    } // setLastBSSTS
    
    public void setLastBSSTS_date(LocalDateTime lastBSSTS) { this.lastBSSTS = lastBSSTS; }
    
    public void setSeenBy(String seenBy) { this.seenBy = seenBy; }
    public void setLastSignalDbm(int lastSignalDbm) { this.lastSignalDbm = lastSignalDbm; }
    public void setMinSignalDbm(int minSignalDbm) { this.minSignalDbm = minSignalDbm; }
    public void setMaxSignalDbm(int maxSignalDbm) { this.maxSignalDbm = maxSignalDbm; }
    
    public void addSSIDNetwork(SSID ssidN) { SSIDNetworkList.add(ssidN); }
    public void addFrequency(String frequency) { frequencyList.add(frequency); }
    public void addCarrier(String carrier) { carrierList.add(carrier); }
    public void addEncoding(String encoding) { encodingList.add(encoding); }
    public void addClient(Client client) { clientList.add(client); }
    public void addAlert(String alert) { alertList.add(alert); }
    
    

    
    // TO STRING
    // Default toString method, not really used, can be changed without causing issues.
    public String toString() {
        return ("Network" + "\n\tManuf: " + manuf + "\n\tFirst: " + getFirstSeen() + "\n\tLast: " +
                getLastSeen() + "\n\tType: " + type + "\n\tBSSID: " + BSSID + "\n\tChannel: " + channel +
                "\n\tMax Seen: " + maxSeen + "\n\tLLC: " + LLC + "\n\tData: " +
                data + "\n\tCrypt: " + crypt + "\n\tFragments: " + fragments + "\n\tRetries: " + retries + "\n\tTotal: " + total +
                "\n\tDatasize: " + datasize + "\n\tLast BSSTS: " + getLastBSSTS() + "\n\tSeen By: " + seenBy +
                "\n\tLastSignalDbm: " + lastSignalDbm + "\n\tMinSignalDbm: " + minSignalDbm + "\n\tMaxSignalDbm: " + maxSignalDbm);
    } // toString
    
    
    // toString method called when outputting networks to a .csv file (when user chooses to export
    // values to .csv). SSIDs are enclosed in {} brackets and Clients are enclosed in [] brackets.
    public String toString_CSV() {
        StringBuilder result = new StringBuilder();
        
        result.append(manuf + "," + getFirstSeen() + "," + getLastSeen() + "," + type + ","
                      + BSSID + ",");
        
        // can have 0 SSIDs
        if (!SSIDNetworkList.isEmpty())
            for (SSID ssid : SSIDNetworkList)
                result.append(ssid.toString_CSV() + ",");
        else
            result.append("None,");
        
        result.append(channel + ",");
        
        // can have [1,infinity] frequencies
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
                      + "," + datasize + "," + getLastBSSTS() + "," + seenBy);
        
        // can have 0 alerts
        if (!alertList.isEmpty())
            for (String alert : alertList)
                result.append(alert + ",");
        else
            result.append("None,");
        
        result.append(lastSignalDbm + "," + minSignalDbm + "," + maxSignalDbm + ",");
        
        // can have 0 clients
        if (!clientList.isEmpty())
            for (Client client : clientList)
                result.append(client.toString_CSV() + ",");
        else
            result.append("None,");
        
        result.deleteCharAt(result.length()-1); // deletes last comma as it's unnecessary
        
        return result.toString();
    } // toString_CSV
    
    
    // toString method called when outputting networks to a .txt file (when user chooses to export
    // values to .txt). Mimics regular Kismet output .nettxt files with the exception that it also
    // has min/max/last signal strength after 'Seen by'.
    public String toString_TXT() {
        StringBuilder result = new StringBuilder();
        result.append("Network\n");
        result.append("  Manuf : " + manuf + "\n");
        result.append("  First : " + getFirstSeen() + "\n");
        result.append("  Last : " + getLastSeen() + "\n");
        result.append("  Type : " + type + "\n");
        result.append("  BSSID : " + BSSID + "\n");
        
        if (!SSIDNetworkList.isEmpty())
            for (SSID ssid : SSIDNetworkList)
                result.append("  " + ssid.toString_TXTNetwork());
            
        result.append("  Channel : " + channel + "\n");
        
        for (String frequency : frequencyList)
            result.append("  Frequency : " + frequency + "\n");
        
        result.append("  Max Seen : " + maxSeen + "\n");
        
        if (!carrierList.isEmpty())
            for (String carrier : carrierList)
                result.append("  Carrier : " + carrier + "\n");
        
        if (!encodingList.isEmpty())
            for (String encoding : encodingList)
                result.append("  Encoding : " + encoding + "\n");
        
        
        result.append("  LLC : " + LLC + "\n");
        result.append("  Data : " + data + "\n");
        result.append("  Crypt : " + crypt + "\n");
        result.append("  Fragments : " + fragments + "\n");
        result.append("  Retries : " + retries + "\n");
        result.append("  Total : " + total + "\n");
        result.append("  Datasize : " + datasize + "\n");
        result.append("  Last BSSTS : " + getLastBSSTS_NoYear() + "\n");
        result.append("  Seen By : " + getSeenBy_TXT() + "\n");
        result.append("  LastSignalDBM : " + lastSignalDbm + "\n");
        result.append("  MinSignalDBM : " + minSignalDbm + "\n");
        result.append("  MaxSignalDBM : " + maxSignalDbm + "\n");
        
        
        if (!alertList.isEmpty())
            for (String alert : alertList)
                result.append("  Alert : " + alert + "\n");
        
        if (!clientList.isEmpty())
            for (Client client : clientList)
                result.append("  " + client.toString_TXT());
        
        return result.toString();
    } // toString_TXT
    
    
} // Network


/*
 NETWORK FORMAT:
    Network: BSSID
    manuf
    first
    last
    type
    BSSID
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
    lastBSSTS
    seenby
    alerts [] - [0,inf)
    clients [] - [0,inf)
*/
