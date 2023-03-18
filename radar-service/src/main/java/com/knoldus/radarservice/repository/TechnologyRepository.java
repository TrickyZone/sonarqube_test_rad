package com.knoldus.radarservice.repository;

import com.knoldus.radarservice.model.Technology;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, UUID> {

    Optional<Technology> findByName(String name);

    Optional<Technology> findByNameAndIdNot(String name, UUID id);

    List<Technology> findByNameIgnoreCaseIn(Set<String> technologies);

    List<Technology> findByQuarterAndYearOrderByQuadrantAscRingAsc(String quarter, String year);

    List<Technology> findByStudio(String studio);

    List<Technology> findByQuarterAndStudioAndYearOrderByQuadrantAscRingAsc(String quarter, String studio, String year);

    List<Technology> findByQuarter(String quarter);

    @Query(value="select * from technology order by quadrant asc , case ring when 'Adopt' THEN 1 WHEN 'Trial' " +
            "THEN 2 WHEN 'Assess' THEN 2 WHEN 'Hold' THEN 4 END;",nativeQuery = true)
    List<Technology> findAllByOrderByQuadrantAscRingAsc();

    List<Technology> findAllByOrderByCreatedAtDesc();

    @Query("SELECT t FROM Technology t WHERE (:quadrant is null or t.quadrant = :quadrant) and (:studio is null or t.studio = :studio) and (:quarter is null or t.quarter = :quarter)")
    List<Technology> findAllByAreaAndStudioAndQuadrant(@Param("quadrant") String area, @Param("studio") String studio, @Param("quarter") String quarter);

    public List<Technology> findByNameContainingIgnoreCase(String name);

}
