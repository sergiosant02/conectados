package com.sergiosantiago.conectados.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sergiosantiago.conectados.Utils.Constants;
import com.sergiosantiago.conectados.Utils.Errors;
import com.sergiosantiago.conectados.Utils.Messages;
import com.sergiosantiago.conectados.Utils.Utils;
import com.sergiosantiago.conectados.config.CustomMapper;
import com.sergiosantiago.conectados.dao.RoomDao;
import com.sergiosantiago.conectados.dao.UserDao;
import com.sergiosantiago.conectados.dtos.ProductCategoryDTO;
import com.sergiosantiago.conectados.dtos.RoomDTO;
import com.sergiosantiago.conectados.dtos.UserDTO;
import com.sergiosantiago.conectados.dtos.ext.ProductExtDTO;
import com.sergiosantiago.conectados.dtos.ext.RoomExtDTO;
import com.sergiosantiago.conectados.models.Product;
import com.sergiosantiago.conectados.models.ProductCategory;
import com.sergiosantiago.conectados.models.Room;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.HttpResponse;
import com.sergiosantiago.conectados.repository.ProductCategoryRepository;
import com.sergiosantiago.conectados.repository.RoomRepository;
import com.sergiosantiago.conectados.services.base.BaseServiceImpl;

@Service
public class RoomService extends BaseServiceImpl<Room, Long, RoomRepository> {

	private UserService userService;
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoomDao roomDao;

	public RoomService(RoomRepository roomRepository, CustomMapper modelMapper, UserService userService,
			ProductCategoryRepository productCategoryRepository) {
		super(roomRepository, modelMapper);
		this.userService = userService;
		this.productCategoryRepository = productCategoryRepository;

	}

	public RoomDTO createRoom(RoomDTO roomDTO, User user) {
		Room room = modelMapper.map(roomDTO, Room.class);
		room.setCode(Utils.getRandomString(Constants.codeLength));
		room.setOwner(user);
		user.getRoomOwner().add(room);
		this.save(room);
		return room.getDTO();
	}

	@Transactional
	public Room createRoomRoom(RoomDTO roomDTO, User user) {
		Room room = modelMapper.map(roomDTO, Room.class);
		room.setCode(Utils.getRandomString(Constants.codeLength));
		room.setOwner(user);
		user.getRoomOwner().add(room);
		repository.save(room);
		return room;
	}

	public List<Room> getRoomsOfUser(User user) {
		List<Room> res = new ArrayList<>();
		res.addAll(user.getRoomOwner());
		res.addAll(user.getRoomIn());
		return res;
	}

	public List<UserDTO> getRoomsGuest(Room room) {
		return room.getBelongTo().stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
	}

	public List<UserDTO> getUserMembersInRoom(Long id) {
		Optional<Room> room = repository.findById(id);
		List<UserDTO> res = new ArrayList<>();
		if (room.isPresent()) {
			res.addAll(this.getRoomsGuest(room.get()));
			res.add(room.get().getOwner().getDTO());
		}
		return res;
	}

	@Transactional
	private ProductCategory saveCategory(ProductCategory category) {
		return productCategoryRepository.save(category);
	}

	@Transactional
	public HttpResponse<RoomDTO> joinToRoom(String roomCode, User user) {
		HttpResponse<RoomDTO> res = new HttpResponse();
		res.setCode(400L);
		Optional<Room> roomOpt = repository.findByCode(roomCode);
		if (roomOpt.isPresent()) {
			Room room = roomOpt.get();
			if (room.getOwner().getId().equals(user.getId())) {
				res.setMessage(Errors.isOwner);
			} else if (room.getBelongTo().stream().map(u -> u.getId()).toList().contains(user.getId())) {
				res.setMessage(Errors.alreadyJoined);
			} else {
				room.getBelongTo().add(user);
				user.getRoomIn().add(room);
				room = repository.save(room);
				res.setData(room.getDTO());
				res.setCode(200L);
			}
		} else {
			res.setMessage(Errors.notFound);
		}
		return res;
	}

	public HttpResponse<RoomDTO> leaveRoom(Long id, User user) {
		HttpResponse<RoomDTO> res = new HttpResponse<>();
		res.setCode(400L);
		Optional<Room> room = repository.findById(id);
		if (room.isPresent()) {
			Room roomRes = room.get();
			if (roomRes.getOwner().getId().equals(user.getId())) {
				if (roomRes.getBelongTo().isEmpty()) {
					this.delete(roomRes);
					res.setCode(200L);
				} else {
					User newOwner = (User) room.get().getBelongTo().toArray()[0];
					room.get().setOwner(newOwner);
					newOwner.getRoomOwner().add(room.get());
					newOwner.getRoomIn().remove(room.get());
					user.getRoomOwner().remove(room.get());
					this.save(roomRes);
					res.setCode(200L);
				}
				res.setMessage(Messages.leftOk);
			} else {
				roomRes.getBelongTo().remove(user);
				user.getRoomIn().remove(roomRes);
				this.save(roomRes);
				res.setData(modelMapper.map(roomRes, RoomDTO.class));
				res.setCode(200L);
			}
		} else {
			res.setMessage(Errors.notFound);
		}
		return res;
	}

