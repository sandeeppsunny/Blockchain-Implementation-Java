package node;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block implements Serializable {
    Integer nonce;
    String prevHash;
    String creator;
    String data;
    Long timestamp;

    public Integer getNonce() {
        return nonce;
    }

    public void setNonce(Integer nonce) {
        this.nonce = nonce;
    }

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
        this.nonce = mineBlock(prevHash, creator, data, timestamp);
        this.selfHash = calculateHash(nonce, prevHash, creator, data, timestamp);
    }

    @Override
    public String toString() {
        return "Previous Hash -> " + prevHash + ", Creator -> " + creator + ". Data -> "
                + data + ", Timestamp -> " + timestamp;
    }

    public int mineBlock(String prevHash, String creator, String data, Long timestamp) {
        int localNonce = 0;
        while(!calculateHash(localNonce, prevHash, creator, data, timestamp).substring(0, 2).equals("00")) {
            localNonce++;
            if(localNonce == Integer.MAX_VALUE) {
                break;
            }
        };
        return localNonce;
    }

    public static Block createGenesisBlock(String creator) {
        return new Block("Genesis hash", creator, "Genesis block", System.currentTimeMillis());
    }

    public String calculateHash(Integer nonce, String prevHash, String creator, String data, Long timestamp) {
        MessageDigest md;
        String input = nonce + prevHash + creator + data + Long.toString(timestamp);
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
