package com.sergiosantiago.conectados.services.base;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergiosantiago.conectados.config.CustomMapper;

public abstract class BaseServiceImpl<T, ID, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {

	protected R repository;

	protected CustomMapper modelMapper;

	protected BaseServiceImpl(R repository, CustomMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public T save(T entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional
	public T update(T entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		repository.delete(entity);
	}

	@Override
	@Transactional
	public void deleteById(ID id) {
		repository.deleteById(id);
	}

	@Override
	public T findById(ID id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}
}
