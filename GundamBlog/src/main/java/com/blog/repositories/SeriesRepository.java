package com.blog.repositories;

import com.blog.models.Series;
import com.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
  Series findBySeriesName(String seriesName);

  @Query("FROM Series b WHERE b.seriesName LIKE %:seriesName%")
  List<Series> getSeriesBySeriesName(String seriesName);
}
