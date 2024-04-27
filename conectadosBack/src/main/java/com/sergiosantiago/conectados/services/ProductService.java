package com.sergiosantiago.conectados.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sergiosantiago.conectados.Utils.Errors;
import com.sergiosantiago.conectados.Utils.Messages;
import com.sergiosantiago.conectados.config.CustomMapper;
import com.sergiosantiago.conectados.dtos.ProductDTO;
import com.sergiosantiago.conectados.models.Product;
import com.sergiosantiago.conectados.models.ProductCategory;
import com.sergiosantiago.conectados.models.Room;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.HttpResponse;
import com.sergiosantiago.conectados.repository.ProductRepository;
import com.sergiosantiago.conectados.services.base.BaseServiceImpl;

@Service
public class ProductService extends BaseServiceImpl<Product, Long, ProductRepository> {

	public static Logger logger = Logger.getLogger(ProductService.class.toString());

	private UserService userService;
	private RoomService roomService;
	private ProductCategoryService productCategoryService;

	public ProductService(ProductRepository productRepository, CustomMapper modelMapper, UserService userService,
			RoomService roomService, ProductCategoryService productCategoryService) {
		super(productRepository, modelMapper);
		this.userService = userService;
		this.roomService = roomService;
		this.productCategoryService = productCategoryService;
	}

	public List<Product> findLikeByName(String name) {
		return repository.findLikeByName(name);
	}

	public List<Product> findByName(String name) {
		return repository.findByName(name);
	}

	public HttpResponse<ProductDTO> createproduct(User user, ProductDTO productDTO) {
		HttpResponse<ProductDTO> res = new HttpResponse<>();
		res.setCode(400L);
		if (productDTO.getCategoryIds() == null) {
			productDTO.setCategoryIds(new HashSet<>());
		}
		try {
			Product product = modelMapper.map(productDTO, Product.class);
			Room room = roomService.findById(productDTO.getRoomId());
			Set<ProductCategory> categories = productCategoryService.findAllByIds(productDTO.getCategoryIds());

			categories.parallelStream().forEach(c -> {
				c.getProducts().add(product);
			});
			product.getCategories().addAll(categories);
			product.setRoom(room);
			product.setRegisterBy(user);
			room.getProducts().add(product);
			user.getProducts().add(product);
			this.save(product);
			res.setCode(200L);
			res.setMessage(Messages.sucefull);
			productDTO = product.getDTO();
			res.setData(productDTO);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			res.setMessage(Errors.notWork);
		}
		return res;
	}

	public HttpResponse<ProductDTO> deleteProduct(User user, ProductDTO productDTO) {
		HttpResponse<ProductDTO> res = new HttpResponse<>();
		res.setCode(400L);
		Product product = findById(productDTO.getId());
		if (product != null && product.getRegisterBy().equals(user)) {
			Set<ProductCategory> categories = productCategoryService.findAllByIds(
					product.getCategories().parallelStream().map(c -> c.getId()).collect(Collectors.toSet()));
			categories.parallelStream().forEach(c -> c.getProducts().remove(product));
			user.getProducts().remove(product);
			this.delete(product);
			res.setCode(200L);
			res.setMessage(Messages.deleteOk);
		} else {
			res.setMessage(Errors.notWork);
		}

		return res;
	}

	public HttpResponse<ProductDTO> assignCategory(ProductDTO productDTO) {
		HttpResponse<ProductDTO> res = new HttpResponse<>();
		res.setCode(400L);
		res.setMessage(Errors.notWork);
		if (productDTO.getCategoryIds() != null && productDTO.getId() != null) {
			Set<ProductCategory> categories = productCategoryService.findAllByIds(productDTO.getCategoryIds());
			Product product = this.findById(productDTO.getId());
			product.getCategories().addAll(categories);
			categories.parallelStream().forEach(c -> c.getProducts().add(product));
			res.setCode(200L);
			res.setMessage(Messages.sucefull);
		}
		return res;
	}

	public HttpResponse<ProductDTO> unassignCategory(ProductDTO productDTO) {
		HttpResponse<ProductDTO> res = new HttpResponse<>();
		res.setCode(400L);
		res.setMessage(Errors.notWork);
		if (productDTO.getCategoryIds() != null && productDTO.getId() != null) {
			Set<ProductCategory> categories = productCategoryService.findAllByIds(productDTO.getCategoryIds());
			Product product = this.findById(productDTO.getId());
			product.getCategories().removeAll(categories);
			categories.parallelStream().forEach(c -> c.getProducts().remove(product));
			res.setCode(200L);
			res.setMessage(Messages.sucefull);
		}
		return res;
	}

}
