package com.fincom.fintech.jpa;

import com.fincom.fintech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findByUserName(String userName);


}
