package com.example.jvspringboottestcontainer.service;

import com.example.jvspringboottestcontainer.dto.ProductDtoResponse;
import com.example.jvspringboottestcontainer.mapper.ProductMapper;
import com.example.jvspringboottestcontainer.model.Product;
import com.example.jvspringboottestcontainer.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
            String.format("Product with id %d is not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findAllSortedByPriceOrTitle(PageRequest pageRequest) {
        return repository.findAllSortedByPriceOrTitle(pageRequest);
    }

    @Override
    public List<ProductDtoResponse> findAllByPriceBetweenSortedByPriceOrTitle(
            BigDecimal from, BigDecimal to, PageRequest pageRequest) {
        return repository.findAllByPriceBetweenSortedByPriceOrTitle(from, to, pageRequest).stream()
            .map(mapper::mapToDto)
            .collect(Collectors.toList());
    }
}
