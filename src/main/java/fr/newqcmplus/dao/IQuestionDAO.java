package fr.newqcmplus.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.newqcmplus.entity.Question;

public interface IQuestionDAO extends JpaRepository<Question, Integer> {

}
