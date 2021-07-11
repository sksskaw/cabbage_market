// 작성자 : 김태훈
package com.gdj.cabbage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChattingMapper {
	Map<String, Object> selectChattingRoomOne(Map<String, Object> map); // 채팅방 정보 가져오기
	void insertChattingRoop(Map<String, Object> map); // 채팅방 생성
	int selectSellerId(int directTradeProductRegistrationId); // 직거래 상품 등록 (판매자) Id 가져오기
	
	List<Map<String, Object>> selectChattingContentListById(int chattingRoomId); // 채팅방에 있는 채팅 내용 리스트
	void insertChattingContent(Map<String, Object> map); // 채팅 내용 입력
}