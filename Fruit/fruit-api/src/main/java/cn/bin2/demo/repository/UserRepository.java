package cn.bin2.demo.repository;

import cn.bin2.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by bing on 2017/9/8.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);

    User findByUserNameOrEmail(String username, String email);

    User findByEmail(String email);
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User u set u.outDate=:outDate, u.validataCode=:validataCode where u.email=:email")
    int setOutDateAndValidataCode(@Param("outDate") String outDate, @Param("validataCode") String validataCode, @Param("email") String email);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User u set u.passWord=:passWord where u.email=:email")
    int setNewPassword(@Param("passWord") String passWord, @Param("email") String email);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User  u set u.introduction=:introduction where u.email=:email")
    int setIntroduction(@Param("introduction") String introduction, @Param("email") String email);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User u set u.userName=:userName where u.email=:email")
    int setUserName(@Param("userName") String userName, @Param("email") String email);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User u set u.profilePicture=:profilePicture where u.id=:id")
    int setProfilePicture(@Param("profilePicture") String profilePicture, @Param("id") Long id);

//    @Query("from User u where u.name=:name")
//    User findUser(@Param("name") String name);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User u set u.backgroundPicture=:backgroundPicture where u.id=:id")
    int setBackgroundPicture(@Param("backgroundPicture") String backgroundPicture, @Param("id") Long id);


}
