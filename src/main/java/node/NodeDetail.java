package node;

import java.io.Serializable;

public class NodeDetail implements Serializable {
    String ipAddress;
    String portNo;

    public NodeDetail() {
        super();
    }

    public NodeDetail(String ipAddress, String portNo) {
        this.ipAddress = ipAddress;
        this.portNo = portNo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || obj.getClass()!= this.getClass())
            return false;
        NodeDetail nodeDetail = (NodeDetail) obj;
        return (nodeDetail.ipAddress.equals(this.ipAddress)  && nodeDetail.portNo.equals(this.portNo));
    }

    @Override
    public int hashCode() {
        return (this.ipAddress + this.portNo).hashCode();
    }
}
