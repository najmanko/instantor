package cz.najmanko.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response_id_seq")
    @SequenceGenerator(name = "response_id_seq", sequenceName = "response_id_seq", allocationSize = 1)
    private Long id;

    private Date delivered;

    private String json;

    @Column(name = "msg_id")
    private String msgId;

    private String action;

    private String encryption;
    
    private String payload;
    
    private String hash;

    private Date saved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDelivered() {
        return delivered;
    }

    public void setDelivered(Date delivered) {
        this.delivered = delivered;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getSaved() {
        return saved;
    }

    public void setSaved(Date saved) {
        this.saved = saved;
    }
}