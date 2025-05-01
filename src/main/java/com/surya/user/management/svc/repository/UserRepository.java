package com.surya.user.management.svc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surya.user.management.svc.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {

}
