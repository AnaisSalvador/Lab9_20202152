package com.example.lab9_20202152.repository;

import com.example.lab9_20202152.entity.Historialpartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialpartidoRepository  extends JpaRepository<Historialpartido,Integer> {
}
