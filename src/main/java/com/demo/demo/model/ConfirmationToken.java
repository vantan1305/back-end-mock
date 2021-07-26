package com.demo.demo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cftoken")
public class ConfirmationToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_auto")
    @SequenceGenerator(name = "token_auto", sequenceName = "token_auto", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "confirmation_token", nullable = true)
    private String confirmationToken;

    @Column(name = "created_date", nullable = true)
    private Date createdDate;

    @Column(name = "expiry_date", nullable = true)
    private Date expiryDate;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "id")
    private Users users;
    
    public ConfirmationToken() {}

    public ConfirmationToken(Users user) {
        this.users = users;
        createdDate = new Date ();
        expiryDate = calculateExpiryDate(10);
        confirmationToken = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Users getUsers() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp (calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date (calendar.getTime().getTime());
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
}