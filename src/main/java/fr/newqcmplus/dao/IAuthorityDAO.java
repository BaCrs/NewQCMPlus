package fr.newqcmplus.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.newqcmplus.entity.Authority;

public interface IAuthorityDAO extends JpaRepository<Authority, Integer> {

}
