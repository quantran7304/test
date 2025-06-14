package com.javaweb.repository.entity;

import java.io.Serializable;
import java.util.Objects;

public class PackageMemberId implements Serializable {
    private Integer userId;
    private Integer membershipId;

    public PackageMemberId() {}
    public PackageMemberId(Integer userId, Integer membershipId) {
        this.userId = userId;
        this.membershipId = membershipId;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getMembershipId() { return membershipId; }
    public void setMembershipId(Integer membershipId) { this.membershipId = membershipId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PackageMemberId)) return false;
        PackageMemberId that = (PackageMemberId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(membershipId, that.membershipId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, membershipId);
    }
}