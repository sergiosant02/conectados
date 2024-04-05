package com.sergiosantiago.conectados.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sergiosantiago.conectados.config.CustomMapper;
import com.sergiosantiago.conectados.config.JwtUtils;
import com.sergiosantiago.conectados.dtos.RoomDTO;
import com.sergiosantiago.conectados.dtos.UserDTO;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.JwtRequest;
import com.sergiosantiago.conectados.models.auth.JwtResponse;
import com.sergiosantiago.conectados.repository.UserRepository;
import com.sergiosantiago.conectados.services.base.BaseServiceImpl;

@Service
public class UserService extends BaseServiceImpl<User, Long, UserRepository> implements UserDetailsService {

	private UserRepository userRepo;
	private JwtUtils jwtTokenUtil;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepo, JwtUtils jwtTokenUtil,
			PasswordEncoder passwordEncoder, CustomMapper modelMapper) {
		super(userRepo, modelMapper);
		this.userRepo = userRepo;
		this.jwtTokenUtil = jwtTokenUtil;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> users = userRepo.findAll();
		User user = userRepo.findByEmail(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Not found: " + username);
		}
	}

	public static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = { user.getRole() };
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

	public UserDetails loginUser(JwtRequest authenticationRequest) {
		UserDetails res = null;
		try {
			res = loadUserByUsername(authenticationRequest.getEmail());
		} catch (Exception e) {
			res = null;
		}
		return res;
	}

	public ResponseEntity<JwtResponse> registerUser(UserDTO userDTO) {

		User user = modelMapper.map(userDTO, User.class);
		ResponseEntity<JwtResponse> res = null;
		if (findByEmail(user.getEmail()) != null) {
			res = ResponseEntity.status(HttpStatus.CONFLICT).body(new JwtResponse("El usuario ya existe"));
		} else {
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole("USER");
			user = this.save(user);

			final String token = jwtTokenUtil.generateToken(user);
			res = ResponseEntity.ok(new JwtResponse(token, user));
		}
		return res;
	}

	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public List<RoomDTO> getListRoomsOwner(User user) {
		return user.getRoomOwner().stream().map(r -> modelMapper.map(r, RoomDTO.class)).collect(Collectors.toList());
	}

	public List<RoomDTO> getListRoomsGuest(User user) {
		return user.getRoomIn().stream().map(r -> modelMapper.map(r, RoomDTO.class)).collect(Collectors.toList());
	}

	public List<RoomDTO> getRoomsMember(User user) {
		List<RoomDTO> rooms = this.getListRoomsOwner(user);
		rooms.addAll(this.getListRoomsGuest(user));
		return rooms;
	}

}