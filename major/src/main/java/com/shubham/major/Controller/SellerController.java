package com.shubham.major.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shubham.major.dto.ProductDTO;
import com.shubham.major.modle.Category;
import com.shubham.major.modle.Product;
import com.shubham.major.service.CategoryService;
import com.shubham.major.service.ProductService;

@Controller
public class SellerController {
	public static String uploadDir= System.getProperty("user.dir") +"/src/main/resources/static/productImages";
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productSerivce;
	
	
	@GetMapping("/seller")
	public String sellerHome()
	{
		return "adminHome";
	}
	
	@GetMapping("/seller/categories")
	public String getCat(Model model)
	{
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
	
	//for categories Add
	@GetMapping("/seller/categories/add")
	public String getCatAdd(Model model)
	{
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/seller/categories/add")
	public String PostCatAdd(@ModelAttribute("category") Category category)
	{
		categoryService.addCategory(category);
		return "redirect:/seller/categories";
	}
	
	@GetMapping("/seller/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/seller/categories";	
	}
	
	@GetMapping("/seller/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		Optional<Category> category =categoryService.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category",category.get());
			return "categoryiesAdd";
		}
		else {
			return "404";
		}
	}
	
	//Product Session
	@GetMapping("/seller/products")
	public String products(Model model)
	{
		model.addAttribute("products",productSerivce.getAllProduct());
		return "products";
	}
	
	@GetMapping("/seller/products/add")
	public String productAddGet(Model model)
	{
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories",categoryService.getAllCategory());
		return "product123";
	}
	
	@PostMapping("/seller/products/add")
	public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
			@RequestParam("productImage")MultipartFile file,
			@RequestParam("imgName")String imgName) throws IOException{
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath =Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}else {
			imageUUID =imgName;
		}
		product.setImageName(imageUUID);
		productSerivce.addProduct(product);
		
		
		return "redirect:/seller/products";
	}
	
	@GetMapping("/seller/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		productSerivce.removeProductById(id);
		return "redirect:/seller/products/add";	
	}
	
	@GetMapping("/seller/product/update/{id}")
	public String updateProductGet(@PathVariable long id,Model model) {
		Product product=productSerivce.getProductById(id).get();
		ProductDTO productDTO =new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
//		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("productDTO",productDTO);
		
		return "produc";
	}
	
	

}
















