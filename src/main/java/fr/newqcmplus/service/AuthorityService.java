package fr.newqcmplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.newqcmplus.dao.IAuthorityDAO;
import fr.newqcmplus.entity.Authority;

@Service
public class AuthorityService {

	@Autowired
	private IAuthorityDAO authorityDAO;

	public Authority addAuthority(Authority authority) {
		return authorityDAO.save(authority);
	}

	public Authority findAuthorityByName(String name) {
		return authorityDAO.getAuthorityByName(name);
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