package com.demo.demo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "timelog")
public class Timelog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timelog_auto")
    @SequenceGenerator(name = "timelog_auto", sequenceName = "timelog_auto", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "time", nullable = true)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = true)
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private Users users;

    public Timelog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
