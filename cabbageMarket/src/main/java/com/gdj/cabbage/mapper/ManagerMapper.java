// 작성자 : 백영재
package com.gdj.cabbage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdj.cabbage.vo.Manager;
import com.gdj.cabbage.vo.Page;

@Mapper
public interface ManagerMapper {
	
	// 배송상품 상세보기
	Map<String, Object> selectDeliveryProductInfo(int applyProductSalesDeliveryId);
	// 오늘 회원
	int selectTodayUsers();
	// 오늘 직거래
	int selectTodayDirect();
	// 오늘 중고
	int selectTodayUsed();
	// 오늘 경매
	int selectTodayAuction();
	// 총 회원
	int selectTotalUsers();
	// 총 직거래
	int selectTotalDirectProduct();
	// 총 중고
	int selectTotalUsedProduct();
	// 총 경매
	int selectTotalAuctionProduct();
	// 배송 신청된 상품 목록 출력
	List<Map<String,Object>> selectDeliveryProductList();
	// 매니저 상세보기
	Map<String, Object> selectManagerInfo(String managerId);
	// 유저목록보기 토탈
	int selectAllUsersTotal(String searchWord);
	// 유저목록보기 기능
	List<Map<String, Object>> selectAllUsersByManager(Page page);
	// 관리자 토탈
	int selectManagerTotal();
	// 관리자 목록
	List<Manager> selectManagerList(Page page);
	// 관리자 수정
	int updateManager(Manager manager);
	// 관리자 추가
	int insertManager(Map<String, Object> map);
	// 관리자 세션
	Map<String, Object> managerLoginSession(Manager manager);

}
