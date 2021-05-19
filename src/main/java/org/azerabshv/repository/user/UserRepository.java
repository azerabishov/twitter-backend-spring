package org.azerabshv.repository.user;

import org.azerabshv.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.screenName LIKE CONCAT('%',:searchKey,'%') OR u.username LIKE CONCAT('%',:searchKey,'%')")
    List<User> findUserByContent(String searchKey);

    Optional<User> findByUsername(String username);

    Optional<User> findByForgotPasswordToken(String forgot_password_token);

    Optional<User> findByEmailVerificationCode(Integer verificationCode);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phone_number);

    Boolean existsByForgotPasswordToken(String forgot_password_token);

}

