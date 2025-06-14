package com.javaweb.repository;

import com.javaweb.repository.entity.PackageMemberEntity;
import com.javaweb.repository.entity.PackageMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageMemberRepository extends JpaRepository<PackageMemberEntity, PackageMemberId> {
    Optional<PackageMemberEntity> findByUserIdAndStatus(Integer userId, String status);
}