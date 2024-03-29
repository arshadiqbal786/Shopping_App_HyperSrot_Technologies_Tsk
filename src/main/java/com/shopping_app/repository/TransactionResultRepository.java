package com.shopping_app.repository;

import com.shopping_app.entity.TransactionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionResultRepository extends JpaRepository<TransactionResult, Long> {
}