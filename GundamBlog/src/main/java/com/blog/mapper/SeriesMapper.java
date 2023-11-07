package com.blog.mapper;

import com.blog.common.StringCommon;
import com.blog.dto.SeriesDTO;
import com.blog.models.Series;

public class SeriesMapper {
  private static SeriesMapper INSTANCE;

  public static SeriesMapper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new SeriesMapper();
    }
    return INSTANCE;
  }

  public Series toEntity(SeriesDTO dto) {
    Series series = new Series();
    series.setSeriesName(dto.getSeriesName());
    series.setPath(StringCommon.createPath(dto.getSeriesName()));
    return series;
  }

  public SeriesDTO toDTO(Series series) {
    SeriesDTO dto = new SeriesDTO();
    dto.setPath(series.getPath());
    dto.setSeriesName(series.getSeriesName());
    return dto;
  }

  public SeriesDTO toDTOForAdmin(Series series) {
    SeriesDTO dto = new SeriesDTO();
    dto.setSeriesId(series.getSeriesId());
    dto.setPath(series.getPath());
    dto.setSeriesName(series.getSeriesName());
    return dto;
  }
}
