package com.kht.ecommerce.ecommerce_application.service;

import com.kht.ecommerce.ecommerce_application.dto.KHTBook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface BookService {
    /*
    @Autowired = 다른 파일이나 다른 파일안에 작성된 기능을 가져와서 사용

    interface 는 특정 기능을 상세하게 어떠한 동작으로 작동하라는 세부 기능설정을
    해주지 않기 때문에 @Autowired 를 작성할 필요가 없음 XXXXX
    @Autowired
    private BookMapper bookmapper;
     */

    List<KHTBook> findAll();

    KHTBook findById(int id);

    int updateById(int id, String title, String author, String genre, MultipartFile imagePath);
    //int updateById(KHTBook khtBook);

    int deleteById(int id);
}
