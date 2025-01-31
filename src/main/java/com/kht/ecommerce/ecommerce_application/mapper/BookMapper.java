package com.kht.ecommerce.ecommerce_application.mapper;


import com.kht.ecommerce.ecommerce_application.dto.KHTBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
//mapper.xml 에 작성된 sql 문을
// 아이디 명칭으로 가져와서 가져온 sql 문을 사용 대기 중인 상태
@Mapper
public interface BookMapper {
    // 책 목록 모두 조회 = findAll
    // *** 목록이나 리스트 와 같은형태로
    // 2개 이상을 조회할 때는 List<DTO파일명> 을 맨 앞에 작성

    //@Select("select * from khtbook")
    List<KHTBook> findAll(); // select * from khtbook sql 문이 들어있는 상태

    // 특정 정보 조회
    KHTBook findById(int id);

    // 수정 void             int     -> 수정한 행 수
    int updateById(int id, String title, String author, String genre, String imagePath);

    int deleteById(int id);
}
