package com.demo.demo.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_auto")
    @SequenceGenerator(name = "user_auto", sequenceName = "user_auto", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", nullable = true)
    private String userName;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(nullable = true)
    private String staffType; // loai nhan vien: 1-NV chinh thuc, 2-thu viec, 3-hoc viec, 4-ctv

    @Column(nullable = true)
    private String avatar;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String middleName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String identityCard; // CMND/CCCD

    @Column(nullable = true)
    private String homeTown;

    @Column(nullable = true)
    private String education; //học vấn: 1-DH, 2-CD, 3: Khác

    @Column(nullable = true)
    private String school;

    @Column(nullable = true)
    private String specialized; // chuyen nganh

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(name = "dob", nullable = true)
    private Date dob;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "users")
    private Set<Timelog> timelogs;

    public Users() {
    }

    public Users(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }


    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Users(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Set<Timelog> getTimelogs() {
        return timelogs;
    }

    public void setTimelogs(Set<Timelog> timelogs) {
        this.timelogs = timelogs;
    }
}
