package com.mytrip.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mytrip.model.Hotel;
import com.mytrip.model.User;
import com.mytrip.helper.Message;
import com.mytrip.service.HotelBookService;
import com.mytrip.service.HotelService;
import com.mytrip.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private HotelBookService hotelBookService;


	@ModelAttribute
	public void addcommanData(Model model, Principal principal) {
		String name = principal.getName();
		User user = userService.finduserByEmail(name);
		System.out.println(user);
		model.addAttribute("user", user);

		List<Hotel> listofhotel = hotelService.GetAllHotel();
		model.addAttribute("listofhotel", listofhotel);


	}

	@GetMapping("/index")
	public String AdminDashboard(Model model) {
		long hotelCOunt = hotelService.HotelCOunt();
		model.addAttribute("hotelCOunt", hotelCOunt);
		
		long hotelBookingCOunt = hotelBookService.HotelBookingCOunt();
		model.addAttribute("hotelBookingCOunt", hotelBookingCOunt);

		long userCount = userService.UserCount();
		model.addAttribute("userCount", userCount);

		model.addAttribute("title", "Admin Dashboard");

		return "admin/admindashboard";
	}

	// view
	@GetMapping("/viewhotel")
	public String ViewHotel(Model model) {

		model.addAttribute("title", "View-Hotels");

		return "admin/viewhotel";
	}

	//booking
//	@GetMapping("/bookhotel")
//	public String bookHotel(Model model) {
//		List<HotelBook> getAllBookHotel = hotelBookService.GetAllBookHotel();
//		model.addAttribute("getAllBookHotel", getAllBookHotel);
//
//		model.addAttribute("title", "book-Hotels");
//
//		return "admin/bookhotel";
//	}

	//add
	@PostMapping("/addhotel")
	public String AddHotel(@ModelAttribute("hotel") Hotel hotel, @RequestParam("photo") MultipartFile file, Model model,
			HttpSession session) {

		try {

			hotel.setHotelImgUrl(file.getOriginalFilename());

			File savefile = new ClassPathResource("static/image").getFile();

			Path path = Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename());

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			Hotel hotel2 = hotelService.AddHotel(hotel);

			if (hotel2 != null) {

				session.setAttribute("message", new Message(" Hotel Added Successfully !!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/index";
		}

		return "redirect:/admin/index";
	}

	//delete
	@GetMapping("/deletehotel/{hId}")
	public String deleteHotel(@PathVariable("hId") Integer hId, Model model, HttpSession session) {

		hotelService.deleteHotel(hId);

		return "redirect:/admin/viewhotel";

	}

	//edit
	@PostMapping("/edithotel")
	public String EditHotel(@ModelAttribute("hotel") Hotel hotel, @RequestParam("photo") MultipartFile file,
			Model model, HttpSession session) {

		try {

			hotel.setHotelImgUrl(file.getOriginalFilename());

			File savefile = new ClassPathResource("static/image").getFile();

			Path path = Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename());

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			Hotel hotel2 = hotelService.AddHotel(hotel);

			if (hotel2 != null) {

				session.setAttribute("message", new Message(" Hotel Edit Successfully !!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/viewhotel";
		}

		return "redirect:/admin/viewhotel";
	}
}
