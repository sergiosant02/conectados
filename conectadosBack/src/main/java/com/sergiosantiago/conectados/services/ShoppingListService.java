package com.sergiosantiago.conectados.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sergiosantiago.conectados.Utils.Errors;
import com.sergiosantiago.conectados.Utils.Messages;
import com.sergiosantiago.conectados.config.CustomMapper;
import com.sergiosantiago.conectados.dtos.ProductDTO;
import com.sergiosantiago.conectados.dtos.ShoppingItemDTO;
import com.sergiosantiago.conectados.dtos.ShoppingListDTO;
import com.sergiosantiago.conectados.dtos.ext.ShoppingListDataExtDTO;
import com.sergiosantiago.conectados.dtos.ext.ShoppingListExtDTO;
import com.sergiosantiago.conectados.models.Product;
import com.sergiosantiago.conectados.models.ProductCategory;
import com.sergiosantiago.conectados.models.Room;
import com.sergiosantiago.conectados.models.ShoppingItem;
import com.sergiosantiago.conectados.models.ShoppingList;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.HttpResponse;
import com.sergiosantiago.conectados.repository.ShoppingItemRepository;
import com.sergiosantiago.conectados.repository.ShoppingListRepository;
import com.sergiosantiago.conectados.services.base.BaseServiceImpl;

@Service
public class ShoppingListService extends BaseServiceImpl<ShoppingList, Long, ShoppingListRepository> {

    private RoomService roomService;
    private ShoppingItemRepository shoppingItemRepository;
    private ProductService productService;

    public ShoppingListService(ShoppingListRepository repository, CustomMapper modelMapper, RoomService roomService,
            ShoppingItemRepository shoppingItemRepository, ProductService productService) {
        super(repository, modelMapper);
        this.roomService = roomService;
        this.shoppingItemRepository = shoppingItemRepository;
        this.productService = productService;
    }

    public HttpResponse<Set<ShoppingListDataExtDTO>> getAllShoppingList(User user, Long id) {
        HttpResponse<Set<ShoppingListDataExtDTO>> res = new HttpResponse<>();
        res.setCode(400L);
        res.setMessage(Errors.notWork);
        Room room = this.roomService.findById(id);
        Set<ShoppingListDataExtDTO> resData = new HashSet<>();
        if (room != null && room.getAllMembers().contains(user)) {
            Set<ShoppingList> shoppingLists = room.getShoppingLists();
            shoppingLists.parallelStream().forEach(sl -> {
                resData.add(sl.getExtDataDTO());
            });
            res.setData(resData);
            res.setCode(200L);
            res.setMessage(Messages.sucefull);
        }
        return res;
    }

    public HttpResponse<ShoppingListDTO> createShoppingList(User user, ShoppingListDTO shoppingListDTO) {
        HttpResponse<ShoppingListDTO> res = new HttpResponse<>();
        res.setCode(400L);
        res.setMessage(Errors.notWork);
        Room room = this.roomService.findById(shoppingListDTO.getRoomId());
        if (room != null && room.getAllMembers().contains(user)) {
            ShoppingList shoppingList = modelMapper.map(shoppingListDTO, ShoppingList.class);
            shoppingList.setRoom(room);
            shoppingList.setItems(new HashSet<>());
            shoppingList.setCompleted(false);
            room.getShoppingLists().add(shoppingList);
            this.save(shoppingList);
            res.setData(shoppingList.getDTO());
            res.setCode(200L);
            res.setMessage(Messages.sucefull);
        }
        return res;
    }

    public HttpResponse<ShoppingListExtDTO> getAllInfoAboutShoppingList(User user, ShoppingListDTO shoppingListDTO) {
        HttpResponse<ShoppingListExtDTO> res = new HttpResponse<>();
        res.setCode(400L);
        res.setMessage(Errors.notWork);
        Room room = this.roomService.findById(shoppingListDTO.getRoomId());
        ShoppingList shoppingList = findById(shoppingListDTO.getId());
        if (room != null && room.getAllMembers().contains(user) && shoppingList != null) {
            Set<ShoppingItem> shoppingItems = shoppingList.getItems();
            ShoppingListExtDTO shoppingListExtDTO = new ShoppingListExtDTO();
            shoppingListExtDTO.setShoppingItemDTOs(shoppingItems.parallelStream()
                    .map(i -> modelMapper.map(i, ShoppingItemDTO.class)).collect(Collectors.toSet()));
            shoppingListExtDTO.setShoppingListDTO(modelMapper.map(shoppingList, ShoppingListDTO.class));
            room.getShoppingLists().add(shoppingList);
            this.save(shoppingList);
            res.setData(shoppingListExtDTO);
            res.setCode(200L);
            res.setMessage(Messages.sucefull);
        }
        return res;
    }

    public HttpResponse<ShoppingListDataExtDTO> deleteShoppingList(User user, ShoppingListDataExtDTO shoppingListDTO) {
        HttpResponse<ShoppingListDataExtDTO> res = new HttpResponse<>();
        res.setCode(400L);
        res.setMessage(Errors.notWork);
        Room room = this.roomService.findById(shoppingListDTO.getRoomId());
        ShoppingList shoppingList = this.findById(shoppingListDTO.getId());
        if (room != null && room.getAllMembers().contains(user) && shoppingList != null) {
            shoppingList.getItems().stream().forEach(item -> {
                this.deleteItemById(item.getId());
            });
            room.getShoppingLists().remove(shoppingList);
            this.delete(shoppingList);
            res.setCode(200L);
            res.setMessage(Messages.sucefull);
        }
        return res;
    }

