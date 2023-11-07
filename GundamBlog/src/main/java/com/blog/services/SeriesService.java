package com.blog.services;

import com.blog.dto.SeriesDTO;
import com.blog.dto.TagDTO;
import com.blog.models.Series;
import com.blog.models.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SeriesService {

  boolean isSeriesExist(String seriesName);

  Series createSeries(SeriesDTO seriesDTO);

  List<SeriesDTO> findAllSeries();

  Page<SeriesDTO> findSeriesByTitleForAdmin(String seriesName, int pageNo, int pageSize);

  Series renameSeries(Long tagId, String newSeriesName);
}
