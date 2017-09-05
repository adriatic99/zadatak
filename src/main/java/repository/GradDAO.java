package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Grad;

public interface GradDAO extends JpaRepository<Grad, Integer> {}
