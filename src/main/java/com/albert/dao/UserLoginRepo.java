package com.albert.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.UserLogin;

@Repository
public interface UserLoginRepo extends CrudRepository<UserLogin,Long>{
	UserLogin findByuserNameAndPassWord(String eMail, String passWord);
	UserLogin findByuserName(String eMail);

}
