
package com.gdj.cabbage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdj.cabbage.Debuging;
import com.gdj.cabbage.mapper.ApplyProductSalesMapper;
import com.gdj.cabbage.mapper.AuctionMapper;
import com.gdj.cabbage.mapper.CategoryMapper;
import com.gdj.cabbage.vo.AuctionProductRegistration;
import com.gdj.cabbage.vo.CategoryMain;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AuctionService {
	@Autowired AuctionMapper auctionMapper;
	@Autowired CategoryMapper categoryMapper;
	@Autowired ApplyProductSalesMapper applyProductSalesMapper;

	//경매상품과 검색어에따른 total, lastPage, auctionList가져오는 서비스 : getAuctionList
	public Map<String, Object> getAuctionList(Map<String, Object> paramMap) {
		log.debug(Debuging.DEBUG+"2 controller에서 보낸 paramMap확인"+paramMap.toString());
		
		int beginRow = (((int)paramMap.get("currentPage")-1)*(int)paramMap.get("rowPerPage"));
		int total = (auctionMapper.selectAuctionForCount()); //검색어 없이 total 가져오는 mapper
		int lastPage = (int)(Math.ceil((double)total / (int)paramMap.get("rowPerPage"))); //lastPage 계산
		
		Map<String,Object> page = new HashMap<String,Object>();
		page.put("beginRow", beginRow);
		page.put("RowPerPage", ((int)paramMap.get("rowPerPage")));
		page.put("SearchWord", ((String)paramMap.get("searchWord")));
		log.debug(Debuging.DEBUG+"3 mapper로 보낼 Page 학인 : "+ page.toString());
		
		log.debug(Debuging.DEBUG+"4 mapper에서 온 total 확인: "+ total);
		List<Map<String,Object>> auctionList = auctionMapper.selectAuctionList(page); //검색어 없이 auctionList 가져오는 mapper
		log.debug(Debuging.DEBUG+"4 mapper에서 보낸 auctionList 확인"+auctionList.toString());
		
		List<CategoryMain> categoryMainList = categoryMapper.selectCategoryMain();//카테고리 메인을 불러오는 mapper
		log.debug(Debuging.DEBUG+"4 mapper에서 보낸 categoryMainList확인"+categoryMainList.toString());
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("total", total);
		resultMap.put("lastPage", lastPage);
		resultMap.put("auctionList", auctionList);
		resultMap.put("categoryMainList", categoryMainList);
		
		return resultMap;
	}
	// 상품 상세정보 가져오는 서비스 : getAuctionOne
	public Map<String, Object> getAuctionOne(int applyId) {
		log.debug(Debuging.DEBUG+"2 controller에서 보낸 applyId확인"+applyId);
		log.debug(Debuging.DEBUG+"3 mapper로 보낼 applyId 학인 : "+ applyId);
		Map<String,Object> auctionList = auctionMapper.selectAuctionOne(applyId); //상세정보 가져오는 mapper
		return auctionList;
	}
	// 상품 이미지들 불러오는 서비스 : getAuctionOne addAuction
	public List<String> getApplyImg(int applyId) {
		log.debug(Debuging.DEBUG+"2 controller에서 보낸 applyId확인"+applyId);
		log.debug(Debuging.DEBUG+"3 mapper로 보낼 applyId 학인 : "+ applyId);
		List<String> auctionList = applyProductSalesMapper.selectApplyImgByKey(applyId); //이미지를 가져오는 mapper
		return auctionList;
	}
	
	// 상품 판매 배송 신청 리스트를 불러오는 서비스 : 갈곳잃음
	public Map<String, Object> getApplyList(Map<String, Object> paramMap) {
		log.debug(Debuging.DEBUG+"2 controller에서 보낸 paramMap확인"+paramMap.toString());
		
		int beginRow = (((int)paramMap.get("currentPage")-1)*(int)paramMap.get("rowPerPage"));
		int total = (auctionMapper.selectApplyForCount(paramMap)); //검색어 없이 apply total 가져오는 mapper
		int lastPage = (int)(Math.ceil((double)total / (int)paramMap.get("rowPerPage"))); //lastPage 계산
		
		Map<String,Object> page = new HashMap<String,Object>();
		page.put("beginRow", beginRow);
		page.put("RowPerPage", ((int)paramMap.get("rowPerPage")));
		page.put("SearchWord", ((String)paramMap.get("searchWord")));
		page.put("userId", ((int)paramMap.get("userId")));
		log.debug(Debuging.DEBUG+"3 mapper로 보낼 Page 학인 : "+ page.toString());
		
		log.debug(Debuging.DEBUG+"4 mapper에서 온 total 확인: "+ total);
		List<Map<String,Object>> applyList = auctionMapper.selectApplyList(page); //검색어 없이 applyList 가져오는 mapper
		log.debug(Debuging.DEBUG+"4 mapper에서 보낸 applyList 확인"+applyList.toString());
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("total", total);
		resultMap.put("lastPage", lastPage);
		resultMap.put("applyList", applyList);
		
		return resultMap;
	}

	// 상품 판매 배송 신청 리스트에서 선택해서 넘어오는 서비스 : addAuction? 이걸로 addUsedProduct 랑 updateAuction/usedProdect도 될것같은데.. 
	// 아 생각대로 할려면 ajax으로 해야하는데요? 우선 만들게...게요?
	public Map<String, Object> getApplyOne(int applyId) {
		log.debug(Debuging.DEBUG+"2 controller에서 보낸 applyId확인"+applyId);
		log.debug(Debuging.DEBUG+"3 mapper로 보낼 applyId 학인 : "+ applyId);
		Map<String,Object> auctionList = auctionMapper.selectApplynOne(applyId); //상세정보 가져오는 mapper
		return auctionList;
	}
	
	// 옥션 등록 service
	public int addAuctionProduct(AuctionProductRegistration auctionProductRegistration) {
		log.debug(Debuging.DEBUG+"2 controller에서 보낸 auctionProductRegistration확인"+auctionProductRegistration.toString());
		log.debug(Debuging.DEBUG+"3 mapper로 보낼 auctionProductRegistration 학인 : "+ auctionProductRegistration);
		int cnt = auctionMapper.insertAuction(auctionProductRegistration);
		log.debug(Debuging.DEBUG+"4 mapper에서 온 cnt 확인: "+ cnt);
		
		if(cnt >0) {
			auctionMapper.updateConfirmationState(auctionProductRegistration.getApplyProductSalesDeliveryId()); 
		}
		return cnt;
	}
	public int addBid(Map<String, Object> map) {
		int cnt = 0;
		log.debug(Debuging.DEBUG+"2 controller에서 보낸 map확인"+map.toString());
		log.debug(Debuging.DEBUG+"3 mapper로 보낼 map 학인 : "+ map);

		int didIBid = auctionMapper.selectBeforeBidId(map);
		
		
		if(didIBid == 0) // 이 아이디로 입찰한적이 없다면,
		{
			log.debug(Debuging.DEBUG+"didIBid가 null = 입찰핝없음");
			cnt = + auctionMapper.insertBidding(map);
			log.debug(Debuging.DEBUG+"4 insertBidHistory mapper에서 온 cnt 확인: "+ cnt);
			
			int newBidId = auctionMapper.selectNewBidId(map);
			log.debug(Debuging.DEBUG+"4 selectBidId mapper에서 온 newBidId 확인: "+ newBidId);
			map.put("newBidId", newBidId);
			cnt = + auctionMapper.insertBidPointPlusHistory(map);
			log.debug(Debuging.DEBUG+"4 mapper에서 온 cnt 확인: "+ cnt);
		} else { // 이 아이디로 입찰한적이 있다면,
			Map<String, Object> beforeBid = auctionMapper.selectBeforeBid(map);		//이전 입찰자 찾는 mapper
			log.debug(Debuging.DEBUG+"4 mapper에서 온 beforeBid 확인: "+ beforeBid.toString());
			if ( ((int)beforeBid.get("userId")) == ((int)map.get("userId")) ) {
				cnt = 210720;
			} else if(didIBid > 0) {
				cnt = auctionMapper.insertBidPointMinusHistory(beforeBid);
				
				cnt = + auctionMapper.insertBidding(map);
				log.debug(Debuging.DEBUG+"4 insertBidHistory mapper에서 온 cnt 확인: "+ cnt);
				
				int newBidId = auctionMapper.selectNewBidId(map);
				log.debug(Debuging.DEBUG+"4 selectBidId mapper에서 온 newBidId 확인: "+ newBidId);
				map.put("newBidId", newBidId);
				cnt = + auctionMapper.insertBidPointPlusHistory(map);
				log.debug(Debuging.DEBUG+"4 mapper에서 온 cnt 확인: "+ cnt);
			} else {
				cnt = 0;
			}
		}
		return cnt;
	}
	public int modifyPcrActuon() {
		log.debug(Debuging.DEBUG+"2 controller에서 보낸 map확인"+"없음");
		log.debug(Debuging.DEBUG+"3 mapper로 보낼 map 학인 : "+ "없음");
		
		return 0;
	}

}
