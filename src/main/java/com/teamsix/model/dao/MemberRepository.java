package com.teamsix.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamsix.model.bean.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	public Member findByEmail(String email);
	public Member findByMemid(String memid);
	@Query(value = "SELECT date_table.registration_date, COALESCE(count_table.registration_count, 0) AS registration_count " +
            "FROM " +
            "(SELECT DATEADD(DAY, -6, CAST(GETDATE() AS date)) AS registration_date " +
            "UNION ALL " +
            "SELECT DATEADD(DAY, -5, CAST(GETDATE() AS date)) " +
            "UNION ALL " +
            "SELECT DATEADD(DAY, -4, CAST(GETDATE() AS date)) " +
            "UNION ALL " +
            "SELECT DATEADD(DAY, -3, CAST(GETDATE() AS date)) " +
            "UNION ALL " +
            "SELECT DATEADD(DAY, -2, CAST(GETDATE() AS date)) " +
            "UNION ALL " +
            "SELECT DATEADD(DAY, -1, CAST(GETDATE() AS date)) " +
            "UNION ALL " +
            "SELECT CAST(GETDATE() AS date)) AS date_table " +
            "LEFT JOIN " +
            "(SELECT CAST(registerdate AS date) AS registration_date, COUNT(*) AS registration_count " +
            "FROM member " +
            "WHERE registerdate >= DATEADD(DAY, -6, CAST(GETDATE() AS date)) " +
            "GROUP BY CAST(registerdate AS date)) AS count_table " +
            "ON date_table.registration_date = count_table.registration_date " +
            "ORDER BY date_table.registration_date", nativeQuery = true)
    List<Object[]> getRegistrationCountPerDay();
    
    public long count();
    @Query(value = " select case isgoogleaccount when 0 then '一般註冊' when 1 then 'google登入' END AS modified_isgoogleaccount,count(*) from member group by isgoogleaccount;", nativeQuery = true)
    public List<Object[]> getIsGoogleAccount();
    
    @Query(value = "SELECT\r\n"
    		+ "  CASE\r\n"
    		+ "    WHEN DATEDIFF(YEAR, birthdate, GETDATE()) < 18 THEN '未滿18歲'\r\n"
    		+ "    WHEN DATEDIFF(YEAR, birthdate, GETDATE()) BETWEEN 18 AND 25 THEN '18~25歲'\r\n"
    		+ "    WHEN DATEDIFF(YEAR, birthdate, GETDATE()) BETWEEN 26 AND 40 THEN '26~40歲'\r\n"
    		+ "    WHEN DATEDIFF(YEAR, birthdate, GETDATE()) BETWEEN 41 AND 60 THEN '41~60歲'\r\n"
    		+ "    ELSE '61歲以上'\r\n"
    		+ "  END AS age_group,\r\n"
    		+ "  COUNT(*) AS count\r\n"
    		+ "FROM member\r\n"
    		+ "WHERE birthdate IS NOT NULL\r\n"
    		+ "GROUP BY \r\n"
    		+ "  CASE\r\n"
    		+ "    WHEN DATEDIFF(YEAR, birthdate, GETDATE()) < 18 THEN '未滿18歲'\r\n"
    		+ "    WHEN DATEDIFF(YEAR, birthdate, GETDATE()) BETWEEN 18 AND 25 THEN '18~25歲'\r\n"
    		+ "    WHEN DATEDIFF(YEAR, birthdate, GETDATE()) BETWEEN 26 AND 40 THEN '26~40歲'\r\n"
    		+ "    WHEN DATEDIFF(YEAR, birthdate, GETDATE()) BETWEEN 41 AND 60 THEN '41~60歲'\r\n"
    		+ "    ELSE '61歲以上'\r\n"
    		+ "  END;",nativeQuery = true)
    public List<Object[]> getMemberAge();
    
}
