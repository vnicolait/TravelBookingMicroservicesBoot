package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.UserAlreadyExist;
import com.model.UserEntity;
import com.repository.UserJpaRepository;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
	UserJpaRepository repository;
    
	@Override
	public void registerUser(UserEntity entity) {
      UserEntity user=repository.findByEmail(entity.getEmail());
      if(user!=null) {
    	  throw new UserAlreadyExist("user with email already exist");
      }
       repository.save(user);
	}

	@Override
	public void login(String email, String password) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
