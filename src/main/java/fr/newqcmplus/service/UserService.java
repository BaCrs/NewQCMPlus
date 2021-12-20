package fr.newqcmplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.newqcmplus.dao.IUserDAO;
import fr.newqcmplus.entity.User;
import fr.newqcmplus.exception.UserNotFoundException;

@Service
public class UserService {
	

	@Autowired
	private IUserDAO userDAO;
	
	public User saveUser(User user) {
		return userDAO.save(user);
	}

	public User findUserById(Integer id) {
		return userDAO.findById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + "was not found"));
	}

	public List<User> findAllUsers() {
		return userDAO.findAll();
	}

	public void deleteUser(Integer id) {
		userDAO.deleteById(id);;
	}

}