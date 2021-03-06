// 작성자 : 백영재
package com.gdj.cabbage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdj.cabbage.Debuging;
import com.gdj.cabbage.service.ManagerService;
import com.gdj.cabbage.vo.BiddingProductDelivery;
import com.gdj.cabbage.vo.BuyingProductDelivery;
import com.gdj.cabbage.vo.Manager;
import com.gdj.cabbage.vo.ProductConfirmationRegistration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ManagerController {
	
@Autowired ManagerService managerService;

	// 로그아웃
	@GetMapping("/manager/managerLogout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/managerLogin";// 세션 종료후 로그인 페이지로 이동
	}

	// 판매완료 중고상품 수정 //
	// GET // 수정페이지만 보내주는것
	@GetMapping("/manager/modifyBidSuccessProduct")
	public String modifyBidSuccessProduct(Model model,
			@RequestParam(value = "apsdId", required = true) int apsdId) {
		
		log.debug(Debuging.DEBUG+" ######################apsdId : " + apsdId);
		
		Map<String, Object> bidSuccessProductOne = managerService.selectBidSuccessProductInfo(apsdId);
		
		log.debug(Debuging.DEBUG+" #####################bidSuccessProductOne : " + bidSuccessProductOne);
		
		model.addAttribute("bidSuccessProductOne", bidSuccessProductOne);
		
		return "manager/modifyBidSuccessProduct";
		
	}
	
	// POST // 수정된 값 받아와서 수정 실질적으로 되는부분
	@PostMapping("/manager/modifyBidSuccessProduct")
	public String modifyBidSuccessProduct(BiddingProductDelivery biddingProductDelivery,
			@RequestParam(value = "apsdId", required = true) int apsdId) {
		
		log.debug("##############biddingProductDelivery : " + biddingProductDelivery.toString());
		log.debug("##############apsdId : " + apsdId);
		
		biddingProductDelivery.setApplyProductSalesDeliveryId(apsdId); // 파라미터 가져온것 전처리로 파라미터에 넣어주는 것
		
		// update
		int updateRow = managerService.modifyBidSuccessProduct(biddingProductDelivery);

		// 디버깅
		log.debug("modify 실행 여부 : " + updateRow);
		
		return "redirect:/manager/getBidSuccessProductList";
	}
	
	// 낙찰완료 경매상품 목록
	@GetMapping("/manager/getBidSuccessProductList")
	public String getBidSuccessProductList(Model model,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="rowPerPage", defaultValue="10") int rowPerPage,
			@RequestParam(value="searchWord", required=false) String searchWord) {
		
		log.debug("★★★★★★★@@@@ currentPage" + currentPage);
		log.debug("★★★★★★★@@@@ rowPerPage" + rowPerPage);
		log.debug("★★★★★★★@@@@ searchWord" + searchWord);
		
		Map<String, Object> map = managerService.getBidSuccessProductList(currentPage, rowPerPage, searchWord);
		
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("bidSuccessList", map.get("bidSuccessList"));
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("currentPage", currentPage);
		
		return "manager/getBidSuccessProductList";
	}
	
	
	// 판매완료 중고상품 수정 //
	// GET // 수정페이지만 보내주는것
	@GetMapping("/manager/modifySoldoutUsedProduct")
	public String modifySoldoutUsedProduct(Model model,
			@RequestParam(value = "apsdId", required = true) int apsdId) {
		
		log.debug(Debuging.DEBUG+" ######################apsdId : " + apsdId);
		
		Map<String, Object> soldoutProductOne = managerService.selectSoldoutUsedProductInfo(apsdId);
		
		log.debug(Debuging.DEBUG+" #####################soldoutProductOne : " + soldoutProductOne);
		
		model.addAttribute("soldoutProductOne", soldoutProductOne);
		
		return "manager/modifySoldoutUsedProduct";
		
	}
	
	// POST // 수정된 값 받아와서 수정 실질적으로 되는부분
	@PostMapping("/manager/modifySoldoutUsedProduct")
	public String modifySoldoutUsedProduct(BuyingProductDelivery buyingProductDelivery,
			@RequestParam(value = "apsdId", required = true) int apsdId) {
		
		log.debug("##############buyingProductDelivery : " + buyingProductDelivery.toString());
		log.debug("##############apsdId : " + apsdId);
		
		buyingProductDelivery.setApplyProductSalesDeliveryId(apsdId); // 파라미터 가져온것 전처리로 파라미터에 넣어주는 것
		
		// update
		int updateRow = managerService.modifySoldoutUsedProduct(buyingProductDelivery);

		// 디버깅
		log.debug("modify 실행 여부 : " + updateRow);
		
		return "redirect:/manager/getSoldoutUsedProductList";
	}
	
	
	// 판매완료 중고상품 목록
	@GetMapping("/manager/getSoldoutUsedProductList")
	public String getSoldoutUsedProductList(Model model,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="rowPerPage", defaultValue="10") int rowPerPage,
			@RequestParam(value="searchWord", required=false) String searchWord) {
		
		log.debug("★★★★★★★@@@@ currentPage" + currentPage);
		log.debug("★★★★★★★@@@@ rowPerPage" + rowPerPage);
		log.debug("★★★★★★★@@@@ searchWord" + searchWord);
		
		Map<String, Object> map = managerService.getSoldoutUsedProductList(currentPage, rowPerPage, searchWord);
		
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("soldoutList", map.get("soldoutList"));
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("currentPage", currentPage);
		
		return "manager/getSoldoutUsedProductList";
	}
	
	
	// 배송상품 상세보기
	@GetMapping("/manager/getDeliveryProductInfo")
	public String getDeliveryProductInfo(Model model, @RequestParam(value="apsdi", required=true) Integer apsdi) {
		
		Map<String, Object> productInfo = managerService.getDeliveryProductInfo(apsdi);
		log.debug(Debuging.DEBUG+" productInfo : " + productInfo.toString());
		
		model.addAttribute("productInfo", productInfo);
		
		return "/manager/getDeliveryProductInfo";
	}
	
	
	// 관리자 인덱스
	@GetMapping("/manager/managerIndex")
	public String managerIndex(Model model) {
		
		Map<String, Object> totoMap = new HashMap<>();
		
		totoMap.put("usersTotal", managerService.getTotalUsers());
		totoMap.put("directTotal", managerService.getTotalDirectProduct());
		totoMap.put("usedTotal", managerService.getTotalUsedProduct());
		totoMap.put("auctionTotal", managerService.getTotalAuctionProduct());
		totoMap.put("usersToday", managerService.getTodayUsers());
		totoMap.put("directToday", managerService.getTodayDirect());
		totoMap.put("usedToday", managerService.getTodayUsed());
		totoMap.put("auctionToday", managerService.getTodayAuction());
		
		model.addAttribute("totoMap", totoMap);
		
		return "/manager/managerIndex";
	}
	
	
	//관리자 수정 //
	// GET // 수정페이지만 보내주는것
	@GetMapping("/manager/modifyManager")
	public String modifyManager(Model model,
			@RequestParam(value = "managerId", required = true) String managerId) {
		log.debug(Debuging.DEBUG+" managerId : " + managerId);
		
		Map<String, Object> modifyManagerOne = managerService.selectManagerInfo(managerId);
		
		log.debug(Debuging.DEBUG+" modifyManagerOne : " + modifyManagerOne.toString());
		
		model.addAttribute("modifyManagerOne", modifyManagerOne);
		
		return "manager/modifyManager";
		
	}
	
	// POST // 수정된 값 받아와서 수정 실질적으로 되는부분
	@PostMapping("/manager/modifyManager")
	public String modifyManager(Manager manager) {
		
		// update
		int row = managerService.modifyManager(manager);

		// 디버깅
		log.debug("modify 실행 여부 : " + row);
		
		return "redirect:/manager/getManagerInfo?managerId=" + manager.getManagerId();
	}
	
	
	// 배송 신청된 상품 목록 출력
	@GetMapping("/manager/getDeliveryProductList")
	public String getDeliveryProductList(HttpSession session, Model model) {		
		
		List<Map<String,Object>> getDeliveryProductList = managerService.getDeliveryProductList();
		log.debug(Debuging.DEBUG+" getDeliveryProductList "+getDeliveryProductList);
		
		session.getAttribute("managerSession");
		model.addAttribute("getDeliveryProductList", getDeliveryProductList);
		
		return "/manager/getDeliveryProductList";
	}
	
	@GetMapping("manager/addDeliveryProductList")
	public String addDeliveryProductToPcr(ProductConfirmationRegistration productConfirmationRegistration) {
		
		managerService.addDeliveryProductToPcr(productConfirmationRegistration);
		
		return "redirect:/manager/getDeliveryProductList";
	}

	// 관리자 상세보기
	@GetMapping("/manager/getManagerInfo")
	public String getManagerInfo(Model model, @RequestParam(value="managerId", required=true) String managerId) {
		
		Map<String, Object> managerInfo = managerService.selectManagerInfo(managerId);
		log.debug(Debuging.DEBUG+" managerInfo : " + managerInfo.toString());
		
		model.addAttribute("managerInfo", managerInfo);
		
		return "/manager/getManagerInfo";
	}

	// 회원 목록 출력 기능
	@GetMapping("/manager/getAllUsersByManager")
	// model은 뷰로 넘겨주기 위한 상자, requestParam 뷰에서 받아 오는 parameter
	public String getAllUsersByManager(Model model,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="rowPerPage", defaultValue="10") int rowPerPage,
			@RequestParam(value="searchWord", required=false) String searchWord){
		
		log.debug("★★★★★★★ currentPage" + currentPage);
		log.debug("★★★★★★★ rowPerPage" + rowPerPage);
		log.debug("★★★★★★★ searchWord" + searchWord);
		
		Map<String, Object> map = managerService.getAllUsersByManager(currentPage, rowPerPage, searchWord);
		
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("allUsersList", map.get("allUsersList"));
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("currentPage", currentPage);
		
		return "manager/getAllUsersByManager";
	}

	// 관리자 목록
	@GetMapping("/manager/getManagerList")
	public String getMangerList(Model model,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="rowPerPage", defaultValue="10") int rowPerPage) {
		
		log.debug(Debuging.DEBUG+" currentPage : " + currentPage);
		log.debug(Debuging.DEBUG+" rowPerPage : " + rowPerPage);
		
		Map<String, Object> map = managerService.getManagerList(currentPage, rowPerPage);
		
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("managerList", map.get("managerList"));
		model.addAttribute("currentPage", currentPage);
		
		return "manager/getManagerList";
	}
	
	
	// 관리자 로그인 //
	// GET
	@GetMapping("/managerLogin")
	public String managerLogin() {
		return "/managerLogin";
	}
	
	// POST
	@PostMapping("/managerLogin")
	public String managerLogin(HttpSession session, Manager manager) {
		log.debug(Debuging.DEBUG+"=================================== manager " + manager);
		log.debug(Debuging.DEBUG+" session " + session);
		
		Map<String, Object> managerSession = managerService.managerLoginSession(manager);
		log.debug(Debuging.DEBUG+" $$$$$$$$$$$$$$$managerSession : " + managerSession);
		
		if(managerSession != null) {
			session.setAttribute("managerSession", managerSession);
			
		}
		
		return "redirect:/manager/managerIndex";
	}
	
	// 관리자 추가 //
	// 폼
	@GetMapping("/manager/addManager")
	public String addManager() {
		
		return "manager/addManager";
	}
	
	// 액션
	@PostMapping("/manager/addManager")
	public String addManager(Model model,
			@RequestParam(value = "managerId", required = false) String managerId,			
			@RequestParam(value = "managerPassword", required = false) String managerPassword,			
			@RequestParam(value = "managerName", required = false) String managerName,			
			@RequestParam(value = "managerNickname", required = false) String managerNickname,			
			@RequestParam(value = "managerAddress", required = false) String managerAddress,			
			@RequestParam(value = "managerPhoneNumber", required = false) Integer managerPhoneNumber,			
			@RequestParam(value = "managerLevel", required = false) Integer managerLevel) {
		
		log.debug("★★★★★★★ManagerController에서 addManager -> managerId : " + managerId);
		log.debug("★★★★★★★ManagerController에서 addManager -> managerPassword : " + managerPassword);
		log.debug("★★★★★★★ManagerController에서 addManager -> managerName : " + managerName);
		log.debug("★★★★★★★ManagerController에서 addManager -> managerNickname : " + managerNickname);
		log.debug("★★★★★★★ManagerController에서 addManager -> managerAddress : " + managerAddress);
		log.debug("★★★★★★★ManagerController에서 addManager -> managerPhoneNumber : " + managerPhoneNumber);
		log.debug("★★★★★★★ManagerController에서 addManager -> managerLevel : " + managerLevel);
		
		Map<String, Object> map = new HashMap<>();
		
		log.debug("★★★★★★★ManagerController에서 addManager -> map : " + map);
		map.put("managerId", managerId);
		map.put("managerPassword", managerPassword);
		map.put("managerName", managerName);
		map.put("managerNickname", managerNickname);
		map.put("managerAddress", managerAddress);
		map.put("managerPhoneNumber", managerPhoneNumber);
		map.put("managerLevel", managerLevel);
		
		int addManager = managerService.addManager(map);
		log.debug("★★★★★★★ManagerController에서 addManager -> addManager : " + addManager);
		
		model.addAttribute("map", map);
		model.addAttribute("managerId", managerId);
		model.addAttribute("managerPassword", managerPassword);
		model.addAttribute("managerName", managerName);
		model.addAttribute("managerNickname", managerNickname);
		model.addAttribute("managerAddress", managerAddress);
		model.addAttribute("managerPhoneNumber", managerPhoneNumber);
		model.addAttribute("managerLevel", managerLevel);
		
		return "redirect:/manager/getManagerList";
	}

}