package node;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block implements Serializable {
    String prevHash;
    String creator;
    String data;
    Long timestamp;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSelfHash() {
        return selfHash;
    }

    public void setSelfHash(String selfHash) {
        this.selfHash = selfHash;
    }

    String selfHash;

    public Block (String prevHash, String creator,  String data, Long timestamp) {
        this.prevHash = prevHash;
        this.creator = creator;
        this.data = data;
        this.timestamp = timestamp;
        this.selfHash = calculateHash(prevHash, creator, data, timestamp);
    }

    @Override
    public String toString() {
        return "Previous Hash -> " + prevHash + ", Creator -> " + creator + ". Data -> "
                + data + ", Timestamp -> " + timestamp;
    }

    public static Block createGenesisBlock(String creator) {
        return new Block("Genesis hash", creator, "Genesis block", System.currentTimeMillis());
    }

    public String calculateHash(String prevHash, String creator, String data, Long timestamp) {
        MessageDigest md;
        String input = prevHash + creator + data + Long.toString(timestamp);
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException e) {
            return "SHA-256 algorithm not found.";
        }
        byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder result = new StringBuilder();
        for(byte hashByte: hashBytes) {
            String hexFromByte = Integer.toHexString(0xff & hashByte);
            if(hexFromByte.length() == 1) {
                // Padding
                result.append("0");
            }
            result.append(hexFromByte);
        }
        return result.toString();
    }
}
