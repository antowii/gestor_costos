package com.antonella.gestor_costos.repository;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<CompraIngrediente, Long> {

}