	public HttpResponse<RoomDTO> fireMember(RoomDTO roomDTO, User user) {
		HttpResponse<RoomDTO> res = new HttpResponse<>();
		res.setCode(400L);
		Optional<Room> roomOpt = repository.findById(roomDTO.getId());
		if (roomOpt.isPresent()) {
			Room room = roomOpt.get();
			if (room.getOwner().equals(user) && !roomDTO.getBelongToUserIds().contains(room.getOwner().getId())) {
				List<User> userToFire = room.getBelongTo().stream()
						.filter(u -> roomDTO.getBelongToUserIds().contains(u.getId())).collect(Collectors.toList());
				userToFire.forEach(u -> u.getRoomIn().remove(room));
				room.getBelongTo().removeAll(userToFire);
				this.save(room);
				res.setCode(200L);
				res.setMessage(Messages.usersFiredOk);
				res.setData(room.getDTO());
			} else {
				res.setCode(400L);
				res.setMessage(Errors.notYourSelf);
			}
		} else {
			res.setCode(400L);
			res.setMessage(Errors.notFound);
		}

		return res;
	}

	public HttpResponse<RoomDTO> deleteRoom(User user, Long id) {
		HttpResponse<RoomDTO> res = new HttpResponse<>();
		res.setCode(400L);
		Optional<Room> roomOpt = repository.findById(id);
		if (roomOpt.isPresent()) {
			Room room = roomOpt.get();
			if (room.getOwner().equals(user)) {
				room.getBelongTo().parallelStream().forEach(u -> u.getRoomIn().remove(room));
				this.delete(room);
				res.setCode(200L);
				res.setMessage(Messages.deleteOk);
			} else {
				res.setMessage(Errors.notYourSelf);
			}
		} else {
			res.setMessage(Errors.notFound);
		}
		return res;
	}

	public HttpResponse<ProductExtDTO> getProductDTOsOfRoom(Long id) {
		HttpResponse<ProductExtDTO> res = new HttpResponse<>();
		res.setCode(400L);
		res.setMessage(Errors.notFound);
		Optional<Room> roomOpt = repository.findById(id);
		if (roomOpt.isPresent()) {
			Room room = roomOpt.get();
			Set<Product> products = room.getProducts();
			Set<ProductCategory> categories = products.parallelStream().map(p -> p.getCategories()).flatMap(Set::stream)
					.collect(Collectors.toSet());
			ProductExtDTO productExtDTO = new ProductExtDTO(
					products.parallelStream().map(Product::getDTO)
							.collect(Collectors.toSet()),
					categories.parallelStream().map(ProductCategory::getDTO)
							.collect(Collectors.toSet()));
			res.setData(productExtDTO);
			res.setCode(200L);
			res.setMessage(Messages.sucefull);
		}
		return res;
	}

	public HttpResponse<ProductCategoryDTO> createProductCategory(User user, ProductCategoryDTO productCategoryDTO) {
		HttpResponse<ProductCategoryDTO> res = new HttpResponse<>();
		res.setCode(400L);
		res.setMessage(Errors.notWork);
		ProductCategory productCategory = modelMapper.map(productCategoryDTO, ProductCategory.class);
		Room room = findById(productCategoryDTO.getRoomId());
		if (room != null) {
			room.getProductCategories().add(productCategory);
			productCategory.setRoom(room);
			productCategory.setProducts(new HashSet<>());
			productCategory.setRegisterBy(user);
			user.getProductCategories().add(productCategory);
			this.save(room);
			res.setCode(200L);
			res.setData(productCategory.getDTO());
			res.setMessage(Messages.sucefull);
		}

		return res;
	}

	@Transactional
	private void deleteCategory(Long id) {
		this.productCategoryRepository.deleteById(id);
	}

	public HttpResponse<ProductCategoryDTO> deleteCategory(User user, Long id) {
		HttpResponse<ProductCategoryDTO> res = new HttpResponse<>();
		res.setCode(400L);
		res.setMessage(Errors.notWork);
		ProductCategory productCategory = this.productCategoryRepository.findById(id).orElse(null);
		if (productCategory != null) {
			if (productCategory.getRegisterBy().equals(user)) {
				productCategory.getProducts().parallelStream().forEach(p -> p.getCategories().remove(productCategory));
				productCategory.getRoom().getProductCategories().remove(productCategory);
				productCategory.getRegisterBy().getProductCategories().remove(productCategory);
				this.deleteCategory(id);
				res.setCode(200L);
				res.setMessage(Messages.deleteOk);
			}
		}
		return res;
	}

	public HttpResponse<RoomExtDTO> getAllRoomInfo(Long id) {
		HttpResponse<RoomExtDTO> res = new HttpResponse<>();
		res.setCode(400L);
		res.setMessage(Errors.notWork);
		Room room = findById(id);
		if (room != null) {
			RoomExtDTO roomExtDto = new RoomExtDTO();
			roomExtDto.getProductCategoryDTOs().addAll(room.getProductCategories().parallelStream()
					.map(ProductCategory::getDTO).collect(Collectors.toSet()));
			roomExtDto.getProductDTOs().addAll(room.getProducts().parallelStream()
					.map(Product::getDTO).collect(Collectors.toSet()));
			roomExtDto.setRoomDTO(room.getDTO());
			res.setCode(200L);
			res.setMessage(Messages.sucefull);
			res.setData(roomExtDto);
		}
		return res;
	}

}
