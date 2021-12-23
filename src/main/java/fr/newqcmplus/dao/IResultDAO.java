package fr.newqcmplus.dao;

import fr.newqcmplus.entity.Quiz;
import fr.newqcmplus.entity.Result;
import fr.newqcmplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IResultDAO extends JpaRepository<Result, Integer> {

    @Query("SELECT r FROM Result r WHERE r.quiz = :quiz AND r.user = :user")
    public List<Result> getResultsByUserAndQuiz(@Param("user") User user, @Param("quiz") Quiz quiz);
	
}
