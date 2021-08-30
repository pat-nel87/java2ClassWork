import java.io.Serializable;

public class MessagePacket implements Serializable {

    private String message;
    private String sender;
    private String sendTo;
    private int packetHeader;

    public MessagePacket(String message, String sender, String sendTo, int packetHeader) {
        this.message = message;
        this.sender = sender;
        this.sendTo = sendTo;
        this.packetHeader = packetHeader;

    }
    public String getMessage() { return this.message; }
    public String getSender() { return this.sender; }
    public String getSendTo() { return this.sendTo; }
    public int getPacketHeader() { return this.packetHeader; }

}
