package fr.newqcmplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.newqcmplus.dao.IAuthorityDAO;
import fr.newqcmplus.entity.Authority;
import fr.newqcmplus.exception.AuthorityNotFoundException;

@Service
public class AuthorityService {

	@Autowired
	private IAuthorityDAO authorityDAO;

	public Authority addAuthority(Authority authority) {
		return authorityDAO.save(authority);
	}

	public Authority findAuthorityById(int id) {
		return authorityDAO.findById(id).orElseThrow(() -> new AuthorityNotFoundException("Authority by id " + id + "was not found"));
	}

	public List<Authority> findAllAuthorities() {
		return authorityDAO.findAll();
	}

	public Authority updateAuthority(Authority authority) {
		return authorityDAO.save(authority);
	}

	public void deleteAuthority(int id) {
		authorityDAO.deleteById(id);
	}

}