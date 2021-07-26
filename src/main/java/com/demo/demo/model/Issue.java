package com.demo.demo.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_auto")
    @SequenceGenerator(name = "issue_auto", sequenceName = "issue_auto", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "status", nullable = true)
    private Integer status;

    @Column(name = "assignee", nullable = true)
    private Integer assignee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "issue_project",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects = new HashSet<> ();

    @OneToMany(mappedBy = "issue")
    private Set<Timelog> timelogs;

    public Issue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAssignee() {
        return assignee;
    }

    public void setAssignee(Integer assignee) {
        this.assignee = assignee;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Timelog> getTimelogs() {
        return timelogs;
    }

    public void setTimelogs(Set<Timelog> timelogs) {
        this.timelogs = timelogs;
    }
}
