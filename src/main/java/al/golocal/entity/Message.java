package al.golocal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long messageId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "senderId")
    private User sender;

    @JsonProperty("sender")
    public Long getSenderId() {
        return sender != null ? sender.getUserId() : null;
    }

    public void setSenderId(Long senderId) {
        if (senderId == null) {
            this.sender = null; // If productId is null, remove the association
        } else {
            // Fetch the Product entity from the database or create a proxy
            this.sender = new User();
            this.sender.setUserId(sender.getUserId()); // Use this if you're using a detached object or JPA reference
        }
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "receiverId")
    private User receiver;

    @JsonProperty("receiver")
    public Long setReceiverId() {
        return receiver != null ? receiver.getUserId() : null;
    }

    public void setReceiverId(Long receiverId) {
        if (receiverId == null) {
            this.receiver = null; // If productId is null, remove the association
        } else {
            // Fetch the Product entity from the database or create a proxy
            this.receiver = new User();
            this.receiver.setUserId(receiver.getUserId()); // Use this if you're using a detached object or JPA reference
        }
    }

    @CreationTimestamp
    @Column(updatable = false)
    private Date addDate;

    @UpdateTimestamp
    @Column
    private Date modDate;

    private Integer status;

}
