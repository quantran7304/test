package com.javaweb.repository.entity;


@Entity
@Table(name = "Role")
public class RoleEntity {

    @Id
    @Column(name = "RoleID")
    private Integer roleId;

    private String roleName;

    private String description;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}