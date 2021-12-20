package fr.newqcmplus.dao;

import fr.newqcmplus.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IResultDAO extends JpaRepository<Result, Integer> {
	
}
