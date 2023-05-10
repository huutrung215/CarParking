package com.trycoder.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.web.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trycoder.dto.MonthlyTicketDto;
import com.trycoder.dto.ParkingDto;
import com.trycoder.dto.PositionDto;
import com.trycoder.model.Car;
import com.trycoder.model.MonthlyTicket;
import com.trycoder.model.Parking;
import com.trycoder.model.ParkingPrice;
import com.trycoder.model.Position;
import com.trycoder.model.PositionCondition;
import com.trycoder.model.PositionStatus;
import com.trycoder.model.UserDtls;
import com.trycoder.repository.ParkingPriceRepository;
import com.trycoder.repository.PositionRepository;
import com.trycoder.repository.UserRepository;
import com.trycoder.service.CarService;
import com.trycoder.service.ParkingPriceService;
import com.trycoder.service.ParkingService;
import com.trycoder.service.PositionService;
import com.trycoder.service.MonthlyTicketService;
import com.trycoder.service.UserService;
import org.springframework.web.server.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	PositionService positionService;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	ParkingService parkingService;
	
	@Autowired
	CarService carService;
	
	@Autowired
    private ParkingPriceRepository parkingPriceRepo;
	
	@Autowired
	ParkingPriceService parkingPriceService;
	
	@Autowired
	MonthlyTicketService monthlyTicketService;
	
	
	// Trang index
	@GetMapping("/")
	public String index(Model model) {
		long availablePositions = positionRepository.countByStatus(PositionStatus.AVAILABLE) - positionRepository.countByCondition(PositionCondition.INACTIVE);
		model.addAttribute("availablePositions", availablePositions);
		
		long countNewUser = userRepo.countByRoleAndCreatedUserBetween("ROLE_USER", LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
		model.addAttribute("countNewUser", countNewUser);
		
		long totalLastMonth = parkingService.calParkingPriceNotMonTickLastMonth() + monthlyTicketService.calMonthlyTicketPriceFromLastMonth();
		model.addAttribute("totalLastMonth", totalLastMonth);
		
		long totalOfDay = parkingService.calParkingPriceNotMonTickDay();
		model.addAttribute("totalOfDay", totalOfDay);
		
		return "index";
	}
	
	//Trang Position
	@GetMapping("/Position")
	public String position(Model model) {
		List<Position> positions = positionService.getAllPositions();
		List<PositionDto> positionDto = new ArrayList<>();
		model.addAttribute("pageTitle", "Vị trí chỗ đỗ xe");
		for (Position position : positions) {
			PositionDto positionModel = new PositionDto();
	        positionModel.setId(position.getPositionId());
	        positionModel.setPositionName(position.getPositionName());
	        positionModel.setStatus(position.getStatus());
	        positionModel.setDescription(position.getDescription());
	        positionModel.setCondition(position.getCondition());
	        positionModel.setParking(position.getParking());
	        positionDto.add(positionModel);
	    }
	    model.addAttribute("positions", positionDto);
		return "showPosition";
	}
	
	//Trang Parking đang đỗ 
	@GetMapping("/Parking")
	public String parking(Model model) {
		List<Parking> parkings = parkingService.findAllParkingsWithNullCheckOut();
		List<ParkingDto> parkingDto = new ArrayList<>();
		model.addAttribute("pageTitle", "Bảng người dùng đang đỗ xe");
		for (Parking parking : parkings) {
			ParkingDto parkingModel = new ParkingDto();
			parkingModel.setId(parking.getParkingId());
			parkingModel.setParkingName(parking.getParkingName());
			parkingModel.setCheckIn(parking.getCheckIn());
			parkingModel.setCheckOut(parking.getCheckOut());
			parkingModel.setPosition(parking.getPosition());
			parkingModel.setMonthlyTicket(parking.getMonthlyTicket());
			parkingModel.setCar(parking.getCar());
			parkingDto.add(parkingModel);
		}
		model.addAttribute("parkings", parkingDto);
		return "showParking";
	}
	
	@GetMapping("/Parking/checkout/{parkingId}")
	public String updateParkingEndDate(@PathVariable("parkingId") Long parkingId) {
	    parkingService.updateParkingCheckOut(parkingId);
	    return "redirect:/Parking";
	}
	
	 // Update Parking Price
    @GetMapping("/ParkingPrice/edit")
    public String showParkingPrice(Model model) {
    	try {
    		ParkingPrice parkingPrice = parkingPriceService.getParkingPriceById(1);
            model.addAttribute("parkingPrice", parkingPrice);
            return "ParkingPrice";
    	} catch (ConfigDataResourceNotFoundException ex) {
            return "redirect:/index";
        }
    }
    
    @PostMapping("/UpdateParkingPrice/1")
    public String updateParkingPrice(@ModelAttribute("parkingPrice") ParkingPrice newParkingPrice,
	        BindingResult result, RedirectAttributes ra) {
    	if (result.hasErrors()) {
            return "index";
        }   	
    	 try {
    		 ParkingPrice updatedPrice = parkingPriceService.updateParkingPrice(1, newParkingPrice);
	        ra.addFlashAttribute("msg", "giá cập nhật thành công");
	    } catch (ConfigDataResourceNotFoundException ex) {
	    }
        return "redirect:/ParkingPrice/edit";
    }
	
	// bảng chỗ đỗ xe
	@GetMapping("/PositionTable")
	public String positionTable(Model model) {
		List<Position> positions = positionService.getAllPositions();
		List<PositionDto> positionDto = new ArrayList<>();
		model.addAttribute("pageTitle", "Bảng chỗ đỗ xe");
		for (Position position : positions) {
			PositionDto positionModel = new PositionDto();
	        positionModel.setId(position.getPositionId());
	        positionModel.setPositionName(position.getPositionName());
	        positionModel.setStatus(position.getStatus());
	        positionModel.setDescription(position.getDescription());
	        positionModel.setCondition(position.getCondition());
	        positionModel.setParking(position.getParking());
	        positionDto.add(positionModel);
	    }
	    model.addAttribute("positions", positionDto);
		return "positionTable";
	}
	
	//Thêm chỗ đỗ xe
	@GetMapping("/Position/add")
	public String newPosition(Model model) {
		model.addAttribute("position", new Position());
		List<String> areaList = Arrays.asList("KV 1", "KV 2");
    	List<String> conditionList = Arrays.asList("ACTIVE", "INACTIVE");
    	model.addAttribute("pageTitle", "Thêm chỗ đỗ xe");
		model.addAttribute("areaList", areaList);
		model.addAttribute("conditionList", conditionList);
		return "addPosition";
	}
	
	// Mapping để xử lý request thêm mới position
    @PostMapping("/positions")
    public String addPosition(@ModelAttribute("position") Position position, RedirectAttributes ra) {
    	positionService.createPosition(position);
    	ra.addFlashAttribute("msg", "Thêm chỗ đỗ thành công.");
        return "redirect:/Position/add";
    }
    
    
    // trang sửa chỗ đỗ xe
    @GetMapping("/Position/edit/{id}")
    public String showEditPositionForm(@PathVariable("id") Long id, Model model) {
        try {
        	List<String> areaList = Arrays.asList("KV 1", "KV 2");
        	List<String> conditionList = Arrays.asList("ACTIVE", "INACTIVE");
        	Position positionId = positionService.getPositionById(id);
            model.addAttribute("positionId", positionId);
    		model.addAttribute("areaList", areaList);
    		model.addAttribute("conditionList", conditionList);
            model.addAttribute("pageTitle", "Cập nhật chỗ đỗ xe");
            return "editPosition";
        } catch (ConfigDataResourceNotFoundException ex) {
            // Handle exception if position is not found
            return "redirect:/index";
        }
    }
    
    //controller cập nhật chỗ đỗ xe
    @PostMapping("/UpdatePosition/{id}")
	public String EditPosition(@PathVariable("id") Long id, Position newPosition,
	        BindingResult result, RedirectAttributes ra) {
    	if (result.hasErrors()) {
            return "positionTable";
        }   	
    	 try {
	        Position updatedPosition = positionService.updatePosition(id, newPosition);
	        ra.addFlashAttribute("msg", "chỗ đỗ cập nhật thành công");
	    } catch (ConfigDataResourceNotFoundException ex) {
	    }
    	return "redirect:/Position/edit/{id}";
	}
    
    // xóa chỗ đỗ xe
    @GetMapping("/Position/delete/{id}")
	public String DeletePosition(@PathVariable("id") Long id, Position newPosition,
	        BindingResult result, RedirectAttributes ra) {
    	try {
            positionService.deletePosition(id);
            ra.addFlashAttribute("msg", "Xóa chỗ đỗ thành công");
        } catch (ConfigDataResourceNotFoundException ex) {
            ra.addFlashAttribute("msg", "Xóa chỗ đỗ thất bại");
        }
        return "redirect:/positionTable";
	}
	
	
	/*
	 * @GetMapping("/UserManagerment") public String userManagerment() { return
	 * "userManagerment"; }
	 */
    
    @ModelAttribute("parkingDto")
    public ParkingDto getParkingDto() {
        return new ParkingDto();
    }

    //hàm chuyển đổi chung parking
    private List<ParkingDto> convertParkingsToParkingDto(List<Parking> parkings, Model model) {
        List<ParkingDto> parkingDto = new ArrayList<>();
        Long totalPrice = 0L;
        for (Parking parking : parkings) {
            ParkingDto parkingModel = new ParkingDto();
            parkingModel.setId(parking.getParkingId());
            parkingModel.setParkingName(parking.getParkingName());
            parkingModel.setCheckIn(parking.getCheckIn());
            parkingModel.setCheckOut(parking.getCheckOut());
            
            Long price = ParkingService.calculateParkingPrice(parking.getCheckIn(), parking.getCheckOut());
            parkingModel.setParkingPrice(price);
            if (parking.getMonthlyTicket() == null) {
                totalPrice += price;
            }
            
            parkingModel.setPosition(parking.getPosition());
            parkingModel.setMonthlyTicket(parking.getMonthlyTicket());
            parkingModel.setCar(parking.getCar());
            parkingDto.add(parkingModel);
        }
        model.addAttribute("totalPrice", totalPrice);
        return parkingDto;
    }
    
    // controller tìm parking
    @GetMapping("/ParkingReport/search")
    public String parkingReportSearch(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
        @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to, Model model) {
		List<Parking> parkings = parkingService.findParkingsBetween(from, to);
		model.addAttribute("pageTitle", "Bảng người dùng đang đỗ xe");
		List<ParkingDto> parkingDto = convertParkingsToParkingDto(parkings, model);
		model.addAttribute("parkings", parkingDto);
	
	    System.out.println("danh sach "+ parkings + "khong tháy gi");

		return "parkingReport";
	}
	
    // controller hiển thị parking
    @GetMapping("/ParkingReport")
	public String parkingReport(Model model) {
		List<Parking> parkings = parkingService.getAllParkingsWithNotNullCheckOut();
		model.addAttribute("pageTitle", "Bảng người dùng đã đỗ xe");
		List<ParkingDto> parkingDto = convertParkingsToParkingDto(parkings, model);
		model.addAttribute("parkings", parkingDto);
		return "parkingReport";
	}
	
    // hiển thị xe của parking id
	@GetMapping("/Parking/car/{id}")
	public String CarParking(@PathVariable("id") Long id, Model model) {
		try {
        	Car carId = carService.getCarById(id);
            model.addAttribute("carId", carId);
            model.addAttribute("pageTitle", "Xem thông tin xe");
            return "redirect:/carsDetail";
        } catch (ConfigDataResourceNotFoundException ex) {
            // Handle exception if position is not found
            return "redirect:/parkingReport";
        } 
	}
	
	@GetMapping("/MonthlyParkingTable")
	public String monParkingMana(Model model) {
		List<MonthlyTicket> monthlyTickets = monthlyTicketService.getMonthlyTicketAvailable();
		model.addAttribute("pageTitle", "Bảng người dùng đăng ký thẻ tháng");
		List<MonthlyTicketDto> monthlyTicketDto = new ArrayList<>();
		ParkingPrice parkingPrice = new ParkingPrice();
		for (MonthlyTicket monthlyTicket : monthlyTickets) {
			MonthlyTicketDto monTicketModel = new MonthlyTicketDto();
			monTicketModel.setTicketId(monthlyTicket.getTicketId());
			monTicketModel.setStartDate(monthlyTicket.getStartDate());
			monTicketModel.setEndDate(monthlyTicket.getEndDate());			
			monTicketModel.setPrice(parkingPrice.getPriceMonth());
			monTicketModel.setUser(monthlyTicket.getUser());
			monthlyTicketDto.add(monTicketModel);
		}
		model.addAttribute("monthlyTickets", monthlyTicketDto);
		return "monParkingMana";
	}
	
	
	
	@GetMapping("/ChangePassword")
	public String ChangePassword() {
		return "changePassword";
	}
	
	@GetMapping("/signin")
	public String signin() {
		
		return "signin";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//kiểm tra xem người dùng hiện tại đã đăng nhập hay chưa
	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		if (p != null) {
			String email = p.getName();
			UserDtls user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}
	}
	
	// hiển thị thông tin admin
	@GetMapping("/ProfileAdmin/{id}")
	public String ProfileAdmin(@PathVariable("id") Long id, Model model) {
		try {
			UserDtls userDtls = userService.getUserById(id);
            model.addAttribute("userDtls", userDtls);
            model.addAttribute("pageTitle", "Xem thông tin Admin");
            return "profileAdmin";
        } catch (ConfigDataResourceNotFoundException ex) {
            // Handle exception if position is not found
            return "redirect:/index";
        } 
	}
	
	// controller tạo tài khoản
	@PostMapping("/createUser")
	public String createuser(@RequestParam("password") String password, @RequestParam("password") String repeatPassword, @ModelAttribute UserDtls user, HttpSession session,  Model model) {

		// System.out.println(user);

		boolean f = userService.checkEmail(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email Id alreday exists");
		}

		else {
			UserDtls userDtls = userService.createUser(password, repeatPassword, user);
			if (userDtls != null) {
				System.out.println("Register Successfully"); 
				session.setAttribute("msg", "Register Successfully");
				model.addAttribute("user", userDtls); // truyền userDtls vào mô hình tham số
			} else {
				session.setAttribute("msg", "Password and Repeat Password do not match");
				System.out.println("Register Failed"); 
			}
		}

		return "redirect:/register";
	}
	
	// controller cập nhật mật khẩu
	@PostMapping("/updatePassword")
	public String updatePassword(Principal p, @RequestParam("oldPass") String oldPass, 
			@RequestParam("newPass") String newPass, 
			@RequestParam("reNewPass") String reNewPass, HttpSession session, Model model) {
		
		String email = p.getName();
		
		UserDtls loginUser = userRepo.findByEmail(email);
		boolean f = passwordEncoder.matches(oldPass, loginUser.getPassword());
		
		if (f) {
			model.addAttribute("pageTitle", "Cập nhật mật khẩu");
			 if (newPass.equals(reNewPass)) {
				 loginUser.setPassword(passwordEncoder.encode(newPass));
			      UserDtls updatePassUser = userRepo.save(loginUser);
			      if (updatePassUser != null) {
			    	  session.setAttribute("msg", "Change Password Successfully");
			      } else {
			    	  session.setAttribute("msg", "Change Password Failled");
			      }
			    } else {
			    	System.out.println("Nhập lại mật khẩu mới bị sai");
			    }
			System.out.println("Mật khẩu cũ đúng");
		}
			
		else {
			session.setAttribute("msg", "Old Password Incorrect");
			System.out.println("Mật khẩu cũ sai");
		}
			
		return "redirect:/ChangePassword";
	}
}
