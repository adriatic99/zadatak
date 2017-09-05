package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Tipoj;

public interface TipojDAO extends JpaRepository<Tipoj, Integer> {}
