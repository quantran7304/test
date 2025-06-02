package com.javaweb.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "\"User\"")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // hoặc AUTO
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "pass_word", nullable = false, length = 255)
    private String password;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

//
//    @Column(name = "birthday")
//    private LocalDate birthday;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;

//    @Column(name = "CreatedArt")
//    private LocalDateTime createdArt;

//    @Column(name = "ImgPath", length = 255)
//    private String imgPath;
//
    @ManyToOne
    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID")
    private RoleEntity roleId;

    public Integer getUserId() {
        return userId;
    }

    public UserEntity() {
        // Constructor mặc định không tham số
    }

    public UserEntity(String firstName, String lastName, String email, String phone, String password, RoleEntity role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.roleId = role;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }

//    public LocalDateTime getCreatedArt() {
//        return createdArt;
//    }
//
//    public void setCreatedArt(LocalDateTime createdArt) {
//        this.createdArt = createdArt;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public LocalDate getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(LocalDate birthday) {
//        this.birthday = birthday;
//    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public RoleEntity getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleEntity roleId) {
        this.roleId = roleId;
    }

    //    public String getImgPath() {
//        return imgPath;
//    }
//
//    public void setImgPath(String imgPath) {
//        this.imgPath = imgPath;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}