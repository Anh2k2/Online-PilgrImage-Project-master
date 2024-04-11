package com.mytrip.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mytrip.model.Hotel;
import com.mytrip.model.HotelBook;
import com.mytrip.model.User;
import com.mytrip.service.HotelBookService;
import com.mytrip.service.HotelService;
import com.mytrip.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private HotelBookService hotelBookService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute
	public void addcommanData(Model model, Principal principal) {
		String name = principal.getName();
		User user = userService.finduserByEmail(name);
		System.out.println(user);
		model.addAttribute("user", user);

		List<HotelBook> userBookingHotel = hotelBookService.UserBookingHotel(user);
		model.addAttribute("userBookingHotel", userBookingHotel);

	}

//	@GetMapping("/index")
//	public String UserIndex(Model model) {
//		model.addAttribute("title", "Holiday-Package");
//		//List<Package> allPAckage = packageService.getAllPAckage();
//
//		//model.addAttribute("allPAckage", allPAckage);
//		return "user/package";
//	}
	@GetMapping("/index")
	public String UserIndex(Model model) {
		model.addAttribute("title", "Hotels");

		List<Hotel> hotel = hotelService.GetAllHotel();

		model.addAttribute("hotel", hotel);

		return "user/hotels";
	}

	@GetMapping("/hotel")
	public String Hotels(Model model) {
		model.addAttribute("title", "Hotels");

		List<Hotel> hotel = hotelService.GetAllHotel();

		model.addAttribute("hotel", hotel);

		return "user/hotels";
	}

	@PostMapping("/change")

	public String ChangePassword(@RequestParam("old") String old, @RequestParam("new") String NewPassword,
								 HttpSession Session, Model model) {

		User user = (User) model.getAttribute("user");

		String uppass = user.getPassword();

		if (this.bCryptPasswordEncoder.matches(old, uppass)) {

			user.setPassword(this.bCryptPasswordEncoder.encode(NewPassword));

			User saveUser = userService.SaveUser(user);

			Session.setAttribute("msg", "Password Change Successfully !!");

		} else {

			Session.setAttribute("msg", "Invalid Current Password Please Try Again !!");

			return "redirect:/user/index";

		}
		return "redirect:/user/index";
	}

}
