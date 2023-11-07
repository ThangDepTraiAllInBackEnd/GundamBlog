package com.blog.services.impl;

import com.blog.common.Message;
import com.blog.dto.SeriesDTO;
import com.blog.mapper.SeriesMapper;
import com.blog.models.Series;
import com.blog.repositories.BlogRepository;
import com.blog.repositories.SeriesRepository;
import com.blog.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeriesServiceImpl implements SeriesService {
  @Autowired
  SeriesRepository seriesRepository;

  @Autowired
  BlogRepository blogRepository;

  @Override
  public Series createSeries(SeriesDTO seriesDTO) {
    Series series = SeriesMapper.getInstance().toEntity(seriesDTO);
    // series name is exits
    if (isSeriesExist(series.getSeriesName())) {
      return null;
    }
    return seriesRepository.save(series);
  }

  @Override
  public List<SeriesDTO> findAllSeries() {
    List<SeriesDTO> seriesDTOList = new ArrayList<>();
    List<Series> seriesList = seriesRepository.findAll();
    for (Series series : seriesList) {
      seriesDTOList.add(SeriesMapper.getInstance().toDTO(series));
    }
    return seriesDTOList;
  }

  @Override
  public Page<SeriesDTO> findSeriesByTitleForAdmin(String seriesName, int pageNo, int pageSize) {
    List<Series> seriesList = seriesRepository.getSeriesBySeriesName(seriesName);
    int totalElement = seriesList.size();
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
    int start = (int) pageable.getOffset();
    int end = (int) ((pageable.getOffset() + pageable.getPageSize()) > seriesList.size() ? seriesList.size() : pageable.getOffset() + pageable.getPageSize());
    seriesList = seriesList.subList(start, end);
    List<SeriesDTO> seriesDTOList = new ArrayList<>();
    for (Series series : seriesList) {
      SeriesDTO seriesDTO = SeriesMapper.getInstance().toDTOForAdmin(series);
      seriesDTO.setNumOfBlog(blogRepository.countBySeries(seriesRepository.findById(series.getSeriesId()).get()));
      seriesDTOList.add(seriesDTO);
    }
    return new PageImpl<>(seriesDTOList, pageable, totalElement);
  }

  @Override
  public Series renameSeries(Long seriesId, String newSeriesName) {
    // tag is exist
    if (seriesRepository.findBySeriesName(newSeriesName) != null) {
      System.out.println(Message.SERIES_EXIST);
      return null;
    }
    Optional<Series> series = seriesRepository.findById(seriesId);
    series.get().setSeriesName(newSeriesName);
    return seriesRepository.save(series.get());
  }

  @Override
  public boolean isSeriesExist(String seriesName) {
    Series seriesNameInDB = seriesRepository.findBySeriesName(seriesName);
    return seriesNameInDB != null;
  }
}
