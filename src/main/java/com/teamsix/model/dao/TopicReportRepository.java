package com.teamsix.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.TopicReport;

public interface TopicReportRepository extends JpaRepository<TopicReport, Integer> {
	List<TopicReport> findByReportStatus(Integer reportStatus);
}
