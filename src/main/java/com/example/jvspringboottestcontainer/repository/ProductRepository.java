package com.example.jvspringboottestcontainer.repository;

import com.example.jvspringboottestcontainer.model.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id = ?1 and p.deleted = false")
    Product getById(Long id);

    @Modifying
    @Query("update Product p set p.deleted = true where p.id = ?1 and p.deleted = false")
    void deleteById(Long id);

    @Query(value = "select p from Product p where p.deleted = false")
    List<Product> findAllSortedByPriceOrTitle(Pageable pageable);

    @Query(value = "select p from Product p where p.price between ?1 and ?2 and p.deleted = false")
    List<Product> findAllByPriceBetweenSortedByPriceOrTitle(
            BigDecimal from, BigDecimal to, Pageable pageable);
}
