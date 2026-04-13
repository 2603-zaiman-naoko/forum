package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    // IDの降順で並び変え
    // メソッド名を「find～」から始めることでSELECT文が発行されます。
    //「〜OrderByIdDesc」とすることで「id」というフィールドを
    // 「desc(降順)」で並び替えるクエリが発行されます。
    public List<Report> findAllByOrderByIdDesc();

}