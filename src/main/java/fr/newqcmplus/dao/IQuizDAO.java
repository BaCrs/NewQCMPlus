package fr.newqcmplus.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.newqcmplus.entity.Quiz;

public interface IQuizDAO extends JpaRepository<Quiz, Integer> {

}