    public HttpResponse<ShoppingListDataExtDTO> changeCompleteStatus(User user,
            ShoppingListDataExtDTO shoppingListDTO) {
        HttpResponse<ShoppingListDataExtDTO> res = new HttpResponse<>();
        res.setCode(400L);
        res.setMessage(Errors.notWork);
        Room room = this.roomService.findById(shoppingListDTO.getRoomId());
        ShoppingList shoppingList = this.findById(shoppingListDTO.getId());
        if (room != null && room.getAllMembers().contains(user) && shoppingList != null) {
            shoppingList.setCompleted(!shoppingList.getCompleted());
            this.save(shoppingList);
            res.setData(shoppingList.getExtDataDTO());
            res.setCode(200L);
            res.setMessage(Messages.sucefull);
        }
        return res;
    }

    public HttpResponse<ShoppingItemDTO> createShoppingItem(User user, ShoppingItemDTO shoppingItemDTO) {
        HttpResponse<ShoppingItemDTO> res = new HttpResponse<>();
        res.setCode(400L);
        res.setMessage(Errors.notWork);
        ShoppingList shoppingList = this.findById(shoppingItemDTO.getShoppingListId());
        if (shoppingList != null) {
            Room room = shoppingList.getRoom();
            Product product = productService.findById(shoppingItemDTO.getProductId());
            if (room != null && room.getAllMembers().contains(user) && product != null) {
                ShoppingItem shoppingItem = modelMapper.map(shoppingItemDTO, ShoppingItem.class);
                shoppingItem.setProduct(product);
                product.getShoppingItems().add(shoppingItem);
                shoppingItem.setShoppingList(shoppingList);
                shoppingList.getItems().add(shoppingItem);
                this.saveItem(shoppingItem);
                this.save(shoppingList);
                res.setData(shoppingItem.getDTO());
                res.setCode(200L);
                res.setMessage(Messages.sucefull);
            }
        }
        return res;
    }

    public HttpResponse<ProductDTO> deleteProduct(User user, ProductDTO productDTO) {
        HttpResponse<ProductDTO> res = new HttpResponse<>();
        res.setCode(400L);
        Product product = productService.findById(productDTO.getId());
        if (product != null && product.getRegisterBy().equals(user)) {
            Set<ProductCategory> categories = product.getCategories().parallelStream().collect(Collectors.toSet());
            Set<ShoppingItem> shoppingItemLists = product.getShoppingItems();
            categories.parallelStream().forEach(c -> c.getProducts().remove(product));
            shoppingItemLists.stream().forEach(i -> {
                this.deleteShoppingItem(user, i.getDTO());
            });
            user.getProducts().remove(product);
            productService.delete(product);
            res.setCode(200L);
            res.setMessage(Messages.deleteOk);
        } else {
            res.setMessage(Errors.notWork);
        }

        return res;
    }

    @Transactional
    public HttpResponse<ShoppingItemDTO> deleteShoppingItem(User user, ShoppingItemDTO shoppingItemDTO) {
        HttpResponse<ShoppingItemDTO> res = new HttpResponse<>();
        res.setCode(400L);
        res.setMessage(Errors.notWork);
        ShoppingList shoppingList = this.findById(shoppingItemDTO.getShoppingListId());
        if (shoppingList != null) {
            Room room = shoppingList.getRoom();
            if (room != null && room.getAllMembers().contains(user)) {
                ShoppingItem shoppingItem = modelMapper.map(shoppingItemDTO, ShoppingItem.class);
                shoppingList.getItems().remove(shoppingItem);
                shoppingItemRepository.delete(shoppingItem);
                res.setCode(200L);
                res.setMessage(Messages.sucefull);
            }
        }
        return res;
    }

    @Transactional
    private void deleteItemById(Long id) {
        this.shoppingItemRepository.deleteById(id);
    }

    @Transactional
    private ShoppingItem saveItem(ShoppingItem item) {
        return this.shoppingItemRepository.save(item);
    }

    @Transactional
    public HttpResponse<ShoppingItemDTO> updateQuantityAndPriceShoppingItem(User user,
            ShoppingItemDTO shoppingItemDTO) {
        HttpResponse<ShoppingItemDTO> res = new HttpResponse<>();
        res.setCode(400L);
        res.setMessage(Errors.notWork);
        ShoppingList shoppingList = this.findById(shoppingItemDTO.getShoppingListId());
        Optional<ShoppingItem> itemOpt = shoppingItemRepository.findById(shoppingItemDTO.getId());
        if (shoppingList != null && itemOpt.isPresent()) {
            Room room = shoppingList.getRoom();
            if (room != null && room.getAllMembers().contains(user)) {
                ShoppingItem shoppingItem = itemOpt.get();
                shoppingItem.setQuantity(shoppingItemDTO.getQuantity());
                shoppingItem.setValue(shoppingItemDTO.getValue());
                shoppingItemRepository.save(shoppingItem);
                res.setData(modelMapper.map(shoppingItem, ShoppingItemDTO.class));
                res.setCode(200L);
                res.setMessage(Messages.sucefull);
            }
        }
        return res;
    }

}
