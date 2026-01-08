package com.eraste.orderservice.infrastructure.adapter.out.persistence;

import com.eraste.orderservice.domain.model.Order;
import com.eraste.orderservice.domain.model.OrderStatus;
import com.eraste.orderservice.domain.port.out.OrderRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA Adapter implementing the OrderRepositoryPort.
 * <p>
 * This class serves as the secondary adapter (driven adapter) in the hexagonal architecture.
 * It implements the {@link OrderRepositoryPort} output port using Spring Data JPA.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see OrderRepositoryPort
 * @see OrderJpaRepository
 * @see OrderMapper
 */
@Component
public class OrderJpaAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository jpaRepository;
    private final OrderMapper mapper;

    /**
     * Constructs an OrderJpaAdapter with required dependencies.
     *
     * @param jpaRepository the Spring Data JPA repository
     * @param mapper        the mapper for domain/entity conversion
     */
    public OrderJpaAdapter(OrderJpaRepository jpaRepository, OrderMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order save(Order order) {
        OrderJpaEntity entity;
        if (order.getId() != null) {
            entity = jpaRepository.findById(order.getId())
                    .orElse(mapper.toJpaEntity(order));
            mapper.updateJpaEntity(entity, order);
        } else {
            entity = mapper.toJpaEntity(order);
        }
        OrderJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return jpaRepository.findByOrderNumber(orderNumber)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return jpaRepository.findByStatus(status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByOrderNumber(String orderNumber) {
        return jpaRepository.existsByOrderNumber(orderNumber);
    }
}
